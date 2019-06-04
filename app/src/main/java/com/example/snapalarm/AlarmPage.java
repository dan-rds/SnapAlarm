package com.example.snapalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;


import static java.util.Calendar.getInstance;

public class AlarmPage extends AppCompatActivity {

    String[] hours={"1","2","3","4","5","6","7","8","9","10","11","12"};
    String[] mins={"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15",
            "16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31",
            "32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47",
            "48","49","50","51","52","53","54","55","56","57","58","59"};
    String[] ampm={"AM","PM"};
    View view;
    public int mhour;
    public int mmin;
    public String minute;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_page);

        /*************Background*****************************/
        view = this.getWindow().getDecorView();
        Calendar calendar =  Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 6);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 0);

        long morning = calendar.getTimeInMillis(); //6:00

        calendar.set(Calendar.HOUR_OF_DAY, 17);
        calendar.set(Calendar.MINUTE,00);
        calendar.set(Calendar.SECOND, 0);

        long evening = calendar.getTimeInMillis();

        long currentTime = System.currentTimeMillis();

        if(currentTime > morning && currentTime <evening){
            view.setBackgroundResource(R.drawable.gradient_first);
        }else{
            view.setBackgroundResource(R.drawable.gradient_night);
        }
         /******************************************/

        //Hour Spinner
        Spinner h = findViewById(R.id.hourSpin);
        ArrayAdapter<String> hAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, hours);
        hAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        h.setAdapter(hAdapter);
        String hour = h.getSelectedItem().toString();
        mhour = Integer.parseInt(hour);


        //Minute Spinner
        Spinner m = findViewById(R.id.minSpin);
        ArrayAdapter<String> mAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mins);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        m.setAdapter(mAdapter);
        minute = m.getSelectedItem().toString();
        mmin = Integer.parseInt(minute);



        //AM/PM Spinner
        Spinner ap = findViewById(R.id.ampmSpin);
        ArrayAdapter<String> ampmAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, ampm);
        ampmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ap.setAdapter(ampmAdapter);



        Button create = findViewById(R.id.createButt);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(AlarmPage.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    public void setTimer(View view){
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Date date = new Date();

        Calendar cal_alarm = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();
        //Calendar calendar = Calendar.getInstance();

        cal_now.setTime(date);
        cal_alarm.setTime(date);



        cal_alarm.set(Calendar.HOUR_OF_DAY, mhour);
        cal_alarm.set(Calendar.MINUTE, mmin );
        cal_alarm.set(Calendar.SECOND, 0);

        if(cal_alarm.before(cal_now)){
            cal_alarm.add(Calendar.DATE, 1);
        }

        Toast.makeText(getApplicationContext(),"Alarm created", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(AlarmPage.this, MyBrodcastReciver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(AlarmPage.this, 2444, intent, 0 );
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);


    }
}
