package com.example.snapalarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class MyBrodcastReciver extends BroadcastReceiver {
    private Ringtone ringtone;
    private String name;
    @Override
    public void onReceive(Context context, Intent intent) {

        Vibrator vibrator = ( Vibrator ) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(VibrationEffect.createOneShot(2000, -1));
        //vibrator.vibrate(2000);

        Notification notification = null;

         notification = new Notification.Builder(context)
                    .setContentTitle("Alarm is ON")
                    .setContentText("Alarm is set")
                    .setSmallIcon(R.mipmap.ic_launcher).build();
        
        NotificationManager manager = ( NotificationManager ) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        manager.notify(0, notification);

        Intent startIntent = new Intent(context.getApplicationContext(), RingtoneService.class);
        context.startService(startIntent);
        Object[] options = intent.getExtras().getStringArray("options");
        String[] stringArray = Arrays.copyOf(options, options.length, String[].class);

        Random r = new Random();
        int randomNumber=r.nextInt(options.length);
        String object_name = stringArray[randomNumber];
        Intent cameraIntent = new Intent(context.getApplicationContext(), CameraActivity.class);

        cameraIntent.putExtra("object_name", object_name);
        Toast.makeText(context, "Take a picture of a " + object_name, Toast.LENGTH_SHORT).show();
        cameraIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(cameraIntent);

    }

}