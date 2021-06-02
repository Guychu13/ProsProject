package com.example.pros.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pros.utils.AppMusicService;
import com.example.pros.R;
import com.example.pros.utils.SpotifyReceiver;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 	מחלקה זו מייצגת את מסך הניצחון/הפסד שיוצג למשתמשים בתום משחק.
 */
public class WinAndLossScreenActivity extends AppCompatActivity {

    private Button toMainScreenButton;
    private TextView titleTextView;
    private TextView secondTextTextView;
    private SpotifyReceiver spotifyBroadcastReciever;
    private IntentFilter filter;

    /**
     * פעולה זו היא הפעולה הראשונה המופעלת בעת כניסה למסך. בפעולה זו מתבצעת הבדיקה הקובעת האם המסך יוצג כמסך ניצחון או הפסד, ומוצגים הטקסטים המתאימים.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_and_loss_screen);

        titleTextView = findViewById(R.id.textView_WinAndLossScreen_title);
        secondTextTextView = findViewById(R.id.textView_WinAndLossScreen_secondText);
        toMainScreenButton = findViewById(R.id.button_WinAndLossScreen_toMainScreenButton);

        Intent intent = getIntent();
        String winner = intent.getStringExtra("winner");
        if(winner.equals("myBlock")){
            titleTextView.setText(getResources().getString(R.string.WinAndLossScreen_myBlockWin_title));
            secondTextTextView.setText(getResources().getString(R.string.WinAndLossScreen_myBlockWin_secondText));
        }
        else{
            titleTextView.setText(getResources().getString(R.string.WinAndLossScreen_enemyCpuBlockWin_title));
            secondTextTextView.setText(getResources().getString(R.string.WinAndLossScreen_enemyCpuBlockWin_secondText));
        }
        toMainScreenButton.setText(getResources().getString(R.string.WinAndLossScreen_toMainScreenButtonText));
        toMainScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        startActivity(new Intent(WinAndLossScreenActivity.this, MainScreenActivity.class));
                    }
                }, 500);
            }
        });
    }

    @Override
    protected void onPause() {//אולי יש צורך להעתיק את זה לכל מסך, צריך לבדוק את זה
        super.onPause();
        stopService(new Intent(getApplicationContext(), AppMusicService.class));
        unregisterReceiver(spotifyBroadcastReciever);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!SettingsScreenActivity.musicMuted){
            startService(new Intent(getApplicationContext(), AppMusicService.class));
        }
        spotifyBroadcastReciever = new SpotifyReceiver();
        filter = new IntentFilter();
        filter.addAction("com.spotify.music.playbackstatechanged");
        filter.addAction("com.spotify.music.metadatachanged");
        filter.addAction("com.spotify.music.queuechanged");
        registerReceiver(spotifyBroadcastReciever, filter);
    }
}