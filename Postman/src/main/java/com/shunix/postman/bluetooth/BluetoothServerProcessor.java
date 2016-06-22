package com.shunix.postman.bluetooth;

import android.annotation.TargetApi;
import android.bluetooth.*;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.os.Build;
import android.os.ParcelUuid;
import android.util.Log;
import com.shunix.postman.R;
import com.shunix.postman.proto.NotificationProto;
import com.shunix.postman.util.Config;
import com.shunix.postman.util.Constants;

import java.util.Arrays;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author shunix
 * @since 2016/6/12
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class BluetoothServerProcessor {
    private final static String TAG = BluetoothServerProcessor.class.getSimpleName();

    private Context mContext;
    private BluetoothGattServer mBluetoothGattServer;
    private int mMessageId;
    private int mPacketCount;
    private int mLastPacketSeq; // seq of last received packet
    private Queue<NotificationProto.NotificationMessageReq> mPendingQueue;

    public BluetoothServerProcessor(Context context) {
        mContext = context;
        mPendingQueue = new LinkedBlockingQueue<>();
        reset();
    }

    public void process() {
        if (mContext != null) {
            startAdvertising();
            if (mBluetoothGattServer == null) {
                BluetoothManager bluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
                if (bluetoothManager != null) {
                    mBluetoothGattServer = bluetoothManager.openGattServer(mContext, mBluetoothGattServerCallback);
                }
            }
            addService();
        }
    }

    private void reset() {
        mLastPacketSeq = -1;
        mPendingQueue.clear();
    }

    private void onReceiveNewMessage(NotificationProto.NotificationMessageReq req) {
        if (req != null) {
            if (Config.DEBUG) {
                Log.d(TAG, "onReceiveNewMessage " + req.getUint32Id());
            }
            mMessageId = req.getUint32Id();
            mPacketCount = req.getUint32Count();
        }
    }

    private boolean isValidPacket(NotificationProto.NotificationMessageReq req) {
        if (req != null) {
            int messageId = req.getUint32Id();
            int packetSeq = req.getUint32Seq();
            if (Config.DEBUG) {
                Log.d(TAG, "isValidPacket " + packetSeq);
            }
            return messageId == mMessageId && packetSeq == mLastPacketSeq + 1;
        }
        return false;
    }

    private boolean isFullMessageReceived() {
        return mPacketCount == mLastPacketSeq + 1;
    }

    private boolean parseMessage() {
        byte[] buffer = new byte[2560];
        int count = 0;
        while(!mPendingQueue.isEmpty()) {
            NotificationProto.NotificationMessageReq req = mPendingQueue.poll();
            byte[] payload = req.getBytesPayload().toByteArray();
            for(int i = 0; i < payload.length; ++i) {
                buffer[count + i] = payload[i];
            }
            count += payload.length;
        }
        byte[] messageBuffer = Arrays.copyOfRange(buffer, 0, count);
        try {
            NotificationProto.MarshalledNotificationMessage message = NotificationProto.MarshalledNotificationMessage.parseFrom(messageBuffer);
            if (message.hasUint32Id()) {
                return true;
            }
        } catch (Exception e) {
            if (Config.DEBUG) {
                Log.e(TAG, e.getMessage());
            }
            return false;
        }
        return false;
    }

    private void sendResponse(BluetoothDevice device, BluetoothGattCharacteristic characteristic, int requestId, int retCode) {
        // FIXME bugs here, client didn't receive correct bytes
        try {
            NotificationProto.NotificationMessageRsp.Builder builder = NotificationProto.NotificationMessageRsp.newBuilder();
            builder.setUint32Id(mMessageId);
            builder.setUint32RetCode(retCode);
            NotificationProto.NotificationMessageRsp rsp = builder.build();
            byte[] value = rsp.toByteArray();
            characteristic.setValue(value);
            mBluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, 0, characteristic.getValue());
            if (Config.DEBUG) {
                Log.d(TAG, "sendResponse " + retCode);
            }
        } catch (Exception e) {
            if (Config.DEBUG) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private void startAdvertising() {
        if (mContext != null) {
            BluetoothManager bluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
            if (bluetoothManager != null) {
                BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
                if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
                    BluetoothLeAdvertiser bluetoothLeAdvertiser = bluetoothAdapter.getBluetoothLeAdvertiser();
                    if (bluetoothLeAdvertiser != null) {
                        AdvertiseSettings advertiseSettings = new AdvertiseSettings.Builder()
                                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED)
                                .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_LOW)
                                .setConnectable(true)
                                .build();
                        ParcelUuid parcelUuid = new ParcelUuid(UUID.fromString(mContext.getString(R.string.server_uuid)));
                        bluetoothAdapter.setName(mContext.getString(R.string.app_name));
                        AdvertiseData advertiseData = new AdvertiseData.Builder()
                                .addServiceUuid(parcelUuid)
                                .setIncludeDeviceName(true)
                                .setIncludeTxPowerLevel(false)
                                .build();
                        bluetoothLeAdvertiser.startAdvertising(advertiseSettings, advertiseData, mAdvertiseCallback);
                    }
                }
            }
        }
    }

    private void addService() {
        if (mContext != null && mBluetoothGattServer != null) {
            BluetoothGattService bluetoothGattService = new BluetoothGattService(UUID.fromString(mContext.getString(R.string.service_uuid)),
                    BluetoothGattService.SERVICE_TYPE_PRIMARY);
            BluetoothGattCharacteristic bluetoothGattCharacteristic = new BluetoothGattCharacteristic(UUID.fromString(mContext.getString(R.string.characteristic_uuid)),
                    BluetoothGattCharacteristic.PROPERTY_WRITE,
                    BluetoothGattCharacteristic.PERMISSION_WRITE);
            bluetoothGattService.addCharacteristic(bluetoothGattCharacteristic);
            mBluetoothGattServer.addService(bluetoothGattService);
        }
    }

    AdvertiseCallback mAdvertiseCallback = new AdvertiseCallback() {
        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            super.onStartSuccess(settingsInEffect);
            if (Config.DEBUG) {
                Log.d(TAG, "advertise callback onStartSuccess");
            }
        }

        @Override
        public void onStartFailure(int errorCode) {
            super.onStartFailure(errorCode);
            if (Config.DEBUG) {
                Log.d(TAG, "advertise callback onStartFailure error code " + errorCode);
            }
        }
    };

    BluetoothGattServerCallback mBluetoothGattServerCallback = new BluetoothGattServerCallback() {
        @Override
        public void onCharacteristicWriteRequest(BluetoothDevice device, int requestId, BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
            super.onCharacteristicWriteRequest(device, requestId, characteristic, preparedWrite, responseNeeded, offset, value);
            if (Config.DEBUG) {
                Log.d(TAG, "onCharacteristicWriteRequest " + characteristic.getUuid());
            }
            try {
                NotificationProto.NotificationMessageReq req = NotificationProto.NotificationMessageReq.parseFrom(value);
                if (mLastPacketSeq == -1) {
                    onReceiveNewMessage(req);
                }
                if (isValidPacket(req)) {
                    mPendingQueue.add(req);
                    mLastPacketSeq = req.getUint32Seq();
                    sendResponse(device, characteristic, requestId, Constants.RETCODE_NEXT_PACKET);
                } else {
                    sendResponse(device, characteristic, requestId, Constants.RETCODE_ERROR);
                    reset();
                }
                if (isFullMessageReceived()) {
                    if(parseMessage()) {
                        sendResponse(device, characteristic, requestId, Constants.RETCODE_MSG_FINISHED);
                    } else {
                        sendResponse(device, characteristic, requestId, Constants.RETCODE_ERROR);
                    }
                }
            } catch (Exception e) {
                if (Config.DEBUG) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    };

    public void destroy() {
        mContext = null;
        mBluetoothGattServer.clearServices();
        mBluetoothGattServer.close();
        mBluetoothGattServer = null;
        mPendingQueue.clear();
        mPendingQueue = null;
    }
}
