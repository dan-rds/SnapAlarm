package com.example.snapalarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

import java.util.List;

public class AlarmPage extends AppCompatActivity {

    String[] hours={"1","2","3","4","5","6","7","8","9","10","11","12"};
    String[] mins={"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15",
            "16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31",
            "32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47",
            "48","49","50","51","52","53","54","55","56","57","58","59"};
    String[] ampm={"AM","PM"};
    String name, sun, mon, tue, wed, thu, fri, sat;

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



        // Create Alarm
        Button create = findViewById(R.id.createButt);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Clicks: ", "Clicked create, saving alarm");
                // Get Name
                EditText nameText = findViewById(R.id.nameEdit);
                name = nameText.getText().toString();
                String hour = h.getSelectedItem().toString(); // Get hour
                String min  = m.getSelectedItem().toString(); // Get min
                String ampm = ap.getSelectedItem().toString(); // Get ampm

                // Day Selection
                ToggleButton sunButton = findViewById(R.id.sunButt);
                ToggleButton monButton = findViewById(R.id.monButt);
                ToggleButton tueButton = findViewById(R.id.tueButt);
                ToggleButton wedButton = findViewById(R.id.wedButt);
                ToggleButton thuButton = findViewById(R.id.thuButt);
                ToggleButton friButton = findViewById(R.id.friButt);
                ToggleButton satButton = findViewById(R.id.satButt);

                // Get Days
                if(sunButton.isChecked()) { sun = "sun"; }
                if(monButton.isChecked()) { mon = "mon"; }
                if(tueButton.isChecked()) { tue = "tue"; }
                if(wedButton.isChecked()) { wed = "wed"; }
                if(thuButton.isChecked()) { thu = "thu"; }
                if(friButton.isChecked()) { fri = "fri"; }
                if(satButton.isChecked()) { sat = "sat"; }

                // Add to database
                db.addAlarm(new AlarmModel(name, hour, min, ampm, sun, mon, tue, wed, thu, fri, sat));

                // Read all alarms on db TEST FUNCTION
                Log.d("Reading: ", "Reading all alarms...");
                List<AlarmModel> alarms = db.getAllAlarms();
                for(AlarmModel alarm : alarms) {
                    String log = "name: " + alarm.getName() + "\n" +
                                 "hour: " + alarm.getHour() + "\n" +
                                 "min: " + alarm.getMin() + "\n" +
                                 "ampm: " + alarm.getAmpm() + "\n" +
                                 "sun: " + alarm.getSun() + "\n" +
                                 "mon: " + alarm.getMon() + "\n" +
                                 "tue: " + alarm.getTue() + "\n" +
                                 "wed: " + alarm.getWed() + "\n" +
                                 "thu: " + alarm.getThu() + "\n" +
                                 "fri: " + alarm.getFri() + "\n" +
                                 "sat: " + alarm.getSat() + "\n";
                    Log.d("ALARM: \n", log);
                }

                // Back to menu
                Intent i = new Intent(AlarmPage.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
