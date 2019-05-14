package com.example.snapalarm;

import android.app.AlarmManager;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
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
    String name = "";
    String sun = "";
    String mon = "";
    String tue = "";
    String wed = "";
    String thu = "";
    String fri = "";
    String sat = "";

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

        //Minute Spinner
        final Spinner m = findViewById(R.id.minSpin);
        ArrayAdapter<String> mAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mins);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        m.setAdapter(mAdapter);

        //AM/PM Spinner
        final Spinner ap = findViewById(R.id.ampmSpin);
        ArrayAdapter<String> ampmAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, ampm);
        ampmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ap.setAdapter(ampmAdapter);

        ToggleButton sunTB = findViewById(R.id.sunButt);

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
                // Get Days

                // Add to database
                db.addAlarm(new AlarmModel(alarm));

                // Read all alarms on db TEST FUNCTION
                Log.d("Reading: ", "Reading all alarms...");
                List<AlarmModel> alarms = db.getAllAlarms();

                for(AlarmModel a: alarms) a.print();

                // Back to menu
                Intent i = new Intent(AlarmPage.this, MainActivity.class);
                startActivity(i);
            }
        });
    }


}
