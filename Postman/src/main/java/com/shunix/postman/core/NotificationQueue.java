package com.shunix.postman.core;

import android.content.Context;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.shunix.postman.util.CommonUtils;
import com.shunix.postman.util.Config;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Queue for holding notifications
 *
 * @author shunix
 * @since 2016/6/7
 */
public class NotificationQueue implements INotificationObserver {
    private final static String TAG = NotificationQueue.class.getSimpleName();

    private Queue<NotificationEntity> mQueue;
    private Context mContext;

    public NotificationQueue(Context context) {
        mQueue = new ConcurrentLinkedQueue<>();
        mContext = context;
    }

    public boolean add(NotificationEntity entity) {
        if (entity != null) {
            return mQueue.add(entity);
        }
        return false;
    }

    public boolean addIfNotExist(NotificationEntity entity) {
        if (entity != null && !mQueue.contains(entity)) {
            return mQueue.add(entity);
        }
        return false;
    }

    public boolean remove(NotificationEntity entity) {
        if (entity != null) {
            return mQueue.remove(entity);
        }
        return false;
    }

    public NotificationEntity remove() {
        return mQueue.remove();
    }

    public boolean remove(int id) {
        Iterator<NotificationEntity> iterator = mQueue.iterator();
        while(iterator.hasNext()) {
            NotificationEntity entity = iterator.next();
            if (entity.getId() == id) {
                remove(entity);
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return mQueue.isEmpty();
    }

    public NotificationEntity peek() {
        return mQueue.peek();
    }

    public void clear() {
        mQueue.clear();
    }

    @Override
    public void onGetNotification(StatusBarNotification notification) {
        NotificationEntity entity = CommonUtils.generateNotificationEntity(mContext, notification);
        if (entity != null) {
            if (Config.DEBUG) {
                Log.d(TAG, entity.toString());
            }
            mQueue.add(entity);
        }
    }
}
