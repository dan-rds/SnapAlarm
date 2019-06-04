package com.example.snapalarm;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class Splash extends AppCompatActivity {
    public FirebaseAuth mFirebaseAuth;
    public FirebaseUser mFirebaseUser;
    public Splash con;
    public String TAG = "__getUserData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        con = this;

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In Activity
            startActivity(new Intent(this, AuthUiActivity.class));
            finish();
            return;
        } else {
            String mUsername = mFirebaseUser.getDisplayName();
            String mEmailAddress = mFirebaseUser.getEmail();
            Log.d("__login user", mUsername + "    " + mEmailAddress);

            getUserData(mFirebaseUser);
        }
    }
    // Switches to main activity, so we can call it inside an onCompleted
    public void triggerMain(ArrayList<String> docs){
        Intent in = new Intent(this, MainActivity.class);
        in.putExtra("docs", docs);
        startActivity(in);
    }
    // Gets called when there is no user in the system.
    public void createUser(DocumentReference docref){
        HashMap data = new HashMap();
        data.put("name", mFirebaseUser.getDisplayName());
        docref.set(data).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                getUserData(mFirebaseUser);
            }
            });
        }

    public void getUserData(FirebaseUser user) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

            final DocumentReference docRef = db.collection("userData").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.get("foods"));
                            // Now that we have the users data, go to main
                            triggerMain( (ArrayList<String>) document.get("foods"));

                        } else {
                            Log.e(TAG, "No such document");
                            createUser(docRef);
                        }
                    } else {
                        Log.e(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }


    }
