package com.example.pros.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.pros.utils.AppMusicService;
import com.example.pros.R;
import com.example.pros.utils.SpotifyReceiver;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 	מחלקה זו מייצגת את מסך הטעינה של האפליקציה.
 */
public class LoadingActivity extends AppCompatActivity {

    private SpotifyReceiver spotifyBroadcastReciever;
    private IntentFilter filter;

    /**
     * פעולה זו היא הפעולה הראשונה המופעלת בעת כניסה למסך. בפעולה זו מוחלות האנימציות על האיורים השונים במסך הטעינה, ומבצע המעבר אל המסך הבא.
     * @param savedInstanceState
     */
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

        //בלוק תחתון
        Animation bottomBlockAnimation = AnimationUtils.loadAnimation(this, R.anim.loading_screen_bottom_block_animation);
        findViewById(R.id.imageView_loadingScreen_bottomBlock).startAnimation(bottomBlockAnimation);
        //בלוק עליון
        final Animation topBlockAnimation = AnimationUtils.loadAnimation(this, R.anim.loading_screen_top_block_animation);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                findViewById(R.id.imageView_loadingScreen_topBlock).startAnimation(topBlockAnimation);
            }
        }, 500);
        //כדור וניצוצות
        Animation ballAnimation = AnimationUtils.loadAnimation(this, R.anim.loading_screen_ball_and_sparks_animation);
        findViewById(R.id.imageView_loadingScreen_ball).startAnimation(ballAnimation);
        findViewById(R.id.imageView_loadingScreen_spark1).startAnimation(ballAnimation);
        findViewById(R.id.imageView_loadingScreen_spark2).startAnimation(ballAnimation);
        findViewById(R.id.imageView_loadingScreen_spark3).startAnimation(ballAnimation);
        //כותרת
        Animation titleAnimation = AnimationUtils.loadAnimation(this, R.anim.loading_screen_title_animation);
        findViewById(R.id.textView_loadingScreen_title).startAnimation(titleAnimation);

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
}
