package com.example.musicapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int actionMusic = intent.getIntExtra("123", 0);
        Intent intentService = new Intent(context, PlayerNotificationService.class);
        intentService.putExtra("action_music_service", actionMusic);

        context.startService(intentService);


    }
}
