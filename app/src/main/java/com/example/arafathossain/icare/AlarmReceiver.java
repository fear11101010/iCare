package com.example.arafathossain.icare;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("status", "receive");
        if (intent.getAction().equalsIgnoreCase(DietInformation.ACTION_DIET)) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle(intent.getStringExtra("title"));
            builder.setContentText(intent.getStringExtra("menu"));
            builder.setSmallIcon(R.mipmap.icare_icon);
            builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            Notification notification = builder.build();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify((int) System.currentTimeMillis(), notification);
        }
        else if (intent.getAction().equalsIgnoreCase(VaccineDetail.ACTION_VACCINE)) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle(intent.getStringExtra("diseaseName"));
            builder.setContentText(intent.getStringExtra("vaccineName")+" dose "+intent.getStringExtra("dose"));
            builder.setSmallIcon(R.mipmap.icare_icon);
            builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            Notification notification = builder.build();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify((int) System.currentTimeMillis(), notification);
        }
    }
}
