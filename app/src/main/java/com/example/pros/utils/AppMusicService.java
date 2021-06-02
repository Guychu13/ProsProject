package com.example.pros.utils;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.pros.R;
import com.example.pros.model.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

/**
 * 	מחלקה זו מייצגת את מוזיקת הרקע של האפליקציה.
 */
public class AppMusicService extends Service {

    private MediaPlayer mediaPlayer;
    /**
     * 	מספר השיר שמתנגן כעת.
     */
    private static int songNum = 0;
    /**
     * הנקודה המדויקת בה השיר נעצר לאחר יציאה מ-Activity מסויים.
     */
    private static int songMSecTime = 0;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        playMusic();
//        songNum = 0;
//        if(FirebaseAuth.getInstance().getCurrentUser() != null){
//            if(User.getInstance().isMusicOn()){
//                playMusic();
//            }
//        }
//        else{
//            playMusic();
//        }
    }

    /**
     * פעולה זו היא הפעולה האחראית על הפעלת המוזיקה.
     */
    public void playMusic(){
        if(songNum == 0){
            Random rg = new Random();
            int num = rg.nextInt(3);
            if (num == 0) {
                songNum = 1;
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music_dreams);
            }
            else if (num == 1) {
                songNum = 2;
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music_hiphop1);
            }
            else {
                songNum = 3;
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music_hiphop2);
            }
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
        else{
            if(songNum == 1){
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music_dreams);
            }
            else if(songNum == 2){
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music_hiphop1);
            }
            else if(songNum == 3){
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music_hiphop2);
            }
        }
        mediaPlayer.seekTo(songMSecTime);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    /**
     * פעולה זו היא הפעולה המופעלת כאשר המוזיקה נעצרת.
     */
    @Override
    public void onDestroy() {
        mediaPlayer.pause();
        songMSecTime = mediaPlayer.getCurrentPosition();
    }
}
