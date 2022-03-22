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
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class MainActivity extends AppCompatActivity {
    public static ExoPlayer player;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputStream is = getResources().openRawResource(R.raw.music);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String jsonString = writer.toString();
        Gson gson = new Gson();
        TrackFiles trackFiles = gson.fromJson(jsonString, TrackFiles.class);

        Log.e("test", trackFiles + "");



        player = new ExoPlayer.Builder(this).build();
        PlayerView playerControlView = findViewById(R.id.exoPlayer);
        // Bind the player to the view.
        playerControlView.setPlayer(player);
        handleBroadCast();
//        // Build the media item.
//        MediaItem mediaItem1 = MediaItem.fromUri(url1);
//        MediaItem mediaItem2 = MediaItem.fromUri(url2);
//        MediaItem mediaItem3 = MediaItem.fromUri(url3);
//        // Set the media item to be played.
//        player.addMediaItem(mediaItem1);
//        player.addMediaItem(mediaItem2);
//        player.addMediaItem(mediaItem3);
//        // Prepare the player.
//        player.prepare(); //add list of media sources
//        // Start the playback.
//        player.play();

        Intent serviceIntent = new Intent(this, PlayerNotificationService.class);
        //serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    private void handleBroadCast() {
        MyReceiver myReceiver = new MyReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                super.onReceive(context, intent);
                Log.d("Ynsuper","|testtttt");
                player.setPlayWhenReady(false);
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,new IntentFilter("123"));
    }
}