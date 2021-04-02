package com.example.pros.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.pros.R;
import com.example.pros.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GameScreenActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private int windowHeight, windowWidth;
    private GameView gameView;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private TextView scoreTextView, timerTextView, smileyTextView;
    private int gameTimerSecondsLeft;
    private int timerPauseDurationMilliSecs;
    private int myBlockScore, enemyCpuBlockScore;
    private boolean didOvertime;
    private boolean winScreenHasAppeared;

    private Intent originIntent;
    private boolean isMultiplayer;
    private boolean isP1;


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

        @Override
        public void handleMessage(@NonNull Message msg) {

            scoreTextView.setText(msg.getData().getString("score_string"));
            myBlockScore = msg.getData().getInt("my_block_score_int");
            enemyCpuBlockScore = msg.getData().getInt("enemy_cpu_score_int");
            timerPauseDurationMilliSecs = 3000;//יוצר השהייה אחרי הגול
        }
    }

    public class GameTimer implements Runnable {

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
//            }
        }
    }
}
