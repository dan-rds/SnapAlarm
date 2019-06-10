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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AlarmPage extends AppCompatActivity {

    public int hour;
    public int min;
    public String minute;
    public String Hhour;
    public int AMPM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_page);

        final DatabaseHelper db = new DatabaseHelper(this);

        //Hour Spinner
        final Spinner h = findViewById(R.id.hourSpin);
        ArrayAdapter hAdapter = ArrayAdapter.createFromResource(this, R.array.hours, R.layout.spinner);
        hAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        h.setAdapter(hAdapter);
        Hhour = h.getSelectedItem().toString();

        //Minute Spinner
        final Spinner m = findViewById(R.id.minSpin);
        ArrayAdapter mAdapter = ArrayAdapter.createFromResource(this, R.array.mins, R.layout.spinner);
        mAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        m.setAdapter(mAdapter);
        minute = m.getSelectedItem().toString();

        //AM/PM Spinner
        final Spinner ap = findViewById(R.id.ampmSpin);
        ArrayAdapter ampmAdapter = ArrayAdapter.createFromResource(this, R.array.ampm, R.layout.spinner);
        ampmAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        ap.setAdapter(ampmAdapter);


        // Create Alarm
        Button create = findViewById(R.id.createButt);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean dayCheck = false;
                int buttonIDs[] = {R.id.sunButt, R.id.monButt, R.id.tueButt, R.id.wedButt, R.id.thuButt, R.id.friButt, R.id.satButt};
                for (int a = 0; a < 7; a++) {
                    ToggleButton b = findViewById(buttonIDs[a]);
                    dayCheck = dayCheck || b.isChecked();
                }
                boolean objCheck = false;
                int objIDs[] = {R.id.item00, R.id.item01, R.id.item02, R.id.item03, R.id.item04, R.id.item05, R.id.item06, R.id.item07,
                        R.id.item08, R.id.item09};
                for(int c = 0; c < 10; c++) {
                    CheckBox d = findViewById(objIDs[c]);
                    objCheck = objCheck || d.isChecked();
                }
                // Ensures a day and obj is selected
                if (dayCheck == true && objCheck == true) {
                    HashMap<String, Object> alarm = new HashMap<>();
                    Log.i("Clicks: ", "Clicked create, saving alarm");
                    // Get Name
                    EditText nameText = findViewById(R.id.nameEdit);
                    alarm.put("names", nameText.getText().toString());


                    alarm.put("hours", Integer.parseInt(h.getSelectedItem().toString())); // Get hour
                    alarm.put("mins", m.getSelectedItem()); // Get min
                    alarm.put("ampm", ap.getSelectedItem().toString()); // Get ampm

                    // Day Selection
                    String dow[] = {"sun", "mon", "tue", "wed", "thu", "fri", "sat"};
                    int toggleIDs[] = {R.id.sunButt, R.id.monButt, R.id.tueButt, R.id.wedButt, R.id.thuButt, R.id.friButt, R.id.satButt};
                    for (int i = 0; i < 7; i++) {
                        ToggleButton tb = findViewById(toggleIDs[i]);
                        alarm.put(dow[i], tb.isChecked() ? 1 : 0);
                    }
                    // Get Items
                    ArrayList<String> selected_objects = new ArrayList();
                    String obj[] = {"wall", "room", "light", "games", "zebra", "shelf", "ceiling", "paper", "tile", "cat"};
                    int checkIDs[] = {R.id.item00, R.id.item01, R.id.item02, R.id.item03, R.id.item04, R.id.item05, R.id.item06, R.id.item07,
                            R.id.item08, R.id.item09};
                    for (int j = 0; j < 10; j++) {

                        CheckBox cb = findViewById(checkIDs[j]);
                        alarm.put(obj[j], cb.isChecked() ? 1 : 0);
                        if(cb.isChecked()){
                            selected_objects.add(obj[j]);
                        }
                    }

                    // Add to database
                    db.addAlarm(new AlarmModel(alarm));


                    // Read all alarms on db TEST FUNCTION
                    Log.d("Reading: ", "Reading all alarms...");
                    List<AlarmModel> alarms = db.getAllAlarms();

                    for (AlarmModel a : alarms) a.print();


                    // Back to menu


                    try {
                        min = Integer.parseInt(m.getSelectedItem().toString());
                        hour = Integer.parseInt(h.getSelectedItem().toString());
                        String day = ap.getSelectedItem().toString();
                        if (day == "AM") {
                            AMPM = 0;
                        } else {
                            AMPM = 1;
                        }
                    } catch (NumberFormatException e) {

                    }
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    Date date = new Date();
                    Calendar cal_alarm = Calendar.getInstance();
                    Calendar cal_now = Calendar.getInstance();

                    cal_now.setTime(date);
                    cal_alarm.setTime(date);

                    cal_alarm.set(Calendar.HOUR_OF_DAY, hour);
                    cal_alarm.set(Calendar.MINUTE, min);
                    cal_alarm.set(Calendar.SECOND, 0);
                    cal_alarm.set(Calendar.AM_PM, AMPM);

                    if (cal_alarm.before(cal_now)) {
                        cal_alarm.add(Calendar.DATE, 1);
                    }

                    Toast.makeText(getApplicationContext(), "Alarm Set " + nameText.getText().toString() + " for " + Integer.parseInt(h.getSelectedItem().toString()) + ":" + m.getSelectedItem() + ap.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AlarmPage.this, MyBrodcastReciver.class);
                    Log.e("Selected" , selected_objects.toString());

                    intent.putExtra("options", selected_objects.toArray(new String[0]));
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(AlarmPage.this, 2444, intent, 0);
                   // PendingIntent.put("options", selected_objects.toArray());
                    alarmManager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);

                    Intent i = new Intent(AlarmPage.this, MainActivity.class);
                    startActivity(i);
                }
                else if(dayCheck == false && objCheck == true) {
                    Toast.makeText(getApplicationContext(), "Please select a day!", Toast.LENGTH_SHORT).show();
                }
                else if(dayCheck == true && objCheck == false) {
                    Toast.makeText(getApplicationContext(), "Please select an object!", Toast.LENGTH_SHORT).show();
                }
                else if(dayCheck == false && objCheck == false) {
                    Toast.makeText(getApplicationContext(), "Please select a day and an object!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
