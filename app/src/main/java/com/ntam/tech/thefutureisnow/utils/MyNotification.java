package com.ntam.tech.thefutureisnow.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.ntam.tech.thefutureisnow.R;

/**
 * Created by bassiouny on 19/03/18.
 */

public class MyNotification {

    private Context context;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;

    public MyNotification(Context context,String title,String message) {
        this.context = context;
        mBuilder =
                new NotificationCompat.Builder(context)
                        .setContentTitle(title)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_LIGHTS)
                        .setContentText(message);
        if (mNotificationManager == null)
            mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void showNotification() {
        mNotificationManager.notify(1, mBuilder.build());
    }
}
