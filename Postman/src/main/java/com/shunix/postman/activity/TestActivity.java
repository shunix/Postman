package com.shunix.postman.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import com.shunix.postman.R;
import com.shunix.postman.bluetooth.BluetoothServerProcessor;
import com.shunix.postman.service.IPostmanInterface;
import com.shunix.postman.service.PostmanService;
import com.shunix.postman.util.BluetoothUtils;
import com.shunix.postman.util.CommonUtils;
import com.shunix.postman.util.Config;

/**
 * @author shunix
 * @since 2016/6/7
 */
public class TestActivity extends Activity implements View.OnClickListener {
    private final static String TAG = TestActivity.class.getSimpleName();
    private IPostmanInterface mIPostmanInterface;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mIPostmanInterface = IPostmanInterface.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.button:
                Intent intent = new Intent(this, PostmanService.class);
                intent.setAction(PostmanService.ACTION_CONNECT_DEVICE);
                bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.button2:
                Notification.Builder builder = new Notification.Builder(this);
                builder.setContentTitle("Test Notification")
                        .setContentText(String.valueOf(System.currentTimeMillis()))
                        .setSmallIcon(android.R.drawable.ic_lock_idle_alarm);
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(1, builder.build());
                break;
            case R.id.button3:
                CommonUtils.checkNotificationPermission(this, getPackageName());
                break;
            case R.id.button4:
                if (BluetoothUtils.isBLESupported(this)) {
                    BluetoothAdapter.LeScanCallback callback = new BluetoothAdapter.LeScanCallback() {
                        @Override
                        public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
                            if (TextUtils.isEmpty(bluetoothDevice.getName())) {
                                return;
                            }
                            if (Config.DEBUG) {
                                Log.d(TAG, "onLeScan " + bluetoothDevice.getName() + " " + bluetoothDevice.getAddress());
                            }
                            if (bluetoothDevice.getName().equals("Postman")) {
                                BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
                                final BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
                                bluetoothAdapter.stopLeScan(this);
                                try {
                                    mIPostmanInterface.connectDevice(bluetoothDevice);
                                } catch (Exception e) {
                                    if (Config.DEBUG) {
                                        Log.e(TAG, e.getMessage());
                                    }
                                }
                            }
                        }
                    };
                    BluetoothUtils.scanBLEDevices(this, callback, 10000);
                }
                break;
            case R.id.button5:
                if (BluetoothUtils.isBLESupported(this)) {
                    BluetoothServerProcessor processor = new BluetoothServerProcessor(this);
                    processor.process();
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
    }
}
