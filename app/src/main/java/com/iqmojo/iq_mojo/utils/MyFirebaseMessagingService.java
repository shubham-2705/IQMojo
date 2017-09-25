package com.iqmojo.iq_mojo.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.iqmojo.BuildConfig;
import com.iqmojo.R;
import com.iqmojo.iq_mojo.ui.activities.SplashActivity;

import android.app.PendingIntent;
import android.app.NotificationManager;

/**
 * Created by shubhamlamba on 25/09/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Create and show notification
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "From: " + remoteMessage.getFrom());
            Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        }

        //Calling method to generate notification
//        if (((BaseApplication) getApplication()).getmFirebaseRemoteConfig().getBoolean("TEST"))
        sendNotification(remoteMessage.getNotification());
    }

    private void sendNotification(RemoteMessage.Notification notification) {
        Intent intent = null;
//        if () {
//            intent = new Intent(this, SplashActivity.class);
//        } else
        intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.mobile_48)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.mobile_48))
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}