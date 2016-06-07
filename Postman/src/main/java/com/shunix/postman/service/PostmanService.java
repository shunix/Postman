package com.shunix.postman.service;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

/**
 * A descent of NotificationListenerService, used for monitoring system notifications
 *
 * @author shunix
 * @since 2016/6/7
 */
public class PostmanService extends NotificationListenerService {
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
    }
}
