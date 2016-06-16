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

import java.util.Arrays;
import java.util.UUID;

/**
 * @author shunix
 * @since 2016/6/12
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class BluetoothServerProcessor {
    private final static String TAG = BluetoothServerProcessor.class.getSimpleName();

    private Context mContext;
    private BluetoothGattServer mBluetoothGattServer;
    private byte[] mBuffer = new byte[2560];
    private int mMessageId;
    private int mPacketCount;
    private int mLastPacketSeq; // seq of last received packet
    private int mCurrentPos; // current position in mBuffer

    public BluetoothServerProcessor(Context context) {
        mContext = context;
        mCurrentPos = 0;
        mLastPacketSeq = 0;
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
                if (req != null) {
                    if (mPacketCount == 0) {
                        mMessageId = req.getUint32Id();
                        mPacketCount = req.getUint32Count();
                    } else {
                        // Check if it's the packets of same message
                        if (req.getUint32Id() != mMessageId) {
                            // clear current status
                            mMessageId = req.getUint32Id();
                            mPacketCount = req.getUint32Count();
                            mLastPacketSeq = 0;
                            mCurrentPos = 0;
                            if (Config.DEBUG) {
                                Log.d(TAG, "packet incomplete, dropped");
                            }
                        }
                    }
                    int seq = req.getUint32Seq();
                    if (seq == mLastPacketSeq + 1) {
                        byte[] payload = req.getBytesPayload().toByteArray();
                        if (payload != null) {
                            for (int i = 0; i < payload.length; ++i) {
                                if (mCurrentPos <= mBuffer.length - 1) {
                                    mBuffer[mCurrentPos] = payload[i];
                                    mCurrentPos++;
                                }
                            }
                        }
                    }
                    mLastPacketSeq = seq;
                    if (mLastPacketSeq + 1 == mPacketCount) {
                        // get full packet
                        byte[] fullMessage = Arrays.copyOfRange(mBuffer, 0, mCurrentPos - 1);
                        NotificationProto.MarshalledNotificationMessage marshalledNotificationMessage = NotificationProto.MarshalledNotificationMessage.parseFrom(fullMessage);
                        if (marshalledNotificationMessage != null) {
                            if (Config.DEBUG) {
                                Log.d(TAG, "onCharacteristicWriteRequest get full message, message id: " + marshalledNotificationMessage.getUint32Id());
                            }
                        }
                    }
                }
            } catch (Exception e) {
                if (Config.DEBUG) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    };
}
