package com.example.pros.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.pros.db.Repository;
import com.example.pros.model.MultiPlayerGame;
import com.example.pros.utils.AppMusicService;
import com.example.pros.R;
import com.example.pros.model.User;
import com.example.pros.utils.SpotifyReceiver;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * המחלקה היא המחלקה המנהלת את מסך המשחק, והיא מקושרת אל קובץ ה-xml של מסך המשחק.
 */
public class GameScreenActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private int windowHeight, windowWidth;
    private GameView gameView;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private TextView scoreTextView, timerTextView, smileyTextView;
    /**
     * הזמן הנותר לתום המשחק.
     */
    private int gameTimerSecondsLeft;
    /**
     * 	כמות השניות שהמשחק מושהה לאחר הבקעת שער של אחד השחקנים.
     */
    private int timerPauseDurationMilliSecs;
    /**
     * 	הניקוד של כל אחד מן הבלוקים.
     */
    private int myBlockScore, enemyCpuBlockScore;
    /**
     * 	משתנה בוליאני המסמל אם הארכת זמן כבר בוצעה.
     */
    private boolean didOvertime;
    /**
     * 	משתנה המסמל האם מסך הניצחון/הפסד הופיע.
     */
    private boolean winScreenHasAppeared;

    private Intent originIntent;
    /**
     * משתנה בוליאני המסמל אם הבלוק הוא חלק ממשחק מקוון או לא.
     */
    private boolean isMultiplayer;
    /**
     * 	משתנה בוליאני המסמל האם הבלוק הוא של יוצר המשחק(P1).
     */
    private boolean isP1;

    private SpotifyReceiver spotifyBroadcastReciever;
    private IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);

        winScreenHasAppeared = false;

        frameLayout = findViewById(R.id.frameLayout_gameScreen_gameFrameLayout);
        timerTextView = findViewById(R.id.textView_gameScreen_timerTextVIew);
        scoreTextView = findViewById(R.id.textView_gameScreen_scoreTextVIew);
        smileyTextView = findViewById(R.id.textView_gameScreen_smileyTextVIew);

        timerPauseDurationMilliSecs = 0;//שהייה לאחרי גול
        gameTimerSecondsLeft = 60;
        timerTextView.setText(gameTimerSecondsLeft / 60 + ":0" + gameTimerSecondsLeft % 60);


        myBlockScore = 0;
        enemyCpuBlockScore = 0;
        scoreTextView.setText(myBlockScore + "-" + enemyCpuBlockScore);

        didOvertime = false;


        originIntent = getIntent();
        isMultiplayer = originIntent.getExtras().getBoolean("isMultiplayer");
        isP1 = originIntent.getExtras().getBoolean("isP1", false);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        windowHeight = frameLayout.getHeight();
        windowWidth = frameLayout.getWidth();
        gameView = new GameView(this, isMultiplayer, isP1, windowHeight, windowWidth, User.getInstance().getChosenSkinImageId(), new ScoreHandler());
        new Handler().postDelayed(new GameTimer(), 2000);
        frameLayout.addView(gameView);
    }

    public class ScoreHandler extends Handler {
        /**
         * 	הפעולה מופעלת לאחר שער, ומעדכנת את הניקוד בלוח התוצאות.
         * @param msg
         */
        @Override
        public void handleMessage(@NonNull Message msg) {

            scoreTextView.setText(msg.getData().getString("score_string"));
            myBlockScore = msg.getData().getInt("my_block_score_int");
            enemyCpuBlockScore = msg.getData().getInt("enemy_cpu_score_int");
            timerPauseDurationMilliSecs = 3000;//יוצר השהייה אחרי הגול
        }
    }

    public class GameTimer implements Runnable {
        /**
         * 	פעולה זו מנהלת את שעון המשחק ומפעילה את מסך הניצחון/הפסד בעת סיומו.
         */
        @Override
        public void run() {
            if (timerPauseDurationMilliSecs != 0) {
                new Handler().postDelayed(new GameTimer(), timerPauseDurationMilliSecs);
                timerPauseDurationMilliSecs = 0;
            } else {
                new Handler().postDelayed(new GameTimer(), 1000);
                gameTimerSecondsLeft -= 1;
                if (gameTimerSecondsLeft % 60 < 10) {
                    timerTextView.setText(gameTimerSecondsLeft / 60 + ":0" + gameTimerSecondsLeft % 60);
                } else {
                    timerTextView.setText(gameTimerSecondsLeft / 60 + ":" + gameTimerSecondsLeft % 60);
                }
                if (gameTimerSecondsLeft <= 0) {

                    if (myBlockScore > enemyCpuBlockScore && !winScreenHasAppeared) {
                        gameView.setGameOver();
                        Intent intent = new Intent(GameScreenActivity.this, WinAndLossScreenActivity.class);
                        intent.putExtra("winner", "myBlock");
                        startActivity(intent);
                        winScreenHasAppeared = true;

                    } else if (myBlockScore < enemyCpuBlockScore && !winScreenHasAppeared) {
                        gameView.setGameOver();
                        Intent intent = new Intent(GameScreenActivity.this, WinAndLossScreenActivity.class);
                        intent.putExtra("winner", "enemyCpuBlock");
                        startActivity(intent);
                        winScreenHasAppeared = true;
                    } else {
                        if (didOvertime) {
                            gameView.setGameOver();
//                            new Handler().postDelayed(new GameTimer(),1000);
                        } else {
                            gameTimerSecondsLeft = 30;
                            smileyTextView.setText(getResources().getString(R.string.gameScreen_overtimeText));
                            didOvertime = true;
//                            new Handler().postDelayed(new GameTimer(), 0);
                        }
                    }
                }
            }
        }
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
