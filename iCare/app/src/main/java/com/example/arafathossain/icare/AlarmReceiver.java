package com.example.arafathossain.icare;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("status","receive");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(intent.getStringExtra("title"));
        builder.setContentText(intent.getStringExtra("menu"));
        builder.setSmallIcon(R.mipmap.icare_icon);
        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int)System.currentTimeMillis(),notification);
    }
}
