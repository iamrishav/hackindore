package com.example.shailendraweb.drinkshopserver.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.example.shailendraweb.drinkshopserver.R;
import com.example.shailendraweb.drinkshopserver.Utils.NotificationHelper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

public class MyFirebaseMessaging extends FirebaseMessagingService {

    //ctrl o

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData() != null)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                sendNotificationAPI26(remoteMessage);
            else
                sendNotification(remoteMessage);
        }


    }

    private void sendNotification(RemoteMessage remoteMessage) {
        //get information from msg
        Map<String , String> data = remoteMessage.getData();
        String title = data.get("title");
        String message = data.get("message");

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri);

        NotificationManager noti =(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        noti.notify(new Random().nextInt(),builder.build());
    }

    private void sendNotificationAPI26(RemoteMessage remoteMessage) {

        //get information from msg
        Map<String , String> data = remoteMessage.getData();
        String title = data.get("title");
        String message = data.get("message");

        //From API level 23 (Android Oreo ), we need implement notification Channel
        NotificationHelper helper;
        Notification.Builder builder;

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        helper = new NotificationHelper(this);
        builder = helper.getDrinkShopNotification(title,message,defaultSoundUri);

        helper.getManager().notify(new Random().nextInt(),builder.build());


    }
}
