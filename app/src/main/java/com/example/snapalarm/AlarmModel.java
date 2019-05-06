package com.example.snapalarm;


import java.util.List;

public class AlarmModel {
    private float _alarmTimeUTC;
    private List <Integer> _repeatDays;
    // TODO figure out the sound type
   // private sound alarmsound

    AlarmModel(float time, List<Integer> repeatDays){
        this._alarmTimeUTC = time;
        this._repeatDays = repeatDays;
    }
    public float getTime(){
        return this._alarmTimeUTC;
    }


}
