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
import com.shunix.postman.util.Constants;

import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author shunix
 * @since 2016/6/11
 */
public class BluetoothClientProcessor {
    private final static String TAG = BluetoothClientProcessor.class.getSimpleName();
    private final static int INTERVAL = 30000;
    private final static int MAX_PAYLOAD_SIZE = 12;

    private BluetoothDevice mDevice;
    private Context mContext;
    private NotificationQueue mQueue;
    private BluetoothGatt mBluetoothGatt;
    private BluetoothGattCharacteristic mBluetoothGattCharacteristic;
    private HandlerThread mHandlerThread;
    private Handler mHandler;
    private Queue<NotificationProto.NotificationMessageReq> mPendingQueue;

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
        mPendingQueue = new LinkedBlockingQueue<>();
    }

    public void process() {
        if (Config.DEBUG) {
            Log.d(TAG, "process");
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mQueue != null && !mQueue.isEmpty() && mPendingQueue.isEmpty()) {
                    if (mContext != null && mDevice != null) {
                        if (mBluetoothGatt == null) {
                            mBluetoothGatt = mDevice.connectGatt(mContext, false, mBluetoothGattCallback);
                        } else {
                            mBluetoothGatt.connect();
                        }
                    }
                    NotificationEntity entity = mQueue.peek();
                    if (entity != null) {
                        fragmentMessage(entity.marshal());
                    }
                }
                mHandler.postDelayed(this, INTERVAL);
            }
        });
    }

    private void sendPacket() {
        if (!mPendingQueue.isEmpty()) {
            if (mBluetoothGattCharacteristic != null) {
                byte[] value = mPendingQueue.peek().toByteArray();
                if (value != null && value.length > 0) {
                    mBluetoothGattCharacteristic.setValue(value);
                }
            }
        }
    }

    private void removeMessage(NotificationProto.NotificationMessageRsp rsp) {
        if (rsp != null && rsp.hasUint32Id()) {
            mQueue.remove(rsp.getUint32Id());
        }
    }

    /**
     * This method does not check mBluetoothGatt, mContext and mQueue for performance
     *
     * @param message
     */
    private void fragmentMessage(NotificationProto.MarshalledNotificationMessage message) {
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
            seq++;
            mPendingQueue.add(req);
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
        mPendingQueue.clear();
        mPendingQueue = null;
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
            byte[] value = characteristic.getValue();
            if (value != null && value.length > 0) {
                try {
                    NotificationProto.NotificationMessageRsp rsp = NotificationProto.NotificationMessageRsp.parseFrom(value);
                    if (rsp != null) {
                        if (rsp.hasUint32RetCode()) {
                            int retCode = rsp.getUint32RetCode();
                            switch (retCode) {
                                case Constants.RETCODE_NEXT_PACKET:
                                    sendPacket();
                                    break;
                                case Constants.RETCODE_MSG_FINISHED:
                                    removeMessage(rsp);
                                    break;
                                case Constants.RETCODE_ERROR:
                                    mPendingQueue.clear();
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                } catch (Exception e) {
                    if (Config.DEBUG) {
                        Log.d(TAG, e.getMessage());
                    }
                }
            }
        }
    };
}
