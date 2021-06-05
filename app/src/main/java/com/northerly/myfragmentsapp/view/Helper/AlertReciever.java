package com.northerly.myfragmentsapp.view.Helper;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class AlertReciever extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {

        PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();
        if(!isScreenOn)
        {
            @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |PowerManager.ACQUIRE_CAUSES_WAKEUP |PowerManager.ON_AFTER_RELEASE,"MyLock");
            wl.acquire(10000);
            @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl_cpu = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"MyCpuLock");

            wl_cpu.acquire(10000);
        }

        String title = intent.getExtras().getString("name");
        String message = intent.getExtras().getString("job");
        NotiicationHelper mNotiicationHelper = new NotiicationHelper(context);
        NotificationCompat.Builder nb = mNotiicationHelper.getChannel1Notification(title, message);
        mNotiicationHelper.getManager().notify(1, nb.build());
    }
}
