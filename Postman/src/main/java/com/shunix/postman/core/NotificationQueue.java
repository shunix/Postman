package com.shunix.postman.core;

import android.content.Context;
import android.service.notification.StatusBarNotification;
import com.shunix.postman.util.CommonUtils;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Queue for holding notifications
 *
 * @author shunix
 * @since 2016/6/7
 */
public class NotificationQueue implements INotificationObserver{
    private Queue<NotificationEntity> mQueue;
    private Context mContext;

    public NotificationQueue(Context context) {
        mQueue = new LinkedBlockingQueue<>();
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

    @Override
    public void onGetNotification(StatusBarNotification notification) {
        NotificationEntity entity = CommonUtils.generateNotificationEntity(mContext, notification);
        if (entity != null) {
            mQueue.add(entity);
        }
    }
}
