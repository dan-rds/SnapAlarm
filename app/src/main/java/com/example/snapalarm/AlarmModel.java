package com.example.snapalarm;


import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.*;
import android.util.Log;


public class AlarmModel {

    private float _alarmTimeUTC;
    private int _hour;
    private int _minute;
    private String _ampm;
    private String _name;
    private boolean[] _repeatDays;
    String dow[] = {"sun","mon","tue","wed","thu","fri","sat"};

    // TODO figure out the sound type
   // private sound alarmsound


    AlarmModel(int hour, int minute, String meridian){
        this._hour = hour;
        this._minute = minute;
        this._ampm = meridian;

    }

    public AlarmModel(Cursor data){
       this._name = data.getString(0);
		this._hour = data.getInt(1);
		this._minute = data.getInt(2);
		this._ampm = data.getString(3);

		this._repeatDays = new boolean[7];
		for(int i = 4; i <= 10; i++){
		    this._repeatDays[i-4] = (data.getInt(i) == 0);
        }

    }

    public AlarmModel(HashMap<String, Object> data){

        for(String key : data.keySet()){
            Log.d(key, (data.get(key).getClass().getCanonicalName())+ "   " + String.valueOf(data.get(key)));
        }

        this._hour = Integer.parseInt(data.get("hours").toString());

        this._name = (String) data.get("names");

        this._minute = Integer.parseInt(data.get("mins").toString());
        this._ampm = (String) data.get("ampm");

        this._repeatDays = new boolean[7];
        for(int i = 0; i < 7; i++){
            this._repeatDays[i] = ((int) data.get(dow[i]) == 0);
        }

    }
    public void print(){
        ContentValues cv = this.toContentValues();
        for(String key: cv.keySet()){
            Log.d("+++"+key, String.valueOf(cv.get(key)));
        }
    }

    public ContentValues toContentValues(){
        ContentValues output = new ContentValues();
        output.put("names", this._name);
        output.put("hours", this._hour);
        output.put("mins", this._minute);
        output.put("ampm", this._ampm);

        String dow[] = {"sun","mon","tue","wed","thu","fri","sat"};
        for(int i = 0; i < 7; i++){
            output.put(dow[i], this._repeatDays[i]? 1 : 0);
        }
        return output;

    }

    public String getTimeString(){
        return String.valueOf(this._hour) + ":" + String.format("%02d", this._minute) + " " + this._ampm;
    }



    public boolean getActiveStatus(){
        //TODO add a db call. Just random for now
        return  _hour % 2 == 0;
    }


}
