package com.example.snapalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AlarmPage extends AppCompatActivity {

    String[] hours={"1","2","3","4","5","6","7","8","9","10","11","12"};
    String[] mins={"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15",
            "16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31",
            "32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47",
            "48","49","50","51","52","53","54","55","56","57","58","59"};
    String[] ampm={"AM","PM"};

    public int hour;
    public int min;
    public String minute;
    public String Hhour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_page);

        final DatabaseHelper db = new DatabaseHelper(this);

        //Hour Spinner
        final Spinner h = findViewById(R.id.hourSpin);
        ArrayAdapter<String> hAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, hours);
        hAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        h.setAdapter(hAdapter);
        Hhour = h.getSelectedItem().toString();

        //Minute Spinner
        final Spinner m = findViewById(R.id.minSpin);
        ArrayAdapter<String> mAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mins);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        m.setAdapter(mAdapter);
        minute = m.getSelectedItem().toString();

        //AM/PM Spinner
        final Spinner ap = findViewById(R.id.ampmSpin);
        ArrayAdapter<String> ampmAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, ampm);
        ampmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ap.setAdapter(ampmAdapter);

        // Create Alarm
        Button create = findViewById(R.id.createButt);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                HashMap<String, Object> alarm = new HashMap<>();
                Log.i("Clicks: ", "Clicked create, saving alarm");
                // Get Name
                EditText nameText = findViewById(R.id.nameEdit);
                alarm.put("names", nameText.getText().toString());
                
                
                alarm.put("hours",Integer.parseInt(h.getSelectedItem().toString())); // Get hour
                alarm.put("mins", m.getSelectedItem()); // Get min
                alarm.put("ampm", ap.getSelectedItem().toString()); // Get ampm

                // Day Selection
                String dow[] = {"sun","mon","tue","wed","thu","fri","sat"};
                int toggleIDs[] = {R.id.sunButt, R.id.monButt, R.id.tueButt, R.id.wedButt, R.id.thuButt, R.id.friButt, R.id.satButt};
                for( int i = 0; i < 7; i++){
                    ToggleButton tb =  findViewById(toggleIDs[i]);
                    alarm.put(dow[i], tb.isChecked()? 1: 0);
                }
                // Get Items
                String obj[] = {"item00","item01","item02","item03","item04","item05","item06","item07","item08","item09"};
                int checkIDs[] = {R.id.item00, R.id.item01, R.id.item02, R.id.item03, R.id.item04, R.id.item05, R.id.item06, R.id.item07,
                                  R.id.item08, R.id.item09};
                for(int j = 0; j < 10; j++){
                    CheckBox cb = findViewById(checkIDs[j]);
                    alarm.put(obj[j], cb.isChecked()? 1: 0);
                }

                // Add to database
                db.addAlarm(new AlarmModel(alarm));



                // Read all alarms on db TEST FUNCTION
                Log.d("Reading: ", "Reading all alarms...");
                List<AlarmModel> alarms = db.getAllAlarms();

                for(AlarmModel a: alarms) a.print();



                // Back to menu


            try{
                min = Integer.parseInt(m.getSelectedItem().toString());
                hour = Integer.parseInt(h.getSelectedItem().toString());
            }catch (NumberFormatException e) {

            }
                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                Date date = new Date();
                Calendar cal_alarm = Calendar.getInstance();
                Calendar cal_now = Calendar.getInstance();

                cal_now.setTime(date);
                cal_alarm.setTime(date);

                cal_alarm.set(Calendar.HOUR_OF_DAY, hour);
                cal_alarm.set(Calendar.MINUTE, min);
                cal_alarm.set(Calendar.SECOND, 0);

                if(cal_alarm.before(cal_now)){
                    cal_alarm.add(Calendar.DATE, 1);
                }

                Toast.makeText(getApplicationContext(), "Alarm Set " + nameText.getText().toString() +" for "+ Integer.parseInt(h.getSelectedItem().toString())  + ":"+m.getSelectedItem() + ap.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AlarmPage.this, MyBrodcastReciver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(AlarmPage.this, 2444, intent, 0);

                alarmManager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);

                Intent i = new Intent(AlarmPage.this, MainActivity.class);
                startActivity(i);
            }


            });
    }

}
