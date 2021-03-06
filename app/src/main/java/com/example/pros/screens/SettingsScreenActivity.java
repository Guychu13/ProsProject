package com.example.pros.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import com.example.pros.utils.AppMusicService;
import com.example.pros.R;
import com.example.pros.model.User;
import com.example.pros.utils.SpotifyReceiver;
import com.google.firebase.auth.FirebaseAuth;

/**
 * 	מחלקה זו מייצגת את מסך ההגדרות של האפליקציה.
 */
public class SettingsScreenActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ImageButton logOutButton, musicOnButton, musicOffButton;
    private float xStart, yStart, xEnd, yEnd;
    /**
     * משתנה בוליאני סטטי המציין האם המשתמש השתיק את המוזיקה באפליקציה.
     */
    public static boolean musicMuted = false;

    private SpotifyReceiver spotifyBroadcastReciever;
    private IntentFilter filter;

    /**
     * פעולה זו היא הפעולה הראשונה המופעלת בעת כניסה למסך, ובעת הפעלתה היא מצמידה מאזינים שיפעילו את הפעולות המתאימות בעת לחיצה על כפתור ההפעלה או הכיבוי של המוזיקה.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);
        logOutButton = findViewById(R.id.imageButton_settingsScreen_loginButton);
        musicOffButton = findViewById(R.id.imageButton_settingsScreen_musicOff);
        musicOnButton = findViewById(R.id.imageButton_settingsScreen_musicOn);

        if(musicMuted){
            musicOffButton.setForeground(getDrawable(R.drawable.pressed_off_music_button));
            musicOnButton.setForeground(getDrawable(R.drawable.unpressed_on_music_button));
        }
        else{
            musicOffButton.setForeground(getDrawable(R.drawable.unpressed_off_music_button));
            musicOnButton.setForeground(getDrawable(R.drawable.pressed_on_music_button));
        }
        musicOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicOffButton.setForeground(getDrawable(R.drawable.pressed_off_music_button));
                musicOnButton.setForeground(getDrawable(R.drawable.unpressed_on_music_button));
                stopService(new Intent(getApplicationContext(), AppMusicService.class));
                musicMuted = true;
                User.getInstance().setMusicOn(false);
            }
        });

        musicOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicOffButton.setForeground(getDrawable(R.drawable.unpressed_off_music_button));
                musicOnButton.setForeground(getDrawable(R.drawable.pressed_on_music_button));
                startService(new Intent(getApplicationContext(), AppMusicService.class));
                musicMuted = false;
                User.getInstance().setMusicOn(true);
            }
        });
    }

    /**
     * פעולה זו מבצעת את תהליך ההתנתקות של משתמש מן האפליקציה לאחר לחיצה על הכפתור המתאים במסך.
     * @param view
     */
    public void onLogOut(View view) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        startActivity(new Intent(SettingsScreenActivity.this, LoginSignUpActivity.class));
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
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

    @Override
    protected void onPause() {//אולי יש צורך להעתיק את זה לכל מסך, צריך לבדוק את זה
        super.onPause();
        stopService(new Intent(getApplicationContext(), AppMusicService.class));
        unregisterReceiver(spotifyBroadcastReciever);
    }

    @Override
    public boolean onTouchEvent(MotionEvent touchEvent) {
        switch (touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                xStart = touchEvent.getX();
                yStart = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                xEnd = touchEvent.getX();
                yEnd = touchEvent.getY();
                if(xEnd < xStart && xStart - xEnd > 30){//Left Swipe
//                    Intent intent = new Intent(SettingsScreenActivity.this, MainScreenActivity.class);
//                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                break;
        }
        return false;
    }
}
