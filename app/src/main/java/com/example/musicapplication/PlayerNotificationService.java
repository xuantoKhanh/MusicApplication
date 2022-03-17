package com.example.musicapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

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

    String url1 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
    String url2 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4";
    String url3 = " http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4";

    private SimpleExoPlayer mPlayer;
    private PlayerView playerControlView;
    private DataSource.Factory dataSourceFactory;
    private PlayerNotificationManager playerNotificationManager;


    @Override
    public void onCreate() {
        super.onCreate();

//        ExoPlayer player = new ExoPlayer.Builder(this).build();
//        playerControlView = playerControlView.findViewById(R.id.exoPlayer);
//        // Bind the player to the view.
//        playerControlView.setPlayer(player);
//
//        // Build the media item.
//        MediaItem mediaItem1 = MediaItem.fromUri(url1);
//        MediaItem mediaItem2 = MediaItem.fromUri(url2);
//        MediaItem mediaItem3 = MediaItem.fromUri(url3);
//// Set the media item to be played.
//        player.addMediaItem(mediaItem1);
//        player.addMediaItem(mediaItem2);
//        player.addMediaItem(mediaItem3);
//// Prepare the player.
//        player.prepare(); //add list of media sources
//// Start the playback.
//        player.play();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID) //sua sang custom notification
                .setContentTitle("Foreground Service") //notification title
                .setContentText(input)
                .setSmallIcon(R.drawable.music_video)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        //do heavy work on a background thread
        //stopSelf();

        return START_NOT_STICKY;
    }

    // concatenatingMediaSource to pass media as a list,
    // so that we can easily prev, next
//    private fun getListOfMediaSource(): ConcatenatingMediaSource {
//        val mediaUrlList = ArrayList<String>()
//        mediaUrlList.add("https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8")
//        mediaUrlList.add("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8")
//        mediaUrlList.add("http://d3rlna7iyyu8wu.cloudfront.net/skip_armstrong/skip_armstrong_stereo_subs.m3u8")
//        mediaUrlList.add("https://moctobpltc-i.akamaihd.net/hls/live/571329/eight/playlist.m3u8")
//        mediaUrlList.add("https://multiplatform-f.akamaihd.net/i/multi/will/bunny/big_buck_bunny_,640x360_400,640x360_700,640x360_1000,950x540_1500,.f4v.csmil/master.m3u8")
//
//        val concatenatingMediaSource = ConcatenatingMediaSource()
//        for (mediaUrl in mediaUrlList) {
//            concatenatingMediaSource.addMediaSource(buildMediaSource(mediaUrl))
//        }
//
//        return concatenatingMediaSource
//
//    }
//
//    //build media source to player
//    private fun buildMediaSource(videoUrl: String): HlsMediaSource? {
//        val uri = Uri.parse(videoUrl)
//        // Create a HLS media source pointing to a playlist uri.
//        return HlsMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
//    }


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
