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

    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    public static final int ACTION_RESUME = 4;
    public static final int ACTION_PAUSE = 1;
    public static final int ACTION_NEXT = 2;
    public static final int ACTION_PREVIOUS = 3;

    public static ExoPlayer player;
    private boolean isPlaying = false;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        createNotificationChannel();
        sendNotification();

        int actionMusic = intent.getIntExtra("action_music_service", 0);
        handleActionMusic(actionMusic);

        return START_NOT_STICKY;
    }

    private void sendNotification(){
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Intent prevIntent = new Intent(this, MyReceiver.class).setAction(String.valueOf(ACTION_PREVIOUS));
        PendingIntent prevPendingIntent = PendingIntent.getBroadcast(this, 0,
                prevIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent nextIntent = new Intent(this, MyReceiver.class).setAction(String.valueOf(ACTION_NEXT));
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(this, 0,
                nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent playIntent = new Intent(this, MyReceiver.class).setAction(String.valueOf(ACTION_PAUSE));
        PendingIntent playPendingIntent = PendingIntent.getBroadcast(this, 0,
                playIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_custom_notification);

//        if(isPlaying){
//            remoteViews.setOnClickPendingIntent(R.id.img_play_or_pause,
//                    getPendingIntent(this, 1));
//            remoteViews.setImageViewResource(R.id.img_play_or_pause, R.drawable.pause_music);
////            Intent intent1 = new Intent("123");
////            intent1.putExtra("test","test");
////            isPlaying = false;
////            //sending
////            LocalBroadcastManager.getInstance(this).sendBroadcast(intent1);
//        }else{
//            remoteViews.setOnClickPendingIntent(R.id.img_play_or_pause,
//                    getPendingIntent(this, ACTION_PAUSE));
//            remoteViews.setImageViewResource(R.id.img_play_or_pause, R.drawable.play_music);

//            Intent intent2 = new Intent("1234");
//            intent2.putExtra("test2","test2");
//            isPlaying = true;
            //LocalBroadcastManager.getInstance(this).sendBroadcast(intent2);
       //}
        remoteViews.setOnClickPendingIntent(R.id.img_next, nextPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.img_previous, prevPendingIntent);


        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID) //add action cho tung button
                .setContentTitle("Foreground Service") //notification title
                .setContentText("abd")
                .setSmallIcon(R.drawable.music_video)
                .setSound(null)
                .setContentIntent(pendingIntent) //moi lan click vao noti lai intent sang layout video moi, de len layout cu, am van bat
                .setCustomContentView(remoteViews)
                .build();

        startForeground(1, notification);




    }

//    private PendingIntent getPendingIntent(Context context, int action){
//        Intent intent = new Intent(this, MyReceiver.class);
//        intent.putExtra("123", action);
//        //intent.putExtra("1234", action);
//
//        return PendingIntent.getBroadcast(context.getApplicationContext(),
//                action, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//    }

    private void handleActionMusic(int action){
        switch(action){
            case ACTION_PAUSE:
                pauseMusic();
                break;
            case ACTION_RESUME:
                resumeMusic();
                break;
            case ACTION_NEXT:
                nextMusic();
                break;
            case ACTION_PREVIOUS:
                prevMusic();
                break;
        }
    }

    private void nextMusic() {
        if (player != null && isPlaying){
            player.seekToNext();
        }
    }
    private void prevMusic(){
        if (player != null && isPlaying){
            player.seekToPrevious();
        }
    }

    private void resumeMusic() {
        if (player != null && !isPlaying){
            // player.prepare();
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
