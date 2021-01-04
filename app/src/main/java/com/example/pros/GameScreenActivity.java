package com.example.pros;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameScreenActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private int windowHeight, windowWidth, userCurrentSkinImageId;
    private User currentUser;
    private GameView gameView;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private TextView scoreTextView, timerTextView, smileyTextView;
    private int gameTimerSecondsLeft;
    private int timerPauseDurationMilliSecs;
    private int myBlockScore, enemyCpuBlockScore;
    boolean didOvertime;
    private boolean appeared;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);

        appeared = false;

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userReference = database.getReference().child("Pros").child("users").child(firebaseUser.getUid());
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUser = snapshot.getValue(User.class);
                userCurrentSkinImageId = currentUser.getChosenSkinImageId();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        frameLayout = findViewById(R.id.frameLayout_gameScreen_gameFrameLayout);
        scoreTextView = findViewById(R.id.textView_gameScreen_scoreTextVIew);
        scoreTextView.setText("0-0");

        timerTextView = findViewById(R.id.textView_gameScreen_timerTextVIew);
        gameTimerSecondsLeft = 150;
        timerTextView.setText("" + gameTimerSecondsLeft / 60 + ":" + gameTimerSecondsLeft % 60);
        timerPauseDurationMilliSecs = 0;
        myBlockScore = 0;
        enemyCpuBlockScore = 0;

        smileyTextView = findViewById(R.id.textView_gameScreen_smileyTextVIew);
        didOvertime = false;
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        windowHeight = frameLayout.getHeight();
        windowWidth = frameLayout.getWidth();
        gameView = new GameView(this, windowHeight, windowWidth, userCurrentSkinImageId, new ScoreHandler());
        new Handler().postDelayed(new GameTimer(),2000);
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

//    public class FinalScoreHandler extends Handler {
//
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//
//            myBlockFinalScore = msg.getData().getIntegerArrayList("final_score").get(0);
//            enemyCpuClockFinalScore = msg.getData().getIntegerArrayList("final_score").get(1);
//        }
//    }

    public class GameTimer implements Runnable {

        @Override
        public void run() {

                if(timerPauseDurationMilliSecs != 0){
                    new Handler().postDelayed(new GameTimer(), timerPauseDurationMilliSecs);
                    timerPauseDurationMilliSecs = 0;
                }
                else{
                    new Handler().postDelayed(new GameTimer(),1000);
                    gameTimerSecondsLeft -=1;
                    if(gameTimerSecondsLeft % 60 < 10){
                        timerTextView.setText("" + gameTimerSecondsLeft / 60 + ":0" + gameTimerSecondsLeft % 60);
                    }
                    else{
                        timerTextView.setText("" + gameTimerSecondsLeft / 60 + ":" + gameTimerSecondsLeft % 60);
                    }
                    if(gameTimerSecondsLeft <= 0){

                        if(myBlockScore > enemyCpuBlockScore && !appeared){
                            gameView.setGameOver();
                            startActivity(new Intent(GameScreenActivity.this, MyBlockWinScreenActivity.class));
                            appeared = true;

                        }
                        else if(myBlockScore < enemyCpuBlockScore && !appeared){
                            gameView.setGameOver();
                            startActivity(new Intent(GameScreenActivity.this, EnemyCpuBlockWinScreenActivity.class));
                            appeared = true;
                        }
                        else{
                            if(didOvertime){
                                gameView.setGameOver();
//                            new Handler().postDelayed(new GameTimer(),1000);
                            }
                            else{
                                gameTimerSecondsLeft = 30;
                                smileyTextView.setText("overtime");
                                didOvertime = true;
                                new Handler().postDelayed(new GameTimer(),0);
                            }
                        }
                    }
                }
//            }
        }
    }
}
