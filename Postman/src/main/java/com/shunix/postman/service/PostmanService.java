package com.shunix.postman.service;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
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

    private NotificationQueue mNotificationQueue;
    private BluetoothDevice mBluetoothDevice;

    private IBinder mBinder = new IPostmanInterface.Stub() {
        @Override
        public void connectDevice(BluetoothDevice device) throws RemoteException {
            mBluetoothDevice = device;
        }
    };

    @Override
    public void onCreate() {
        if (Config.DEBUG) {
            Log.d(TAG, "onCreate");
        }
        super.onCreate();
        mNotificationQueue = new NotificationQueue(getApplicationContext());
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if (Config.DEBUG) {
            Log.d(TAG, "onNotificationPosted");
        }
        super.onNotificationPosted(sbn);
        mNotificationQueue.onGetNotification(sbn);
    }
}
