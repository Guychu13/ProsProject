package com.example.pros.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.example.pros.AppMusicService;
import com.example.pros.R;
import com.example.pros.utils.SpotifyReceiver;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingActivity extends AppCompatActivity {

    private Intent intent;
    private SpotifyReceiver spotifyBroadcastReciever;
    private IntentFilter filter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(LoadingActivity.this, LoginSignUpActivity.class));
                finish();
            }
        }, 3000);

    }

    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(getApplicationContext(), AppMusicService.class));
        spotifyBroadcastReciever = new SpotifyReceiver();
        filter = new IntentFilter();
        filter.addAction("com.spotify.music.playbackstatechanged");
        filter.addAction("com.spotify.music.metadatachanged");
        filter.addAction("com.spotify.music.queuechanged");
        registerReceiver(spotifyBroadcastReciever, filter);
    }

    @Override
    protected void onPause() {//אולי יש צורך להעתיק את זה לכל מסך, צריך לבדוק את זה
        super.onPause();
        stopService(new Intent(getApplicationContext(), AppMusicService.class));
        unregisterReceiver(spotifyBroadcastReciever);
    }
}
