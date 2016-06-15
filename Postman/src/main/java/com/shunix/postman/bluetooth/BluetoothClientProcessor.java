package com.shunix.postman.bluetooth;

import android.bluetooth.*;
import android.content.Context;
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

    private BluetoothDevice mDevice;
    private Context mContext;
    private NotificationQueue mQueue;
    private BluetoothGatt mBluetoothGatt;
    private BluetoothGattCharacteristic mBluetoothGattCharacteristic;

    public BluetoothClientProcessor(Context context, BluetoothDevice device, NotificationQueue queue) {
        mDevice = device;
        mContext = context;
        mQueue = queue;
    }

    public void process() {
        if (mContext != null && mDevice != null) {
            if (mBluetoothGatt == null) {
                mBluetoothGatt = mDevice.connectGatt(mContext, false, mBluetoothGattCallback);
            } else {
                mBluetoothGatt.connect();
            }
            sendPacket();
        }
    }

    private void sendPacket() {
        if (mQueue != null && !mQueue.isEmpty() && mBluetoothGatt != null) {
            NotificationEntity entity = mQueue.peek();
            NotificationProto.NotificationMessageReq req = entity.marshal();
            if (mBluetoothGattCharacteristic != null) {
                mBluetoothGattCharacteristic.setValue(req.toByteArray());
                mBluetoothGatt.writeCharacteristic(mBluetoothGattCharacteristic);
            }
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
