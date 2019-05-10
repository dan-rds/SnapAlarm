package com.example.snapalarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class AlarmPage extends AppCompatActivity {

    String[] hours={"1","2","3","4","5","6","7","8","9","10","11","12"};
    String[] mins={"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15",
            "16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31",
            "32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47",
            "48","49","50","51","52","53","54","55","56","57","58","59"};
    String[] ampm={"AM","PM"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_page);

        //Hour Spinner
        Spinner h = findViewById(R.id.hourSpin);
        ArrayAdapter<String> hAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, hours);
        hAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        h.setAdapter(hAdapter);

        //Minute Spinner
        Spinner m = findViewById(R.id.minSpin);
        ArrayAdapter<String> mAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mins);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        m.setAdapter(mAdapter);

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
}
