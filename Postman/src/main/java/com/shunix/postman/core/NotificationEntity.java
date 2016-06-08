package com.shunix.postman.core;

import android.text.TextUtils;

import java.util.Date;

/**
 * Wrapper for a notification.
 *
 * @author shunix
 * @since 2016/6/7
 */
public class NotificationEntity {
    private final static String DIVIDER = "\n";
    private byte[] mIcon;
    private String mPackageName;
    private long mTimestamp;
    private String mTitle;
    private String mContent;

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public byte[] getIcon() {
        return mIcon;
    }

    public void setIcon(byte[] icon) {
        mIcon = icon;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(String packageName) {
        mPackageName = packageName;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(long timestamp) {
        mTimestamp = timestamp;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (!TextUtils.isEmpty(mPackageName)) {
            builder.append("Package Name: ").append(mPackageName).append(DIVIDER);
        }
        if (!TextUtils.isEmpty(mTitle)) {
            builder.append("Title: ").append(mTitle).append(DIVIDER);
        }
        if (!TextUtils.isEmpty(mContent)) {
            builder.append("Content: ").append(mContent).append(DIVIDER);
        }
        builder.append("Timestamp: ").append(new Date(mTimestamp));
        return builder.toString();
    }
}
