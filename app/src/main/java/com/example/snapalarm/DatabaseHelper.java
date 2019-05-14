package com.example.snapalarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    // DB Info
    private static final String dbName = "alarmDB";

    // Table Name
    private static final String tableAlarms = "alarms";

    // Table Columns
    private static final String keyName = "names";
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
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String createAlarmTable = "Create Table " + tableAlarms + " (" + keyName + " TEXT, " + keyHour +
                                  " INTEGER, " + keyMin + " INTEGER, " + keyAMPM + " TEXT, " + keySun + " TEXT, " +
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
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = alarmModel.toContentValues();

        db.insert(tableAlarms, null, values);
        db.close();
    }

    // Get all alarms
    public ArrayList<AlarmModel> getAllAlarms() {
        ArrayList<AlarmModel> alarmList = new ArrayList<AlarmModel>();
        String selectQuery = "SELECT * FROM " + tableAlarms;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do {
                alarmList.add(new AlarmModel(cursor));
            } while (cursor.moveToNext());
        }
        return alarmList;
    }

    // Get number of Alarms
    public int getPictureCount() {
        String countQuery = "SELECT * FROM " + tableAlarms;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    // Delete an alarm by name
    public void deleteAlarmName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableAlarms, keyName + " = ?", new String[] {name});
        db.close();
    }

}
