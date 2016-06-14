package com.shunix.postman.util;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import com.shunix.postman.core.NotificationEntity;

import java.io.ByteArrayOutputStream;
import java.util.Random;

/**
 * Common utils.
 *
 * @author shunix
 * @since 2016/6/7
 */
public final class CommonUtils {
    private final static String TAG = CommonUtils.class.getSimpleName();
    private final static String NOTIFICATION_LISTENERS_ACTION = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
    private final static String NOTIFICATION_LISTENERS_KEY = "enabled_notification_listeners";

    public static Drawable getPackageIcon(Context context, String packageName) {
        if (context == null || TextUtils.isEmpty(packageName)) {
            return null;
        }
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return null;
        }
        try {
            return packageManager.getApplicationIcon(packageName);
        } catch (Exception e) {
            if (Config.DEBUG) {
                Log.e(TAG, e.getMessage());
            }
        }
        return null;
    }

    public static byte[] convertDrawableToByteArray(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            if (Config.DEBUG) {
                Log.e(TAG, e.getMessage());
            }
        } finally {
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (Exception e) {
                    if (Config.DEBUG) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            }
        }
        return null;
    }

    public static NotificationEntity generateNotificationEntity(Context context, StatusBarNotification statusBarNotification) {
        if (context != null && statusBarNotification != null) {
            NotificationEntity entity = new NotificationEntity();
            Random random = new Random();
            int delta = random.nextInt(1000);
            long timestamp = System.currentTimeMillis();
            entity.setId(timestamp + delta);
            if (!TextUtils.isEmpty(statusBarNotification.getPackageName())) {
                entity.setPackageName(statusBarNotification.getPackageName());
                Drawable packageIconDrawable = getPackageIcon(context, statusBarNotification.getPackageName());
                if (packageIconDrawable != null) {
                    byte[] bytes = convertDrawableToByteArray(packageIconDrawable);
                    if (bytes != null) {
                        entity.setIcon(bytes);
                    }
                }
            }
            entity.setTimestamp(statusBarNotification.getPostTime());
            Notification notification = statusBarNotification.getNotification();
            if (notification != null && notification.extras != null) {
                CharSequence title = notification.extras.getCharSequence(Notification.EXTRA_TITLE);
                CharSequence content = notification.extras.getCharSequence(Notification.EXTRA_TEXT);
                if (title != null) {
                    entity.setTitle(title.toString());
                }
                if (content != null) {
                    entity.setContent(content.toString());
                }
            }
            return entity;
        }
        return null;
    }

    public static void openNotificationAccessSettings(Context context) {
        if (context != null) {
            Intent intent = new Intent(NOTIFICATION_LISTENERS_ACTION);
            context.startActivity(intent);
        }
    }

    public static boolean isGrantedNotificationAccess(Context context, String packageName) {
        boolean result = false;
        if (context != null && !TextUtils.isEmpty(packageName)) {
            String grantedList = Settings.Secure.getString(context.getContentResolver(), NOTIFICATION_LISTENERS_KEY);
            if (Config.DEBUG) {
                if (grantedList != null) {
                    Log.d(TAG, grantedList);
                }
            }
            if (grantedList != null) {
                result = grantedList.contains(packageName);
            }
        }
        if (Config.DEBUG) {
            Log.d(TAG, "isGrantedNotificationAccess: " + result);
        }
        return result;
    }

    public static void checkNotificationPermission(Context context, String packageName) {
        if (context != null && !TextUtils.isEmpty(packageName)) {
            if (!isGrantedNotificationAccess(context, packageName)) {
                openNotificationAccessSettings(context);
            }
        }
    }
}
