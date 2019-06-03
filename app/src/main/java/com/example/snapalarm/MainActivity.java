package com.example.snapalarm;


import android.content.Context;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final Context context = this;
    View view;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*************Background*****************************/
        view = this.getWindow().getDecorView();
        Calendar calendar =  Calendar.getInstance();
        textView = findViewById(R.id.textView);
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
            textView.setText("Good Morning");
        }else{
            view.setBackgroundResource(R.drawable.gradient_night);
            textView.setText("Good Evening");
        }
        /******************************************/




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

        ListView list = findViewById(R.id.images_list);
        ArrayList<AlarmModel> alarms = new ArrayList<>();

        //DatabaseHelper mDatabaseHelper= new DatabaseHelper(super.getApplicationContext());

       // Cursor data = mDatabaseHelper.getData();
        int i = 0;
        while(i < 10) {

            AlarmModel a = new AlarmModel(i,i, Meridian.AM);
            alarms.add(a);
            i +=1;

        }

        CustomAdapter mAdapter = new CustomAdapter(super.getApplicationContext(),alarms);
        list.setAdapter(mAdapter);

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
