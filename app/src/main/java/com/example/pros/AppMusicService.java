package com.example.pros;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.pros.model.User;

import java.util.Random;

public class AppMusicService extends Service {
    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        Random rg = new Random();
        int num = rg.nextInt(3);
        /////לעשות if של תכונה בסינגלטון
        if(User.getInstance().isMusicOn()){
            if (num == 0) {
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music_dreams);
            } else if (num == 1) {
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music_hiphop1);
            } else {
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music_hiphop2);
            }
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
    }

}
