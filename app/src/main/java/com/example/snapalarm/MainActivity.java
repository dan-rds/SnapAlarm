package com.example.snapalarm;


import android.content.Context;

import android.content.Intent;
import android.os.Bundle;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button cameraFab = findViewById(R.id.camera_btn);
        Button alarmFab = findViewById(R.id.alarm_btn);

        cameraFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, CameraActivity.class);//change to camera activity
                startActivity(i);
            }
        });
        alarmFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("clicks","Click button, go to create alarm");
                Intent i = new Intent(MainActivity.this, AlarmPage.class);
                startActivity(i);
            }
        });

        ListView listView = findViewById(R.id.alarmList);

        final DatabaseHelper db = new DatabaseHelper(this);
        ArrayList<AlarmModel> alarmList = db.git ();
        if(alarmList != null ) {

            CustomAdapter mAdapter = new CustomAdapter(super.getApplicationContext(), alarmList);
            listView.setAdapter(mAdapter);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    protected void onResume() {

        super.onResume();
        Log.d("______Main", "resumed");

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("camera_msg")){
            Toast.makeText(getApplicationContext(), extras.getCharSequence("camera_msg"), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
