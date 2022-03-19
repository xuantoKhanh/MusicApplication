package com.example.musicapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.StyledPlayerView;

public class MainActivity extends AppCompatActivity {
    public static ExoPlayer player;

    String url1 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";
    String url2 = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4";
    String url3 = " http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4";

    public static final String ACTION_RESUME =  "4";
    public static final String ACTION_PAUSE = "1";
    public static final String ACTION_NEXT =  "2";
    public static final String ACTION_PREVIOUS = "3";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player = new ExoPlayer.Builder(this).build();
        PlayerView playerControlView = findViewById(R.id.exoPlayer);
        // Bind the player to the view.
        playerControlView.setPlayer(player);
        handleBroadCast();
        // Build the media item.
        MediaItem mediaItem1 = MediaItem.fromUri(url1);
        MediaItem mediaItem2 = MediaItem.fromUri(url2);
        MediaItem mediaItem3 = MediaItem.fromUri(url3);
        // Set the media item to be played.
        player.addMediaItem(mediaItem2);
        player.addMediaItem(mediaItem1);
        player.addMediaItem(mediaItem3);
        // Prepare the player.
        player.prepare(); //add list of media sources
        // Start the playback.
        player.play();

        Intent serviceIntent = new Intent(this, PlayerNotificationService.class);
        //serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    private void handleBroadCast() {
        MyReceiver myReceiver = new MyReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                super.onReceive(context, intent);
                Log.d("Ynsuper","testtttt");
                player.setPlayWhenReady(false);
            }
        };


        //registering
        IntentFilter filter = new IntentFilter("123");
        filter.addAction("1234");
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver, filter);


//        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,
//                new IntentFilter("123"));
//
    }
}