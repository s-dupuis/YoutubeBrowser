package com.kixot.youtubebrowser;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.util.Random;

public class NotificationsManager {

    private final static String CHANNEL = "DOWNLOADS";

    private static int generateNotificationId () {
        return new Random().nextInt(1000000);
    }

    private static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);

            NotificationChannel channel = new NotificationChannel(CHANNEL, name, NotificationManager.IMPORTANCE_LOW);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

    public static int sendNotification(Context context, String title, String content) {
        createNotificationChannel(context);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL)
                .setSmallIcon(R.drawable.youtube)
                .setContentTitle(title)
                .setContentText(content)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setAutoCancel(true);

        int notificationId = generateNotificationId();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(notificationId, builder.build());

        return notificationId;
    }

}
