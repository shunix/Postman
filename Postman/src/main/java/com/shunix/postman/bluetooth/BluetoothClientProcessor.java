package com.shunix.postman.bluetooth;

import android.bluetooth.*;
import android.content.Context;
import android.util.Log;
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

    public BluetoothClientProcessor(Context context, BluetoothDevice device) {
        mDevice = device;
        mContext = context;
    }

    public void process() {
        if (mContext != null && mDevice != null) {
            mDevice.connectGatt(mContext, false, mBluetoothGattCallback);
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
                for(BluetoothGattService service : list) {
                    if (Config.DEBUG) {
                        Log.d(TAG, "onServiceDiscovered " + service.getUuid());
                    }
                    for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
                        if (Config.DEBUG) {
                            Log.d(TAG, "BluetoothGattCharacteristic " + characteristic.toString());
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

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
        }
    };
}
