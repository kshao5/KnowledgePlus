package com.example.knowledgeplus.SendNotificationPack;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.knowledgeplus.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

// keeps the service for 10 seconds after app is closed
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String title, message, receiver;
    private static final String CHANNEL_NAME = "Main Notifications";
    private static final String CHANNEL_DESCRIPTION = "Notification description";
    private static final String CHANNEL_ID = "MyNotificationChannel";
    // once the message is received from another device
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        title = remoteMessage.getData().get("Title");
        message = remoteMessage.getData().get("Message");
        Log.d("messagingservice", title);
        Log.d("messagingservice", message);
        int NOTIFICATION_ID = 234;
        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(CHANNEL_DESCRIPTION);
            notificationChannel.enableVibration(true);
            notificationChannel.enableLights(true);
            notificationChannel.canShowBadge();
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notificationChannel.setLightColor(getResources().getColor(R.color.purple_700));
            manager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setChannelId("my_channel_01");
        // show the notification on the screen
        manager.notify(9001, builder.build());
    }
}
