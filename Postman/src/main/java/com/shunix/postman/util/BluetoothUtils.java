package com.shunix.postman.util;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;

/**
 * @author shunix
 * @since 2016/6/10
 */
public class BluetoothUtils {
    private final static String TAG = BluetoothUtils.class.getSimpleName();

    public static boolean isBLESupported(Context context) {
        if (context == null) {
            return false;
        }
        boolean result = context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
        if (Config.DEBUG) {
            Log.d(TAG, "isBLESupported: " + result);
        }
        return result;
    }

    public static boolean checkBluetoothEnabled(Context context) {
        if (context != null) {
            BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
            if (bluetoothManager != null) {
                BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
                if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    context.startActivity(intent);
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    public static void scanBLEDevices(Context context, final BluetoothAdapter.LeScanCallback callback, long timeout) {
        if (context != null && callback != null) {
            if (checkBluetoothEnabled(context)) {
                BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
                final BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
                Handler timeoutHandler = new Handler(context.getMainLooper());
                timeoutHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bluetoothAdapter.stopLeScan(callback);
                    }
                }, timeout);
                bluetoothAdapter.startLeScan(callback);
            }
        }
    }
}
