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
import com.shunix.postman.util.Config;

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

    public BluetoothServerProcessor(Context context) {
        mContext = context;
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
                        ParcelUuid parcelUuid = new ParcelUuid(UUID.fromString(mContext.getString(R.string.uuid)));
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