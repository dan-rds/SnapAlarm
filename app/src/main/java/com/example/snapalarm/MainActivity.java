package com.example.snapalarm;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Button alarmFab = findViewById(R.id.alarm_btn);

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
        ArrayList<AlarmModel> alarmList = db.getAllAlarms ();
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
