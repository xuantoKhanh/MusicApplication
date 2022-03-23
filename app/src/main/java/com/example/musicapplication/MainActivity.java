package com.example.musicapplication;

import static com.google.android.exoplayer2.Player.TIMELINE_CHANGE_REASON_PLAYLIST_CHANGED;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

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

public class MainActivity extends AppCompatActivity implements Player.Listener {
    public static ExoPlayer player;
    int position = 0;
    TextView title;
    public static List<ResponseMusic> listResponseMusic;
    private Button buttonNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.title);
        buttonNext = findViewById(R.id.button_next);

        buttonNext.setOnClickListener(view -> {
                    if (player != null) {
                        player.seekToNext();
                    }
                }
        );
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
        Type typeOfList = new TypeToken<ArrayList<ResponseMusic>>() {
        }.getType();
        listResponseMusic = gson.fromJson(jsonString, typeOfList);


        player = new ExoPlayer.Builder(this).build();
        PlayerView playerControlView = findViewById(R.id.exoPlayer);
        // Bind the player to the view.
        playerControlView.setPlayer(player);
        player.addListener(this);
        handleBroadCast();


        // Build the media item.
        for (int i = 0; i < listResponseMusic.size(); i++) {
            MediaItem mediaItem = MediaItem.fromUri(String.valueOf(listResponseMusic.get(i).getSources().get(0)));
            player.addMediaItem(mediaItem);
            // Prepare the player.
            player.prepare(); //add list of media sources
            // Start the playback.
            player.play();

            Intent serviceIntent = new Intent(this, PlayerNotificationService.class);
            ContextCompat.startForegroundService(this, serviceIntent);
        }
    }

    @Override
    public void onMediaItemTransition(
            @Nullable MediaItem mediaItem, @Player.MediaItemTransitionReason int reason) {
        title.setText(listResponseMusic.get(player.getCurrentMediaItemIndex()).getTitle());
        Log.d("Ynsuper", "MediaItem: " + listResponseMusic.get(player.getCurrentMediaItemIndex()).getTitle());
    }

    private void handleBroadCast() {
        MyReceiver myReceiver = new MyReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                super.onReceive(context, intent);
                Log.d("Ynsuper", "|testtttt");
                player.setPlayWhenReady(false);
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver, new IntentFilter("123"));
    }


    @Override
    public void onTimelineChanged(
            Timeline timeline, @Player.TimelineChangeReason int reason) {
        if (reason == TIMELINE_CHANGE_REASON_PLAYLIST_CHANGED) {
            // Update the UI according to the modified playlist (add, move or remove).

        }
    }
}