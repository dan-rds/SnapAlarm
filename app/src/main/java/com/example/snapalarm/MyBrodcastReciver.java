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

public class MyBrodcastReciver extends BroadcastReceiver {
    private Ringtone ringtone;
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null && extras.keySet().contains("command") ){
            Log.e("Command"," present in budle");
            ringtone.stop();

        }
        Vibrator vibrator = ( Vibrator ) context.getSystemService(Context.VIBRATOR_SERVICE);
        //vibrator.vibrate(VibrationEffect.createOneShot(2000, -1));
        vibrator.vibrate(2000);

        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
         notification = new Notification.Builder(context)
                    .setContentTitle("Alarm is ON")
                    .setContentText("Alarm is set")
                    .setSmallIcon(R.mipmap.ic_launcher).build();
        }
        NotificationManager manager = ( NotificationManager ) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        manager.notify(0, notification);

        Uri notif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

        ringtone = RingtoneManager.getRingtone(context, notif);
        ringtone.play();
    }

}