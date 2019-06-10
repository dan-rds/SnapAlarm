package com.example.snapalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.content.Intent;
import com.google.firebase.auth.FirebaseUser;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CameraActivity extends AppCompatActivity{

    final static int REQUEST_CODE = 1;
    private ImageView image;
    private String mCurrentPhotoPath;
    private Intent takePictureIntent;
    private FirebaseUser user;
    private String photoDBPath;
    private String object_name;
    public Uri photoUri = new Uri.Builder().build();
    private ArrayList<String> words_found;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        words_found = new ArrayList<String>();

        object_name = savedInstanceState.getString("object_name");

//        catch(Exception e){
//            object_name = "wall";
//        }
        dispatchTakePictureIntent();

        Button ret = findViewById(R.id.returnButt);
        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CameraActivity.this, MainActivity.class);
                Log.e(String.valueOf(words_found.contains(object_name)), words_found.toString());
                if( words_found.contains(object_name)){
                    i.putExtra("camera_msg", "Found " + object_name);
                    Log.d("Stopping alarm", "___");
                    Intent stopIntent = new Intent(getApplicationContext(), RingtoneService.class);
                    stopService(stopIntent);
                } else{
                    i.putExtra("camera_msg", "Try again, did not find " + object_name);
                }
                startActivity(i);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            ListView imageRes = findViewById(R.id.imageResults);
//            Bundle extras = takePictureIntent.getExtras();
//            Bundle userInfo = getIntent().getExtras();
//            Bitmap data = (Bitmap) extras.get("data");
//            image = (ImageView) findViewById(R.id.img);
//            image.setImageBitmap(data);
            ArrayList<String> imageRecResults = new ArrayList<String>();
            int index = 1;

            // wherever this needs to be redirected
            HashMap<String, Object> map = GVision.callGVis(mCurrentPhotoPath);
            Set<Map.Entry<String,Object>> hashSet=map.entrySet();
            for(Map.Entry entry:hashSet ) {
                words_found.add(entry.getKey().toString().toLowerCase());
                Log.e("Ke y= "+entry.getKey(), "Value="+entry.getValue());
                imageRecResults.add(index + ". " + entry.getKey().toString() + "     " + entry.getValue().toString());
                index++;
            }
            for(String w : words_found){
                Log.e("Found", w);
            }
            ArrayAdapter<String> resultAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, imageRecResults);
            imageRes.setAdapter(resultAdapter);


        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        mCurrentPhotoPath = image.getAbsolutePath();
        Log.d("__photo path", mCurrentPhotoPath);
        return image;
    }

    private void dispatchTakePictureIntent() {
        takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.snapalarm.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CODE);
            }
        }
        galleryAddPic();

    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    }


