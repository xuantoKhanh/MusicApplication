package com.example.musicapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.util.Util;

import java.security.Provider;

public class PlayerNotificationService extends Service {

    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    public static final int ACTION_RESUME = 0;
    public static final int ACTION_PAUSE = 1;
    public static final int ACTION_NEXT = 2;
    public static final int ACTION_PREVIOUS = 3;

    public static ExoPlayer player;
    private boolean isPlaying;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_custom_notification);

        if(isPlaying){ //bat su kien click
            remoteViews.setOnClickPendingIntent(R.id.img_play, getPendingIntent(this, ACTION_PAUSE));
            remoteViews.setImageViewResource(R.id.img_play, R.drawable.pause_music);
        }else{
            remoteViews.setOnClickPendingIntent(R.id.img_play, getPendingIntent(this, ACTION_RESUME));
            remoteViews.setImageViewResource(R.id.img_play, R.drawable.play_music);
        }


        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service") //notification title
                .setContentText("abd")
                .setSmallIcon(R.drawable.music_video)
                .setSound(null)
                .setContentIntent(pendingIntent)
                .setCustomContentView(remoteViews)
                .build();

        startForeground(1, notification);
        //do heavy work on a background thread
        //stopSelf();

        int actionMusic = intent.getIntExtra("action_music_service", 0);
        handleActionMusic(actionMusic);

        return START_NOT_STICKY;
    }

    private PendingIntent getPendingIntent(Context context, int action){
        Intent intent = new Intent(this, MyReceiver.class);
        intent.putExtra("action_music", action);

        return PendingIntent.getBroadcast(context.getApplicationContext(), action, intent, PendingIntent.FLAG_CANCEL_CURRENT);


    }
    private void handleActionMusic(int action){
        switch(action){
            case ACTION_PAUSE:
                pauseMusic();
                break;
            case ACTION_RESUME:
                resumeMusic();
                break;
            case ACTION_NEXT:
                break;
            case ACTION_PREVIOUS:
                break;
        }
    }

    private void resumeMusic() {
        if (player != null && !isPlaying){
            player.play();
            isPlaying = true;

        }
    }

    private void pauseMusic(){
        if (player != null && isPlaying){
            player.pause();
            isPlaying = false;

        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
