package com.example.musicapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    public static final String ACTION_PLAY = "resume";
    public static final String ACTION_PAUSE = "pause";
    public static final String ACTION_NEXT = "next";
    public static final String ACTION_PREV = "prev";
    @Override
    public void onReceive(Context context, Intent intent) {

//        int actionMusic = intent.getIntExtra("action_music", 0);
//        Intent intentService = new Intent(context, PlayerNotificationService.class);
//        intentService.putExtra("action_music_service", actionMusic);
//
//        context.startService(intentService);

        Intent intent1 = new Intent(context, PlayerNotificationService.class);
        if(intent.getAction() != null){
            switch (intent.getAction()){
                case ACTION_PLAY:
                    Toast.makeText(context, "Play", Toast.LENGTH_SHORT).show();
                    intent1.putExtra("myActionName", intent.getAction());
                    context.startService(intent1);
                    break;
                case ACTION_PREV:
                    Toast.makeText(context, "Previous", Toast.LENGTH_SHORT).show();
                    intent1.putExtra("myActionName", intent.getAction());
                    context.startService(intent1);
                    break;
                case ACTION_NEXT:
                    Toast.makeText(context, "Next", Toast.LENGTH_SHORT).show();
                    intent1.putExtra("myActionName", intent.getAction());
                    context.startService(intent1);
                    break;

            }
        }



    }
}
