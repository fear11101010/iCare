package com.example.arafathossain.icare;

import android.app.AlarmManager;
import android.app.Application;


public class ApplicationMain extends Application {
    private static DatabaseHelper databaseHelper;
    private static AlarmManager alarmManager;

    @Override
    public void onCreate() {
        super.onCreate();
        databaseHelper = new DatabaseHelper(this);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    public static DatabaseHelper getDatabase() {
        return databaseHelper;
    }

    public static AlarmManager getAlarmManager() {
        return alarmManager;
    }
}
