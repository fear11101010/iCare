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
        db.execSQL(AlarmTable.CREATE_TABLE_QUERY);
        db.execSQL(VaccineTable.CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ProfileTable.DROP_TABLE_QUERY);
        db.execSQL(DietTable.DROP_TABLE_QUERY);
        db.execSQL(DoctorProfileTable.DROP_TABLE_QUERY);
        db.execSQL(AlarmTable.DROP_TABLE_QUERY);
        db.execSQL(VaccineTable.DROP_TABLE_QUERY);
        db.execSQL(VaccineTable.CREATE_TABLE_QUERY);
        db.execSQL(AlarmTable.CREATE_TABLE_QUERY);
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
        db.delete(AlarmTable.TABLE_NAME, AlarmTable.COLUMN_PROFILE_ID + "=?", new String[]{profileId});
        if (row > 0) return 1;
        return 0;
    }

    public ArrayList<Integer> getAllAlarmByProfileId(String profileId) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Integer> idList = null;
        Cursor cursor = db.query(AlarmTable.TABLE_NAME, new String[]{AlarmTable.COLUMN_ID}, AlarmTable.COLUMN_PROFILE_ID + "=?", new String[]{profileId}, null, null, null);
        if (cursor.moveToFirst()) {
            idList = new ArrayList<>();
            do {
                idList.add(cursor.getInt(cursor.getColumnIndex(AlarmTable.COLUMN_ID)));
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
        } else {
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
    public int addVaccineInformation(VaccineDetail detail) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(VaccineTable.COLUMN_DATE, detail.getDate());
        values.put(VaccineTable.COLUMN_DISEASE_NAME, detail.getDiseaseName());
        values.put(VaccineTable.COLUMN_PROFILE_ID, detail.getProfileId());
        values.put(VaccineTable.COLUMN_DOSE_NO, detail.getDoseNo());
        values.put(VaccineTable.COLUMN_REMINDER, detail.getReminder());
        values.put(VaccineTable.COLUMN_STATUS,detail.getStatus());
        values.put(VaccineTable.COLUMN_VACCINE_NAME,detail.getVaccineName());

        return (int) db.insert(VaccineTable.TABLE_NAME, null, values);
    }
    public ArrayList<VaccineDetail> getVaccineList(String profileId){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(VaccineTable.TABLE_NAME,null,VaccineTable.COLUMN_PROFILE_ID+"=?",new String[]{profileId},null,null,null);
        ArrayList<VaccineDetail> vaccineDetails = null;
        if (cursor.moveToFirst()){
            vaccineDetails = new ArrayList<>();
            do {
                VaccineDetail detail = new VaccineDetail();
                detail.setReminder(cursor.getString(cursor.getColumnIndex(VaccineTable.COLUMN_REMINDER)));
                detail.setDiseaseName(cursor.getString(cursor.getColumnIndex(VaccineTable.COLUMN_DISEASE_NAME)));
                detail.setVaccineName(cursor.getString(cursor.getColumnIndex(VaccineTable.COLUMN_VACCINE_NAME)));
                detail.setDate(cursor.getString(cursor.getColumnIndex(VaccineTable.COLUMN_DATE)));
                detail.setDoseNo(cursor.getString(cursor.getColumnIndex(VaccineTable.COLUMN_DOSE_NO)));
                detail.setStatus(cursor.getString(cursor.getColumnIndex(VaccineTable.COLUMN_STATUS)));
                detail.setId(cursor.getString(cursor.getColumnIndex(VaccineTable.COLUMN_ID)));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return vaccineDetails;
    }

    public int addAlarmInformation(Reminder reminder) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AlarmTable.COLUMN_ALARM_KEY, reminder.getKey());
        values.put(AlarmTable.COLUMN_KEY_ID, reminder.getKeyId());
        values.put(AlarmTable.COLUMN_PROFILE_ID, reminder.getProfileId());
        return (int) db.insert(AlarmTable.TABLE_NAME, null, values);
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
                diet.setProfileId(cursor.getString(cursor.getColumnIndex(DietTable.COLUMN_PROFILE_ID)));
                diet.setDay(cursor.getString(cursor.getColumnIndex(DietTable.COLUMN_DAY)));
                dietList.add(diet);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dietList;
    }
    public int updateDotorProfile(ContentValues values, int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.update(DoctorProfileTable.TABLE_NAME, values, DoctorProfileTable.COLUMN_ID+"=?", new String[]{String.valueOf(id)});
    }
    public int removeDiet(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(DietTable.TABLE_NAME, DietTable.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    public int removeAlarm(String id, String key) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(AlarmTable.TABLE_NAME, AlarmTable.COLUMN_KEY_ID + "=? AND " + AlarmTable.COLUMN_ALARM_KEY + "=?", new String[]{id, key});
    }

    public int getAlarmId(String id, String key) {
        SQLiteDatabase db = getReadableDatabase();
        int alarmId = -1;
        Cursor cursor = db.query(AlarmTable.TABLE_NAME, new String[]{AlarmTable.COLUMN_ID}, AlarmTable.COLUMN_KEY_ID + "=? AND " + AlarmTable.COLUMN_ALARM_KEY + "=?", new String[]{id, key}, null, null, null);
        if (cursor.moveToFirst()) {
            alarmId = cursor.getInt(cursor.getColumnIndex(AlarmTable.COLUMN_ID));
        }
        cursor.close();
        return alarmId;
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
        Cursor cursor = db.query(DoctorProfileTable.TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            profiles = new ArrayList<>();
            do {
                DoctorProfile profile = new DoctorProfile();
                profile.setId(cursor.getInt(cursor.getColumnIndex(DoctorProfileTable.COLUMN_ID)));
                profile.setName(cursor.getString(cursor.getColumnIndex(DoctorProfileTable.COLUMN_DOCTOR_NAME)));
                profile.setDesignation(cursor.getString(cursor.getColumnIndex(DoctorProfileTable.COLUMN_DESIGNATION)));
                profile.setDegree(cursor.getString(cursor.getColumnIndex(DoctorProfileTable.COLUMN_DEGREE_ACHIEVED)));
                profile.setEmail(cursor.getString(cursor.getColumnIndex(DoctorProfileTable.COLUMN_EMAIL)));
                profile.setContactNo(cursor.getString(cursor.getColumnIndex(DoctorProfileTable.COLUMN_CONTACT_NO)));
                profile.setChamber(cursor.getString(cursor.getColumnIndex(DoctorProfileTable.COLUMN_CHAMBER_ADDRESS)));
                profile.setWorkPlace(cursor.getString(cursor.getColumnIndex(DoctorProfileTable.COLUMN_WORKPLACE)));
                profiles.add(profile);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return profiles;
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
    public int removeDoctorProfile(String id){
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(DoctorProfileTable.TABLE_NAME,DoctorProfileTable.COLUMN_ID+"=?",new String[]{id});
    }

    public class ProfileTable {
        public static final String TABLE_NAME = "profile";


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

    public class AlarmTable {
        public static final String TABLE_NAME = "alarm_schedule";
        public static final String COLUMN_ALARM_KEY = "key";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_KEY_ID = "key_id";
        public static final String COLUMN_PROFILE_ID = "profile_id";

        public static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_KEY_ID + " INTEGER," + COLUMN_PROFILE_ID + " INTEGER," +
                COLUMN_ALARM_KEY + " TEXT)";


        public static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }
    public class VaccineTable {
        public static final String TABLE_NAME = "vaccine_schedule";
        public static final String COLUMN_DISEASE_NAME = "disease_name";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_VACCINE_NAME = "vaccine_name";
        public static final String COLUMN_PROFILE_ID = "profile_id";
        public static final String COLUMN_REMINDER = "reminder";
        public static final String COLUMN_DOSE_NO = "dose_no";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_STATUS = "status";

        public static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_DOSE_NO + " INTEGER," + COLUMN_PROFILE_ID + " INTEGER," +
                COLUMN_DISEASE_NAME + " TEXT,"+
                COLUMN_VACCINE_NAME + " TEXT,"+
                COLUMN_DATE + " TEXT,"+COLUMN_STATUS + " TEXT,"+
                COLUMN_REMINDER + " TEXT)";


        public static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }
}
