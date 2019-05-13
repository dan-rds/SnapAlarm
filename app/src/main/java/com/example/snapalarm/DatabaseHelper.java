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
                                  " TEXT, " + keyMin + " TEXT, " + keyAMPM + " TEXT, " + keySun + " TEXT, " +
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
        ContentValues values = new ContentValues();
        values.put(keyName, alarmModel.getName());
        values.put(keyHour, alarmModel.getHour());
        values.put(keyMin, alarmModel.getMin());
        values.put(keyAMPM, alarmModel.getAmpm());
        values.put(keySun, alarmModel.getSun());
        values.put(keyMon, alarmModel.getMon());
        values.put(keyTue, alarmModel.getTue());
        values.put(keyWed, alarmModel.getWed());
        values.put(keyThu, alarmModel.getThu());
        values.put(keyFri, alarmModel.getFri());
        values.put(keySat, alarmModel.getSat());
        db.insert(tableAlarms, null, values);
        db.close();
    }

    // Get all alarms
    public List<AlarmModel> getAllAlarms() {
        List<AlarmModel> alarmList = new ArrayList<AlarmModel>();
        String selectQuery = "SELECT * FROM " + tableAlarms;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()) {
            do {
                AlarmModel alarm = new AlarmModel();
                alarm.setName(cursor.getString(0));
                alarm.setHour(cursor.getString(1));
                alarm.setMin(cursor.getString(2));
                alarm.setAmpm(cursor.getString(3));
                alarm.setSun(cursor.getString(4));
                alarm.setMon(cursor.getString(5));
                alarm.setTue(cursor.getString(6));
                alarm.setWed(cursor.getString(7));
                alarm.setThu(cursor.getString(8));
                alarm.setFri(cursor.getString(9));
                alarm.setSat(cursor.getString(10));
                alarmList.add(alarm);
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


}
