package com.example.arafathossain.icare;

import android.app.Application;

/**
 * Created by Arafat Hossain on 6/14/2015.
 */
public class DatabaseApplication extends Application {
    private static DatabaseHelper databaseHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        databaseHelper = new DatabaseHelper(this);
    }
    public static DatabaseHelper getDatabase(){
        return databaseHelper;
    }
}
