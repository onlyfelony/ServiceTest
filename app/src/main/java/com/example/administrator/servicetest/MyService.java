package com.example.administrator.servicetest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class MyService extends Service {
    private static final String TAG = "MyService";

    private DownloadBinder mbinder = new DownloadBinder();

    class DownloadBinder extends Binder {

        public void startDownload(){

            Log.d(TAG, "startDownload executed");
        }

        public int getProgress(){
            Log.d(TAG, "getProgress  executed");
            return 10;
        }

    }



    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind  executed");

        return mbinder;

    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate  executed");


        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);


        if(Build.VERSION.SDK_INT>=26) {
            NotificationChannel channel = new NotificationChannel("MyChannelId", "myChannel", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);//开启闪光灯
            channel.enableVibration(true);//开启震动
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, "MyChannelId")
                    .setContentTitle("This is content title")
                    .setContentText("This is content text")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setLights(Color.YELLOW,1000,2000)//设置闪光灯亮1s暗2s
                    .setVibrate(new long []{0,1000,2000,3000})//设置震动频率
                    .setAutoCancel(true)//点击通知后，通知自动取消
                    .setContentIntent(pendingIntent)
                    .build();
            startForeground(1,notification);

        }else {

            Notification   notification = new NotificationCompat.Builder(this, null)
                    .setContentTitle("This is content title")
                    .setContentText("This is content text")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setLights(Color.YELLOW,1000,2000)//设置闪光灯亮1s暗2s
                    .setVibrate(new long []{0,1000,2000,3000})//设置震动频率
                    .setAutoCancel(true)//点击通知后，通知自动取消
                    .setContentIntent(pendingIntent)
                    .build();
            startForeground(1,notification);
        }



    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand executed");

        new Thread(new Runnable() {
            @Override
            public void run() {
                //处理任务的逻辑



                stopSelf();

            }
        }).start();

        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy executed");
        super.onDestroy();
    }
}
