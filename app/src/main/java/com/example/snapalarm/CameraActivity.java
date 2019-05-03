package com.example.snapalarm;

import android.graphics.Bitmap;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity{

    final static int REQUEST_CODE = 1;

    private Intent takePictureIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        Log.d("___onCreate Camera", "created");
        dispatchTakePictureIntent();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.d("___onResult Camera", String.valueOf(requestCode));

        String toastMsg;
        if (resultCode == -1 ) {
            toastMsg = "Picture Taken!";
        }
        else {
            toastMsg = "Picture NOT Taken!";
        }
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        i.putExtra("camera_msg", toastMsg);
        startActivity(i);
    }



    private void dispatchTakePictureIntent() {
        Log.d("___Camera", "dispatchTakePictureIntent");
        takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_CODE);
    }

}
