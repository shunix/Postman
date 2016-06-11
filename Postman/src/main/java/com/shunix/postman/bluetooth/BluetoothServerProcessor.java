package com.shunix.postman.bluetooth;

import android.bluetooth.*;
import android.content.Context;

/**
 * @author shunix
 * @since 2016/6/12
 */
public class BluetoothServerProcessor {
    private final static String TAG = BluetoothServerProcessor.class.getSimpleName();

    private Context mContext;
    private BluetoothGattServer mBluetoothGattServer;

    public BluetoothServerProcessor(Context context) {
        mContext = context;
    }

    public void process() {
        if (mContext != null) {
            if (mBluetoothGattServer == null) {
                BluetoothManager bluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
                if (bluetoothManager != null) {
                    mBluetoothGattServer = bluetoothManager.openGattServer(mContext, mBluetoothGattServerCallback);
                }
            }
        }
    }

    BluetoothGattServerCallback mBluetoothGattServerCallback = new BluetoothGattServerCallback() {
        @Override
        public void onConnectionStateChange(BluetoothDevice device, int status, int newState) {
            super.onConnectionStateChange(device, status, newState);
        }

        @Override
        public void onServiceAdded(int status, BluetoothGattService service) {
            super.onServiceAdded(status, service);
        }

        @Override
        public void onCharacteristicReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicReadRequest(device, requestId, offset, characteristic);
        }

        @Override
        public void onCharacteristicWriteRequest(BluetoothDevice device, int requestId, BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
            super.onCharacteristicWriteRequest(device, requestId, characteristic, preparedWrite, responseNeeded, offset, value);
        }
    };
}
