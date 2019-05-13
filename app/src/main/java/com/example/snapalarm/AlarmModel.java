package com.example.snapalarm;


import java.util.List;

public class AlarmModel {
    private String name;
    private String hour;
    private String min;
    private String ampm;
    private String sun;
    private String mon;
    private String tue;
    private String wed;
    private String thu;
    private String fri;
    private String sat;

    public AlarmModel() {

    }

    public AlarmModel(String name, String hour, String min, String ampm, String sun, String mon,
                      String tue, String wed, String thu, String fri, String sat) {
        this.name = name;
        this.hour = hour;
        this.min = min;
        this.ampm = ampm;
        this.sun = sun;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat = sat;
    }

    public void setName(String name) { this.name = name; }

    public void setHour(String hour) { this.hour = hour; }

    public void setMin(String min) { this.min = min; }

    public void setAmpm(String ampm) { this.ampm = ampm; }

    public void setSun(String sun) { this.sun = sun; }

    public void setMon(String mon) { this.mon = mon; }

    public void setTue(String tue) { this.tue = tue; }

    public void setWed(String wed) { this.wed = wed; }

    public void setThu(String thu) { this.thu = thu; }

    public void setFri(String fri) { this.fri = fri; }

    public void setSat(String sat) { this.sat = sat; }

    public String getName() { return name; }

    public String getHour() { return hour; }

    public String getMin() { return min; }

    public String getAmpm() { return ampm; }

    public String getSun() { return sun; }

    public String getMon() { return mon; }

    public String getTue() { return tue; }

    public String getWed() { return wed; }

    public String getThu() { return thu; }

    public String getFri() { return fri; }

    public String getSat() { return sat; }
}
