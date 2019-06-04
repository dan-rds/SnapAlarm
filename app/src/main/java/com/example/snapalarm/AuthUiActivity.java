package com.example.snapalarm;

import android.os.Bundle;
import java.util.Arrays;

import android.support.annotation.MainThread;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
//import android.support.annotation.MainThread;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.util.ExtraConstants;

public class AuthUiActivity extends AppCompatActivity {


    String GOOGLE_TOS_URL = "https://www.google.com/policies/terms/";
    private static final int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_ui);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity( new Intent(AuthUiActivity.this, MainActivity.class));
            finish();
        }else {
            showSignInScreen();
        }
    }

    private void showSignInScreen() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setTheme(R.style.FirebaseLoginTheme)
                        .setLogo(R.drawable.logo_transparent)      // Set logo drawable
                        .setTheme(R.style.AppTheme)
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.EmailBuilder().build(),
                                new AuthUI.IdpConfig.GoogleBuilder().build()))
                        .setTosAndPrivacyPolicyUrls(GOOGLE_TOS_URL, GOOGLE_TOS_URL)
                        //.setTosUrl(GOOGLE_TOS_URL)
                        .setIsSmartLockEnabled(false)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            handleSignInResponse(resultCode, data);
            return;
        }

    }
    @MainThread
    private void handleSignInResponse(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Intent in = new Intent(this, Splash.class);
            in.putExtra(ExtraConstants.IDP_RESPONSE, IdpResponse.fromResultIntent(data));
            startActivity(in);
            finish();
            return;
        }

        if (resultCode == RESULT_CANCELED) {
            Log.d("Signin Failed", "User cancelled sign in");
            return;
        }

        if (resultCode == ErrorCodes.NO_NETWORK) {
            Log.d("Signin Failed", "No network connection");
            return;
        }
        Log.d("Signin Failed", "REASON UNKNOWN");

    }

}



