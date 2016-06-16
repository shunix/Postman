package com.shunix.postman.bluetooth;

import android.bluetooth.*;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import com.google.protobuf.ByteString;
import com.shunix.postman.R;
import com.shunix.postman.core.NotificationEntity;
import com.shunix.postman.core.NotificationQueue;
import com.shunix.postman.proto.NotificationProto;
import com.shunix.postman.util.Config;

import java.util.Arrays;
import java.util.List;

/**
 * @author shunix
 * @since 2016/6/11
 */
public class BluetoothClientProcessor {
    private final static String TAG = BluetoothClientProcessor.class.getSimpleName();
    private final static int INTERVAL = 30000;
    private final static int PACKET_INTERVAL = 50;
    private final static int MAX_PAYLOAD_SIZE = 12;

    private BluetoothDevice mDevice;
    private Context mContext;
    private NotificationQueue mQueue;
    private BluetoothGatt mBluetoothGatt;
    private BluetoothGattCharacteristic mBluetoothGattCharacteristic;
    private HandlerThread mHandlerThread;
    private Handler mHandler;
    private boolean mIsSending; // indicate whether the processor is sending packets

    public BluetoothClientProcessor(Context context, BluetoothDevice device, NotificationQueue queue) {
        if (Config.DEBUG) {
            Log.d(TAG, "onCreate");
        }
        mDevice = device;
        mContext = context;
        mQueue = queue;
        mHandlerThread = new HandlerThread(TAG);
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
    }

    public void process() {
        if (Config.DEBUG) {
            Log.d(TAG, "process");
        }
        sendPacket();
    }

    private void sendPacket() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mQueue != null && !mQueue.isEmpty() && !mIsSending) {
                    if (mContext != null && mDevice != null) {
                        if (mBluetoothGatt == null) {
                            mBluetoothGatt = mDevice.connectGatt(mContext, false, mBluetoothGattCallback);
                        } else {
                            mBluetoothGatt.connect();
                        }
                    }
                    mIsSending = true;
                    NotificationEntity entity = mQueue.peek();
                    if (entity != null) {
                        splitPacketAndSend(entity.marshal());
                    }
                    mIsSending = false;
                }
                mHandler.postDelayed(this, INTERVAL);
            }
        });
    }

    /**
     * This method does not check mBluetoothGatt, mContext and mQueue for performance
     *
     * @param message
     */
    private void splitPacketAndSend(NotificationProto.MarshalledNotificationMessage message) {
        int id = message.getUint32Id();
        byte[] payload = message.toByteArray();
        int length = payload.length;
        int seq = 0;
        int count = (length % MAX_PAYLOAD_SIZE != 0) ? (length / MAX_PAYLOAD_SIZE + 1) : (length / MAX_PAYLOAD_SIZE);
        for (int i = 0; i < length; i += MAX_PAYLOAD_SIZE) {
            byte[] packetPayload = Arrays.copyOfRange(payload, i, Math.min(i + MAX_PAYLOAD_SIZE, length - 1));
            NotificationProto.NotificationMessageReq.Builder builder = NotificationProto.NotificationMessageReq.newBuilder();
            builder.setUint32Id(id).setUint32Count(count).setUint32Seq(seq).setBytesPayload(ByteString.copyFrom(packetPayload));
            NotificationProto.NotificationMessageReq req = builder.build();
            if (mBluetoothGattCharacteristic != null) {
                mBluetoothGattCharacteristic.setValue(req.toByteArray());
                mBluetoothGatt.writeCharacteristic(mBluetoothGattCharacteristic);
            }
            seq++;
            try {
                Thread.sleep(PACKET_INTERVAL);
            } catch (InterruptedException e) {
                if (Config.DEBUG) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    public void destroy() {
        mDevice = null;
        mContext = null;
        mQueue = null;
        mBluetoothGatt.disconnect();
        mBluetoothGattCharacteristic = null;
        mHandlerThread.quit();
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
        if (Config.DEBUG) {
            Log.d(TAG, "onDestroy");
        }
    }

    BluetoothGattCallback mBluetoothGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                if (Config.DEBUG) {
                    Log.d(TAG, "onConnectionStateChange STATE_CONNECTED");
                }
                gatt.discoverServices();
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            List<BluetoothGattService> list = gatt.getServices();
            if (list != null && list.size() > 0) {
                for (BluetoothGattService service : list) {
                    if (Config.DEBUG) {
                        Log.d(TAG, "onServiceDiscovered " + service.getUuid());
                    }
                    for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
                        if (Config.DEBUG) {
                            Log.d(TAG, "BluetoothGattCharacteristic " + characteristic.getUuid());
                        }
                        if (characteristic.getUuid().toString().equals(mContext.getString(R.string.characteristic_uuid))) {
                            mBluetoothGattCharacteristic = characteristic;
                            break;
                        }
                    }
                }
            }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
        }
    };
}
