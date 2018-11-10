package com.map_movil.map_movil.api.notificacion;

import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.map_movil.map_movil.model.Notification;

public class FirebaseNotification extends FirebaseMessagingService {
    private static final String TAG = "FirebaseMsgService";

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("your token",s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getFrom());
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
        Uri uriSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder  notificationBuilder = new NotificationCompat.Builder(this, "0")
                .setContentTitle(notification.getStrTitle())
                .setContentText(notification.getStrDescription())
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setAutoCancel(true)
                .setSound(uriSound)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000 })
                /*.setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(""))*/
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        NotificationManagerCompat notificationManagerCompat  = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(0, notificationBuilder.build());
    }
}
