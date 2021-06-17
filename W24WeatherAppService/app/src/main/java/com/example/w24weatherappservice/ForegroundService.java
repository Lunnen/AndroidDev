package com.example.w24weatherappservice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

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
        //int imageValue = getResources().getIdentifier("@drawable/" + myImage, null, getPackageName());

        // Hardcoded test - String thisUrl = "http://openweathermap.org/img/wn/01d@2x.png";

        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification = new Notification.Builder(this, NOTIFY_ID)
                    .setContentTitle(myTitle)
                    .setContentText(myMessage)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    //.setLargeIcon(largeImage)
                    .setContentIntent(pendingIntent)
                    .build();


        }

        Log.d("gotten", "onStartCommand: " + editBundle.getString("title"));
        Log.d("gotten", "onStartCommand: " + editBundle.getString("message"));
        Log.d("gotten", "onStartCommand: " + myImage);
        //Log.d("gotten", "onStartCommand: " + imageValue);

        startForeground(1, notification);

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

