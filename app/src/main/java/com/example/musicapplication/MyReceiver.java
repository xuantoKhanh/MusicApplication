package com.example.musicapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {

    public static final int ACTION_RESUME = 4;
    public static final int ACTION_PAUSE = 1;
    public static final int ACTION_NEXT = 2;
    public static final int ACTION_PREVIOUS = 3;

    @Override
    public void onReceive(Context context, Intent intent) {


        Intent intentService = new Intent(context, PlayerNotificationService.class);
        context.startService(intentService);


    }
}
