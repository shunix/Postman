package com.shunix.postman.core;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.google.protobuf.ByteString;
import com.shunix.postman.proto.NotificationProto;
import com.shunix.postman.util.Config;

import java.util.Date;

/**
 * Wrapper for a notification.
 *
 * @author shunix
 * @since 2016/6/7
 */
public class NotificationEntity {
    private final static String TAG = NotificationEntity.class.getSimpleName();
    private final static String DIVIDER = "\n";

    private byte mId;
    private String mApplicationName;
    private long mTimestamp;
    private String mTitle;
    private String mContent;

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
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

    public byte getId() {
        return mId;
    }

    public void setId(byte id) {
        mId = id;
    }

    public String getApplicationName() {
        return mApplicationName;
    }

    public void setApplicationName(String applicationName) {
        mApplicationName = applicationName;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Id: ").append(mId).append(DIVIDER);
        if (!TextUtils.isEmpty(mApplicationName)) {
            builder.append("Application Name: ").append(mApplicationName).append(DIVIDER);
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

    public NotificationProto.MarshalledNotificationMessage marshal() {
        NotificationProto.MarshalledNotificationMessage.Builder builder = NotificationProto.MarshalledNotificationMessage.newBuilder();
        builder.setUint32Id(mId);
        if (!TextUtils.isEmpty(mApplicationName)) {
            builder.setStrAppname(mApplicationName);
        }
        builder.setUint64Timestamp(mTimestamp);
        if (!TextUtils.isEmpty(mTitle)) {
            builder.setStrTitle(mTitle);
        }
        if (!TextUtils.isEmpty(mContent)) {
            builder.setStrContent(mContent);
        }
        NotificationProto.MarshalledNotificationMessage message = builder.build();
        if (Config.DEBUG) {
            Log.d(TAG, "marshalled size: " + message.getSerializedSize());
        }
        return message;
    }
}
