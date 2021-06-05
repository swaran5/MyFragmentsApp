package com.northerly.myfragmentsapp.view.Helper;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class BootReceiver extends BroadcastReceiver {
    SharedPreferences shrdPref;
    SharedPreferences spreceiver;

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {

        shrdPref = context.getSharedPreferences("sharePref", Context.MODE_PRIVATE);
        long c = shrdPref.getLong("calender", 0);
        String title = shrdPref.getString("name", "0");
        String message = shrdPref.getString("job", "0");

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, AlertReciever.class);
        i.putExtra("name", title);
        i.putExtra("job", message);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 2, i, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c, pendingIntent);

        spreceiver = context.getSharedPreferences("Receive", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spreceiver.edit();
        editor.putLong("getActivity", c);
        editor.putString("job", "Boot Receiver");
        editor.commit();
    }
}