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

    public DatabaseHelper(Context context) { super(context, dbName, null, 1); }

    @Override
    public void onCreate(SQLiteDatabase db){

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
