package com.shunix.postman.service;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.shunix.postman.bluetooth.BluetoothClientProcessor;
import com.shunix.postman.core.NotificationQueue;
import com.shunix.postman.util.Config;

/**
 * A descent of NotificationListenerService, used for monitoring system notifications
 *
 * @author shunix
 * @since 2016/6/7
 */
public class PostmanService extends NotificationListenerService {
    private final static String TAG = PostmanService.class.getSimpleName();
    public final static String ACTION_CONNECT_DEVICE = "com.shunix.postman.connectdevice";

    private NotificationQueue mNotificationQueue;
    private BluetoothDevice mBluetoothDevice;
    private BluetoothClientProcessor mBluetoothClientProcessor;

    private IBinder mBinder = new com.shunix.postman.service.IPostmanInterface.Stub() {
        @Override
        public void connectDevice(BluetoothDevice device) throws RemoteException {
            if (Config.DEBUG) {
                Log.d(TAG, "connectDevice " + device.getName());
            }
            mBluetoothDevice = device;
            if (mBluetoothClientProcessor != null) {
                mBluetoothClientProcessor.destroy();
            }
            mBluetoothClientProcessor = new BluetoothClientProcessor(PostmanService.this, mBluetoothDevice, mNotificationQueue);
            mBluetoothClientProcessor.process();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        if (Config.DEBUG) {
            Log.d(TAG, "onCreate");
        }
        mNotificationQueue = new NotificationQueue(getApplicationContext());
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (intent.getAction().equals(ACTION_CONNECT_DEVICE)) {
            return mBinder;
        } else {
            return super.onBind(intent);
        }
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if (Config.DEBUG) {
            Log.d(TAG, "onNotificationPosted");
        }
        mNotificationQueue.onGetNotification(sbn);
    }
}
