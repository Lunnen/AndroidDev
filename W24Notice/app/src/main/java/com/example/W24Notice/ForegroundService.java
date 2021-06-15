package com.example.W24Notice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class ForegroundService extends Service {
    public static final String NOTIFY_ID = "IDChannel";

    public ForegroundService() {
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        createNotificationChannel();

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Bundle editBundle = intent.getBundleExtra("myInfo");
        String myTitle = editBundle.getString("title");
        String myMessage = editBundle.getString("message");
        String myImage = editBundle.getString("image");
        int imageValue = getResources().getIdentifier("@drawable/" + myImage, null, getPackageName());

        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification = new Notification.Builder(this, NOTIFY_ID)
                    .setContentTitle(myTitle)
                    .setContentText(myMessage)
                    .setSmallIcon(R.drawable.ic_launcher_foreground) //icon
                    .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),imageValue))
                    .setContentIntent(pendingIntent)
                    .build();
        }

        Log.d("gotten", "onStartCommand: " + editBundle.getString("title"));
        Log.d("gotten", "onStartCommand: " + editBundle.getString("message"));
        Log.d("gotten", "onStartCommand: " + myImage);
        Log.d("gotten", "onStartCommand: " + imageValue);

        startForeground(5, notification); // need a unique ID here cant be 0


        return super.onStartCommand(intent, flags, startId);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    NOTIFY_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

