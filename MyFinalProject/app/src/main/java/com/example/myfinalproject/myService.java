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


    public static final String CHANNEL_ID = "ForegroundServiceChannel";

    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String date="";
        String time="";
        boolean temp;

        if (intent != null) {
            try {
                date = intent.getStringExtra("Date");
                time = intent.getStringExtra("Time");
                temp = intent.getAction().equals("StopService");
                if(temp){
                    stopForeground(true);
                    stopSelf();
                }

            }catch (Exception e){

            }

        }


        createNotificationChannel();

        PendingIntent pendingIntent = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast
                    (getApplicationContext(), 0, intent, PendingIntent.FLAG_MUTABLE);
        }
        else {
            pendingIntent = PendingIntent.getActivity
                    (getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        }

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Shopping list App")
                .setContentText("You have to go to shop at " + date + ", " + time)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        //do heavy work on a background thread
        //stopSelf();
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

    private void createNotificationChannel() {
        //This is for channel create or not depend on SDK var
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
    }


//    private volatile boolean shouldStop = false;
//    int foregroundServiceInt = 0;
//    public static final int CHANNEL_ID_INT = 1;
//    NotificationManager mNotiMgr;
//    Notification.Builder mNotifyBuilder;
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onCreate() {
//        initForeground();
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//
//        if (intent != null) {
//            try {
//                boolean temp = intent.getAction().equals("StopService");
//                if(temp){
////                    stopForeground(true);
////                    stopSelf();
//                    shouldStop = true;
//                }else{
//                    shouldStop = false;
//                }
//
//            }catch (Exception e){
//
//            }
//
//        }
//
//
//
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                Intent localIntent = new Intent();
//                localIntent.setAction("Counter");
//
//
//                while(shouldStop == false){
//
//                    foregroundServiceInt++;
//                    localIntent.putExtra("foregroundServiceInt", foregroundServiceInt);
//                    sendBroadcast(localIntent);
//                    updateNotification(String.valueOf(foregroundServiceInt));
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                    notificationManager.cancel(startId);
//                }
//            }
//        }).start();
//
//        return START_NOT_STICKY;
//    }
//
//
//    private void initForeground(){
//        String CHANNEL_ID = "my_channel_01";
//        if (mNotiMgr==null)
//            mNotiMgr= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
//                "My main channel",
//                NotificationManager.IMPORTANCE_DEFAULT);
//        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE))
//                .createNotificationChannel(channel);
//// Create an explicit intent for an Activity in your app
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//        mNotifyBuilder = new Notification.Builder(this, CHANNEL_ID)
//                .setContentTitle("Testing Notification...")
//                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                .setContentIntent(pendingIntent);
//        startForeground(CHANNEL_ID_INT, updateNotification(Integer.toString(0)));
//    }
//
//    public Notification updateNotification(String details){
//        mNotifyBuilder.setContentText(details).setOnlyAlertOnce(false);
//        Notification noti = mNotifyBuilder.build();
//        noti.flags = Notification.FLAG_ONLY_ALERT_ONCE;
//        mNotiMgr.notify(CHANNEL_ID_INT, noti);
//        return noti;
//    }
//

}
