package com.map_movil.map_movil.api.notificacion;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.map_movil.map_movil.model.Notification;

import static android.support.v4.app.NotificationCompat.GROUP_ALERT_SUMMARY;

public class FirebaseNotification extends FirebaseMessagingService {
    private static final String TAG = "FirebaseMsgService";

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        //Log.e("your token",s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        //Log.d(TAG, "From: " + remoteMessage.getFrom());
        Notification notification = new Notification();
        notification.setStrId(remoteMessage.getFrom());
        notification.setStrTitle(remoteMessage.getNotification().getTitle());
        notification.setStrDescription(remoteMessage.getNotification().getBody());
        notification.setStrSomeValues(remoteMessage.getData().get("key"));

        showNotification(notification);

        /*
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }*/
    }


    private void showNotification(Notification notification){
        String GROUP_KEY_WORK_NOTIFICATION = "com.map_movil.map_movil";
        String CHANNEL_ID = "SIG-MAP";
        String CHANNEL_NAME = "CHANNEL NOTIFICATION MAP";
        Uri defaultUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        android.app.Notification notification1 = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setBadgeIconType(android.R.drawable.ic_dialog_info)
                //.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), android.R.drawable.ic_dialog_info))
                .setContentTitle(notification.getStrTitle())
                .setContentText(notification.getStrDescription())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(defaultUri)
                .setAutoCancel(true)
                .setVibrate(new long[] { 1000, 500, 500})
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setGroup(GROUP_KEY_WORK_NOTIFICATION)
                .setGroupSummary(true)
                .setGroupAlertBehavior(GROUP_ALERT_SUMMARY)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();

            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );

            channel.enableVibration(true);
            channel.setLightColor(Color.RED);
            channel.enableLights(true);
            channel.setSound(defaultUri, attributes);

            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0, notification1);
        //vibrator.vibrate(1000);
    }
}
