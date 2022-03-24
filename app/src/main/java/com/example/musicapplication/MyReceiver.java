package com.example.musicapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    public static final String ACTION_RESUME = "4";
    public static final String ACTION_PAUSE = "1";
    public static final String ACTION_NEXT = "2";
    public static final String ACTION_PREVIOUS = "3";

    @Override
    public void onReceive(Context context, Intent intent) {
//
        Intent intent1 = new Intent(context, PlayerNotificationService.class);
        if(intent.getAction() != null){
            switch (intent.getAction()){
                case ACTION_PAUSE:
                    Toast.makeText(context, "Play", Toast.LENGTH_SHORT).show();
                    intent1.putExtra("myActionName", intent.getAction());
                    context.startService(intent1);
                    break;
                case ACTION_PREVIOUS:
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
//
//
//        Intent intentService = new Intent(context, PlayerNotificationService.class);
//        context.startService(intentService);


    }


