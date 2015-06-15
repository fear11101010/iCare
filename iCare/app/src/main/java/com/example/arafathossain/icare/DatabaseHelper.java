package com.example.arafathossain.icare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Arafat Hossain on 6/9/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "iCare";
    private static final String TABLE_NAME = "profile";
    private static final String COLUMN_PROFILE_NAME = "profile_name";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_CONTACT_NO = "contact_no";
    private static final String COLUMN_HEIGHT = "height";
    private static final String COLUMN_WEIGHT = "weight";
    private static final String COLUMN_BLOOD_GROUP = "blood_group";
    private static final String COLUMN_DATE_OF_BIRTH = "date_of_birth";
    private static final String COLUMN_GENDER = "gender";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
            "( " + COLUMN_PROFILE_NAME + " TEXT PRIMARY KEY NOT NULL," +
            COLUMN_USER_NAME + " TEXT," +
            COLUMN_EMAIL + " TEXT," +
            COLUMN_CONTACT_NO + " TEXT," +
            COLUMN_DATE_OF_BIRTH + " TEXT," +
            COLUMN_WEIGHT + " TEXT," +
            COLUMN_HEIGHT + " TEXT," +
            COLUMN_BLOOD_GROUP + " TEXT," +
            COLUMN_GENDER + " TEXT)";
    private static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_QUERY);
        db.execSQL(CREATE_TABLE_QUERY);
    }
    public ArrayList<String> getAllProfileName(){
        ArrayList<String> profileNames = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,new String[]{COLUMN_PROFILE_NAME},null,null,null,null,null);
        if (cursor.moveToFirst()){
            profileNames = new ArrayList<>();
            do {
                profileNames.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        return profileNames;
    }
    public Profile getProfileByName(String profileName){
        Profile userProfile = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,null,COLUMN_PROFILE_NAME+"=?",new String[]{profileName},null,null,null);
        if (cursor.moveToFirst()){
            do {
                userProfile = new Profile();
                userProfile.setBloodGroup(cursor.getString(cursor.getColumnIndex(COLUMN_BLOOD_GROUP)));
                userProfile.setContactNo(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT_NO)));
                userProfile.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
                userProfile.setDateOfBirth(cursor.getString(cursor.getColumnIndex(COLUMN_DATE_OF_BIRTH)));
                userProfile.setUserName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                userProfile.setWeight(cursor.getString(cursor.getColumnIndex(COLUMN_WEIGHT)));
                userProfile.setHeight(cursor.getString(cursor.getColumnIndex(COLUMN_HEIGHT)));
                userProfile.setGender(cursor.getString(cursor.getColumnIndex(COLUMN_GENDER)));
                userProfile.setProfileName(cursor.getString(cursor.getColumnIndex(COLUMN_PROFILE_NAME)));
            }while (cursor.moveToNext());
        }
        return userProfile;
    }
    public int addProfile(Profile profile){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PROFILE_NAME,profile.getProfileName());
        values.put(COLUMN_USER_NAME,profile.getUserName());
        values.put(COLUMN_EMAIL,profile.getEmail());
        values.put(COLUMN_CONTACT_NO,profile.getContactNo());
        values.put(COLUMN_DATE_OF_BIRTH,profile.getDateOfBirth());
        values.put(COLUMN_WEIGHT,profile.getWeight());
        values.put(COLUMN_HEIGHT,profile.getHeight());
        values.put(COLUMN_BLOOD_GROUP,profile.getBloodGroup());
        values.put(COLUMN_GENDER,profile.getGender());
        try{
        db.insertOrThrow(TABLE_NAME,null,values);
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
        return 1;
    }
}
