package com.shunix.postman.core;

import android.service.notification.StatusBarNotification;

/**
 * @author shunix
 * @since 2016/6/7
 */
public interface INotificationObserver {
    void onGetNotification(StatusBarNotification notification);
}
