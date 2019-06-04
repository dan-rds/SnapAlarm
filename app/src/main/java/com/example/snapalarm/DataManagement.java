//package com.example.snapalarm;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.widget.Toast;
//
//import java.io.File;
//import java.util.*;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.FieldValue;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//
//public class DataManagement extends AppCompatActivity implements AsyncRequester {
//    public String TAG = "__DATA_MGMT";
//    private FirebaseFirestore db;
//    private FirebaseUser user;
//    public String uri;
//    private String photoPath;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_datamgmt);
//        Bundle bundle = getIntent().getExtras();
//
//        this.user = (FirebaseUser)bundle.get("user");
//        Log.e("_______", user.getDisplayName());
//
//        this.db = FirebaseFirestore.getInstance();
//        photoPath = (String)bundle.get("photoPath");
//        if(photoPath != null){
//            uploadFood();
//        }
//    }
//
//    @Override
//    public void onCompletedTask(Map m){
//        Intent in = new Intent( this, MainActivity.class);
//        in.putExtra("newFood", "NULL");
//        startActivity(in);
//    }
//    @Override
//    public void onCompletedTask(String f){
//        Intent in = new Intent( this, MainActivity.class);
//        in.putExtra("newFood", f);
//        startActivity(in);
//    }
//
//    private void uploadFood(){
//        HashMap map = GVision.callGVis(photoPath);
//        Log.d("TAG", "Map of preds: " + Arrays.asList(map));
//
//
//
//        ResolveFood rf = new ResolveFood(this);
//        String finalFood = rf.resolveFood(map);
//        Log.d("__DM_FOOD_RESPONSE", finalFood);
//        if (finalFood.equals("Food Not Identified")) {
//            Log.d("TAG", "Final Food not identified");
//            Toast.makeText(this, "Food Not Identified", Toast.LENGTH_LONG).show();
//            Intent in = new Intent( getApplicationContext(), Splash.class);
//            startActivity(in);
//        }
//        AsyncTask<String, Void, String> nutrition = new NutritionixTaskCall(this).execute(finalFood);
//        try {
//            String n = nutrition.get();
//            Food f = NutritionixParser.parse(n);
//            Log.e("TAG", f.toString());
//            uploadPhotoToStorage(f, photoPath);
//        } catch (Exception e ) {
//            e.printStackTrace();
//        }
//    }
//
//    private void getUri(StorageReference storageRef, final Food f, final String relPath) {
//
//        storageRef.getDownloadUrl().addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                Log.d(TAG, "COULD NOT FIND URL");
//            }
//        }).addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri photoUri) {
//                Log.d(TAG, "URL: " + photoUri.toString());
//                f.setUrl(photoUri.toString());
//                pushFood(f);
//            }
//        });
//    }
//
//    public void uploadPhotoToStorage(final Food f, String mCurrentPhotoPath) {
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        final StorageReference storageRef = storage.getReference();
//        Uri file = Uri.fromFile(new File(mCurrentPhotoPath));
//
//        final StorageReference uploadRef = storageRef.child("images/" + file.getLastPathSegment());
//        UploadTask uploadTask = uploadRef.putFile(file);
//        final String photoDBPath = "images/" + file.getLastPathSegment();
//
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                Log.d(TAG, "NO PHOTO WAS UPLOADED");
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Log.d(TAG, "PHOTO " + photoDBPath + " WAS UPLOADED!");
//                getUri(uploadRef, f, photoDBPath);
//            }
//        });
//    }
//}
//
//
//
//
