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

import java.util.concurrent.atomic.AtomicInteger;

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

    private static AtomicInteger mMessageId;

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
        mMessageId = new AtomicInteger(0);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNotificationQueue.clear();
        mNotificationQueue = null;
        mBluetoothDevice = null;
        mBluetoothClientProcessor.destroy();
        mBluetoothClientProcessor = null;
        mMessageId.set(0);
    }

    public static byte generateMessageId() {
        int id = mMessageId.addAndGet(1);
        if (id > 128) {
            mMessageId.set(0);
            id = mMessageId.addAndGet(1);
        }
        return (byte) id;
    }
}
