package com.example.snapalarm;


import java.util.List;

enum Meridian {
    AM, PM
        }
public class AlarmModel {
    private float _alarmTimeUTC;
    private int _hour;
    private int _minute;
    private Meridian _ampm;
    private List <Integer> _repeatDays;
    // TODO figure out the sound type
   // private sound alarmsound

    AlarmModel(float time, List<Integer> repeatDays){
        this._alarmTimeUTC = time;
        this._repeatDays = repeatDays;
    }

    AlarmModel(int hour, int minute, Meridian meridian){
        this._hour = hour;
        this._minute = minute;
        this._ampm = meridian;

    }
    public float getUTC(){
        return this._alarmTimeUTC;
    }
    public String getTimeString(){
        return String.valueOf(this._hour) + ":" + String.format("%02d", this._minute) + " " + (this._ampm == Meridian.AM? "am" : "pm");
    }



    public boolean getActiveStatus(){
        //TODO add a db call. Just random for now
        return  _hour % 2 == 0;
    }


}
