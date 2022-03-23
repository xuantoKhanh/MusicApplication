package com.example.musicapplication;

import static com.example.musicapplication.MainActivity.listResponseMusic;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

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


    public static final String CHANNEL_ID = "ForegroundService Channel";
    public static final int NOTIFICATION_ID = 123;
    public static final String ACTION_PLAY = "resume";
    public static final String ACTION_PAUSE = "pause";
    public static final String ACTION_NEXT = "next";
    public static final String ACTION_PREV = "prev";

    public static ExoPlayer player;
    private boolean isPlaying = true;
    int position = 0;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();

        String actionName = intent.getStringExtra("myActionName");
        if (actionName != null) {
            switch (actionName) {
                case ACTION_PLAY:
                    break;
                case ACTION_PREV:
                    player.seekBack();
                    break;
                case ACTION_NEXT:
                    player.seekToNext();
                    break;
            }
        }
            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_custom_notification);

            remoteViews.setTextViewText(R.id.tv_title_song, listResponseMusic.get(position).getTitle());
            remoteViews.setTextViewText(R.id.tv_single_song, listResponseMusic.get(position).getSubtitle());
            //remoteViews.setImageViewResource(R.id.ima_song, Integer.parseInt(listResponseMusic.get(position).getThumb()));


            if (isPlaying) { //bat su kien click
//            remoteViews.setOnClickPendingIntent(R.id.img_play, getPendingIntent(this, ACTION_PAUSE));
//            remoteViews.setImageViewResource(R.id.img_play, R.drawable.pause_music);
                Intent intent1 = new Intent("123");
                intent.putExtra("test", "test");
                isPlaying = false;
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent1);
            } else {
                isPlaying = true;
                remoteViews.setOnClickPendingIntent(R.id.img_next, getPendingIntent(this, Integer.parseInt(ACTION_NEXT)));
                remoteViews.setImageViewResource(R.id.img_play, R.drawable.play_music);
            }

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID) //add action cho tung button
                    .setSmallIcon(R.drawable.music_video)
                    .setSound(null)
                    .setContentIntent(pendingIntent) //moi lan click vao noti lai intent sang layout video moi, de len layout cu, am van bat
                    .setCustomContentView(remoteViews)
                    .build();

            startForeground(1, notification);

            int actionMusic = intent.getIntExtra("action_music_service", 0);
            //handleActionMusic(actionMusic);

            return START_NOT_STICKY;

    }

    private PendingIntent getPendingIntent(Context context, int action){
        Intent intent = new Intent(this, MyReceiver.class);
        intent.putExtra("123", action);

        return PendingIntent.getBroadcast(context.getApplicationContext(), action, intent, PendingIntent.FLAG_CANCEL_CURRENT);
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
                    NotificationManager.IMPORTANCE_DEFAULT);

            serviceChannel.setSound(null, null);
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
