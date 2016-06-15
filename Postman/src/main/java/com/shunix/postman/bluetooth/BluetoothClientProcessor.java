package com.shunix.postman.bluetooth;

import android.bluetooth.*;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import com.shunix.postman.R;
import com.shunix.postman.core.NotificationEntity;
import com.shunix.postman.core.NotificationQueue;
import com.shunix.postman.proto.NotificationProto;
import com.shunix.postman.util.Config;

import java.util.List;

/**
 * @author shunix
 * @since 2016/6/11
 */
public class BluetoothClientProcessor {
    private final static String TAG = BluetoothClientProcessor.class.getSimpleName();
    private final static int INTERVAL = 10000;

    private BluetoothDevice mDevice;
    private Context mContext;
    private NotificationQueue mQueue;
    private BluetoothGatt mBluetoothGatt;
    private BluetoothGattCharacteristic mBluetoothGattCharacteristic;
    private HandlerThread mHandlerThread;
    private Handler mHandler;

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
                if (mQueue != null && !mQueue.isEmpty()) {
                    if (mContext != null && mDevice != null) {
                        if (mBluetoothGatt == null) {
                            mBluetoothGatt = mDevice.connectGatt(mContext, false, mBluetoothGattCallback);
                        } else {
                            mBluetoothGatt.connect();
                        }
                    }
                    NotificationEntity entity = mQueue.peek();
                    NotificationProto.NotificationMessageReq req = entity.marshal();
                    if (mBluetoothGattCharacteristic != null) {
                        mBluetoothGattCharacteristic.setValue(req.toByteArray());
                        mBluetoothGatt.writeCharacteristic(mBluetoothGattCharacteristic);
                        if (Config.DEBUG) {
                            Log.d(TAG, "write notification of id " + entity.getId());
                        }
                    }
                }
                mHandler.postDelayed(this, INTERVAL);
            }
        });
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
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
        }
    };
}
