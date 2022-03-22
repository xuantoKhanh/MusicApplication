package com.example.musicapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static ExoPlayer player;
    int position = 0;

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
        Type userListType = new TypeToken<ArrayList<TrackList>>(){}.getType();

        ArrayList<TrackList> trackLists = gson.fromJson(jsonString, userListType);

        Log.e("test", trackLists + "");


        player = new ExoPlayer.Builder(this).build();
        PlayerView playerControlView = findViewById(R.id.exoPlayer);
        // Bind the player to the view.
        playerControlView.setPlayer(player);
        handleBroadCast();
        player.setMediaSource((MediaSource) trackLists.get(position).getSources());

        //MediaItem item = MediaItem.fromUri(url2);

        // Build the media item.
       //MediaItem mediaItem1 = MediaItem.trackLists.getSource();
//        MediaItem mediaItem2 = MediaItem.fromUri(url2);
//        MediaItem mediaItem3 = MediaItem.fromUri(url3);
        // Set the media item to be played.
//        player.addMediaItem(mediaItem2);
//        player.addMediaItem(mediaItem1);
//        player.addMediaItem(mediaItem3);
        // Prepare the player.
        player.prepare(); //add list of media sources
        // Start the playback.
        player.play();

        Intent serviceIntent = new Intent(this, PlayerNotificationService.class);
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
       // filter.addAction("1234");
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver, filter);


//        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,
//                new IntentFilter("123"));
//
    }
}