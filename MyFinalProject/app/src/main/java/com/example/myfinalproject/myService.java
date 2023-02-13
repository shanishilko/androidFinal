package com.example.myfinalproject;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class myService extends Service {
    public static final String CHANNEL_ID = "ServiceAlarmChannel";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String date="";
        String time="";

        if (intent != null) {
            try {
                date = intent.getStringExtra("Date");
                time = intent.getStringExtra("Time");

            }catch (Exception e){

            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = CHANNEL_ID;
            String description = CHANNEL_ID;
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }


        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Shopping list App")
                .setContentText("You need to go shopping on " + date + ", at " + time)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(null)
                .build();
        startForeground(1, notification);

        return START_NOT_STICKY;
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
