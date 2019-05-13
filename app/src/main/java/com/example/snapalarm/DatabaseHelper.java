package com.example.snapalarm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // DB Info
    private static final String dbName = "alarmDB";

    // Table Name
    private static final String tableAlarms = "alarms";

    // Table Columns
    private static final String keyHour = "hours";
    private static final String keyMin = "mins";
    private static final String keyAMPM = "ampm";
    private static final String keySun = "sun";
    private static final String keyMon = "mon";
    private static final String keyTue = "tue";
    private static final String keyWed = "wed";
    private static final String keyThu = "thu";
    private static final String keyFri = "fri";
    private static final String keySat = "sat";

    public DatabaseHelper(Context context) { super(context, dbName, null, 1); }

    @Override
    public void onConfirgue(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String createAlarmTable = "Create Table " + tableAlarms + " (" + keyHour + " TEXT, " +
                                  keyMin + " TEXT, " + keyAMPM + " TEXT, " + keySun + " TEXT, " +
                                  keyMon + " TEXT, " + keyTue + " TEXT, " + keyWed + " TEXT, " +
                                  keyThu + " TEXT, " + keyFri + " TEXT, " + keySat + " TEXT)";
        db.execSQL(createAlarmTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableAlarms);
        onCreate(db);
    }

    // Adding an alarm
    public void addAlarm(AlarmModel alarmModel) {
        SQLiteDatabase db = 
    }
}