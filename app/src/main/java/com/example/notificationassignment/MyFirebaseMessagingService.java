package com.example.notificationassignment;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

//        UtilityApp.setFCMToken(s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String titleStr = remoteMessage.getData().get("title");
        String bodyStr = remoteMessage.getData().get("body");

        if (titleStr == null || titleStr.isEmpty())
            titleStr = remoteMessage.getNotification().getTitle();

        if (bodyStr == null || bodyStr.isEmpty())
            bodyStr = remoteMessage.getNotification().getBody();

        sendNotification(titleStr, bodyStr);
    }

    private void sendNotification(String messageTitle, String messageBody) {

        Intent intent = new Intent(this, HomePage.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "Que Notifications";

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_HIGH);

            // Configure the notification channel.
            notificationChannel.setDescription("My Notifications Service");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.MAGENTA);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
//            notificationChannel.setSound(defaultSoundUri);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(messageBody))
                .setSound(defaultSoundUri)
                //     .setPriority(Notification.PRIORITY_MAX)
                .setContentTitle(messageTitle)
                .setContentText(messageBody);

        notificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());

    }
}
