package com.example.arafathossain.icare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "iCare";

    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ProfileTable.CREATE_TABLE_QUERY);
        db.execSQL(DietTable.CREATE_TABLE_QUERY);
        db.execSQL(DoctorProfileTable.CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ProfileTable.DROP_TABLE_QUERY);
        db.execSQL(DietTable.DROP_TABLE_QUERY);
        db.execSQL(DoctorProfileTable.DROP_TABLE_QUERY);
        db.execSQL(ProfileTable.CREATE_TABLE_QUERY);
        db.execSQL(DietTable.CREATE_TABLE_QUERY);
        db.execSQL(DoctorProfileTable.CREATE_TABLE_QUERY);
    }

    public ArrayList<Profile> getAllProfile() {
        ArrayList<Profile> profiles = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(ProfileTable.TABLE_NAME, new String[]{ProfileTable.COLUMN_PROFILE_NAME, "_id"}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            profiles = new ArrayList<>();
            do {
                Profile profile = new Profile();
                profile.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                profile.setProfileName(cursor.getString(cursor.getColumnIndex(ProfileTable.COLUMN_PROFILE_NAME)));
                profiles.add(profile);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return profiles;
    }

    public Profile getProfileById(String id) {
        Profile userProfile = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(ProfileTable.TABLE_NAME, null, "_id=?", new String[]{id}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                userProfile = new Profile();
                
                userProfile.setMasterEmail(cursor.getString(cursor.getColumnIndex(ProfileTable.COLUMN_MASTER_EMAIL)));///////////////////********** /////////////////////
                
                
                userProfile.setBloodGroup(cursor.getString(cursor.getColumnIndex(ProfileTable.COLUMN_BLOOD_GROUP)));
                userProfile.setContactNo(cursor.getString(cursor.getColumnIndex(ProfileTable.COLUMN_CONTACT_NO)));
                userProfile.setEmail(cursor.getString(cursor.getColumnIndex(ProfileTable.COLUMN_EMAIL)));
                userProfile.setDateOfBirth(cursor.getString(cursor.getColumnIndex(ProfileTable.COLUMN_DATE_OF_BIRTH)));
                userProfile.setUserName(cursor.getString(cursor.getColumnIndex(ProfileTable.COLUMN_USER_NAME)));
                userProfile.setWeight(cursor.getString(cursor.getColumnIndex(ProfileTable.COLUMN_WEIGHT)));
                userProfile.setHeight(cursor.getString(cursor.getColumnIndex(ProfileTable.COLUMN_HEIGHT)));
                userProfile.setGender(cursor.getString(cursor.getColumnIndex(ProfileTable.COLUMN_GENDER)));
                userProfile.setProfileName(cursor.getString(cursor.getColumnIndex(ProfileTable.COLUMN_PROFILE_NAME)));
                userProfile.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return userProfile;
    }

    public int addProfile(Profile profile) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        
        values.put(ProfileTable.COLUMN_MASTER_EMAIL, profile.getMasterEmail()); /////////////////////////********************************************///////////
        
        values.put(ProfileTable.COLUMN_PROFILE_NAME, profile.getProfileName());
        values.put(ProfileTable.COLUMN_USER_NAME, profile.getUserName());
        values.put(ProfileTable.COLUMN_EMAIL, profile.getEmail());
        values.put(ProfileTable.COLUMN_CONTACT_NO, profile.getContactNo());
        values.put(ProfileTable.COLUMN_DATE_OF_BIRTH, profile.getDateOfBirth());
        values.put(ProfileTable.COLUMN_WEIGHT, profile.getWeight());
        values.put(ProfileTable.COLUMN_HEIGHT, profile.getHeight());
        values.put(ProfileTable.COLUMN_BLOOD_GROUP, profile.getBloodGroup());
        values.put(ProfileTable.COLUMN_GENDER, profile.getGender());
        try {
            db.insertOrThrow(ProfileTable.TABLE_NAME, null, values);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    public int removeProfile(String profileId) {
        SQLiteDatabase db = getWritableDatabase();
        int row = db.delete(ProfileTable.TABLE_NAME, "_id=?", new String[]{profileId});
        db.delete(DietTable.TABLE_NAME, DietTable.COLUMN_PROFILE_ID + "=?", new String[]{profileId});
        if (row > 0) return 1;
        return 0;
    }

    public ArrayList<Integer> getAllDietIdByProfileId(String profileId) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Integer> idList = null;
        Cursor cursor = db.query(DietTable.TABLE_NAME, new String[]{DietTable.COLUMN_ID}, DietTable.COLUMN_PROFILE_ID + "=?", new String[]{profileId}, null, null, null);
        if (cursor.moveToFirst()) {
            idList = new ArrayList<>();
            do {
                idList.add(cursor.getInt(cursor.getColumnIndex(DietTable.COLUMN_ID)));
            } while (cursor.moveToNext());
            Log.d("sizeid", idList.size() + "");
        }
        cursor.close();
        return idList;
    }

    public int updateProfile(ContentValues values, int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.update(ProfileTable.TABLE_NAME, values, "_id=?", new String[]{String.valueOf(id)});
    }

    public boolean checkProfileName(String profileName) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(ProfileTable.TABLE_NAME, null, ProfileTable.COLUMN_PROFILE_NAME + "=?", new String[]{profileName}, null, null, null);
        if (cursor.moveToFirst()) {
            cursor.close();
            return false;
        }
        else {
            cursor.close();
            return true;
        }
    }

    public int addDietInformation(DietInformation diet) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DietTable.COLUMN_DAY, diet.getDay());
        values.put(DietTable.COLUMN_MENU, diet.getMenu());
        values.put(DietTable.COLUMN_TIME, diet.getTime());
        values.put(DietTable.COLUMN_TITLE, diet.getTitle());
        values.put(DietTable.COLUMN_REMINDER, diet.getReminder());
        values.put(DietTable.COLUMN_PROFILE_ID, diet.getProfileId());

        return (int) db.insert(DietTable.TABLE_NAME, null, values);
    }

    public int updateDiet(ContentValues values, int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.update(DietTable.TABLE_NAME, values, DietTable.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    public ArrayList<DietInformation> getDietList(String weekDay, String profileId) {
        ArrayList<DietInformation> dietList = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(DietTable.TABLE_NAME, null, DietTable.COLUMN_DAY + "=? AND " + DietTable.COLUMN_PROFILE_ID + "=?", new String[]{weekDay, profileId}, null, null, null);
        if (cursor.moveToFirst()) {
            dietList = new ArrayList<>();
            do {
                DietInformation diet = new DietInformation();
                diet.setId(cursor.getInt(cursor.getColumnIndex(DietTable.COLUMN_ID)));
                diet.setTitle(cursor.getString(cursor.getColumnIndex(DietTable.COLUMN_TITLE)));
                diet.setTime(cursor.getString(cursor.getColumnIndex(DietTable.COLUMN_TIME)));
                diet.setReminder(cursor.getString(cursor.getColumnIndex(DietTable.COLUMN_REMINDER)));
                diet.setMenu(cursor.getString(cursor.getColumnIndex(DietTable.COLUMN_MENU)));
                diet.setProfileName(cursor.getString(cursor.getColumnIndex(DietTable.COLUMN_PROFILE_ID)));
                diet.setDay(cursor.getString(cursor.getColumnIndex(DietTable.COLUMN_DAY)));
                dietList.add(diet);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dietList;
    }

    public int removeDiet(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(DietTable.TABLE_NAME, DietTable.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    public int addDoctorProfile(DoctorProfile profile) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DoctorProfileTable.COLUMN_DOCTOR_NAME, profile.getName());
        values.put(DoctorProfileTable.COLUMN_EMAIL, profile.getEmail());
        values.put(ProfileTable.COLUMN_CONTACT_NO, profile.getContactNo());
        values.put(DoctorProfileTable.COLUMN_DEGREE_ACHIEVED, profile.getDegree());
        values.put(DoctorProfileTable.COLUMN_DESIGNATION, profile.getDesignation());
        values.put(DoctorProfileTable.COLUMN_SPECIALIZATION, profile.getSpecialist());
        values.put(DoctorProfileTable.COLUMN_WORKPLACE, profile.getWorkPlace());
        values.put(DoctorProfileTable.COLUMN_CHAMBER_ADDRESS, profile.getChamber());
        try {
            db.insertOrThrow(DoctorProfileTable.TABLE_NAME, null, values);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }
    public ArrayList<DoctorProfile> getAllDoctorProfile() {
        ArrayList<DoctorProfile> profiles = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(DoctorProfileTable.TABLE_NAME, new String[]{DoctorProfileTable.COLUMN_DOCTOR_NAME, DoctorProfileTable.COLUMN_ID}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            profiles = new ArrayList<>();
            do {
                DoctorProfile profile = new DoctorProfile();
                profile.setId(cursor.getInt(cursor.getColumnIndex(DoctorProfileTable.COLUMN_ID)));
                profile.setName(cursor.getString(cursor.getColumnIndex(DoctorProfileTable.COLUMN_DOCTOR_NAME)));
                profiles.add(profile);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return profiles;
    }
    public int removeDoctorProfile(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(DoctorProfileTable.TABLE_NAME, DoctorProfileTable.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }
    public class DietTable {
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_PROFILE_ID = "profile_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DAY = "day";
        public static final String COLUMN_REMINDER = "reminder";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_MENU = "menu";
        public static final String TABLE_NAME = "diet_informtion";
        public static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_PROFILE_ID + " INTEGER," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_TIME + " TEXT," +
                COLUMN_MENU + " TEXT," +
                COLUMN_DAY + " TEXT," +
                COLUMN_REMINDER + " TEXT)";
        public static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public class ProfileTable {
        public static final String TABLE_NAME = "profile";
        
       public static final String COLUMN_MASTER_EMAIL = "master_email"; //////////////////////***************************///////


        public static final String COLUMN_PROFILE_NAME = "profile_name";
        
        
        public static final String COLUMN_USER_NAME = "user_name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_CONTACT_NO = "contact_no";
        public static final String COLUMN_HEIGHT = "height";
        public static final String COLUMN_WEIGHT = "weight";
        public static final String COLUMN_BLOOD_GROUP = "blood_group";
        public static final String COLUMN_DATE_OF_BIRTH = "date_of_birth";
        public static final String COLUMN_GENDER = "gender";
        public static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "( _id INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_PROFILE_NAME + " TEXT NOT NULL," +
                COLUMN_USER_NAME + " TEXT," +
                COLUMN_MASTER_EMAIL + " TEXT," + ////////////////////////////*********************////////////////////////////
                COLUMN_EMAIL + " TEXT," +
                COLUMN_CONTACT_NO + " TEXT," +
                COLUMN_DATE_OF_BIRTH + " TEXT," +
                COLUMN_WEIGHT + " TEXT," +
                COLUMN_HEIGHT + " TEXT," +
                COLUMN_BLOOD_GROUP + " TEXT," +
                COLUMN_GENDER + " TEXT)";
        public static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

    public class DoctorProfileTable {
        public static final String TABLE_NAME = "doctor_profile";
        public static final String COLUMN_DOCTOR_NAME = "doctor_name";
        public static final String COLUMN_DEGREE_ACHIEVED = "degree_achieved";
        public static final String COLUMN_SPECIALIZATION = "specialization";
        public static final String COLUMN_DESIGNATION = "designation";
        public static final String COLUMN_WORKPLACE = "workplace";
        public static final String COLUMN_CHAMBER_ADDRESS = "chamber_address";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_CONTACT_NO = "contact_no";
        public static final String COLUMN_ID = "id";

        public static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_DOCTOR_NAME + " TEXT," +
                COLUMN_DEGREE_ACHIEVED + " TEXT," +
                COLUMN_SPECIALIZATION + " TEXT," +
                COLUMN_DESIGNATION + " TEXT," +
                COLUMN_WORKPLACE + " TEXT," +
                COLUMN_CHAMBER_ADDRESS + " TEXT," +
                COLUMN_EMAIL + " TEXT," +
                COLUMN_CONTACT_NO + " TEXT)";


        public static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }
}
