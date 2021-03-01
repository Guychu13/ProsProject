package com.example.pros.screens;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pros.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.xml.datatype.Duration;

public class MainScreenActivity extends AppCompatActivity {

    private TextView userGreet;
    private ImageView currentSkinImage;
    private MainScreenPresenter mainScreenPresenter;
    private EditText dialogGameCodeEditText;
    private ImageView dialogJoinGameImageView;
    private String dialogGameCodeTyped;
    private Dialog dialogJoinOfflineGame;

    private float xStart, yStart, xEnd, yEnd;
    //private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        mainScreenPresenter = new MainScreenPresenter(this);
    }

    @Override
    public void onBackPressed() {

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
                if(xStart < xEnd && xEnd - xStart > 30){//Right Swipe
                    Intent intent = new Intent(MainScreenActivity.this, SettingsScreenActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
                if(xEnd < xStart && xStart - xEnd > 30){//Left Swipe
                    Intent intent = new Intent(MainScreenActivity.this, SkinsScreenActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                break;
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    public void setUserNameGreetText(String userName) {//עדיין יש את החלק של הפיירבייס למעלה שעושה אותו דבר
        userGreet = findViewById(R.id.textView_mainScreen_usernameGreet);
        userGreet.setText(getResources().getString(R.string.mainScreen_usernameGreet) + " " + userName);
    }

    public void setChosenSkinImage(int chosenSkinImageId) {//עדיין יש את החלק של הפיירבייס למעלה שעושה אותו דבר
        currentSkinImage = findViewById(R.id.imageView_mainScreen_chosenSkin);
        currentSkinImage.setImageResource(chosenSkinImageId);
    }

    public void goToSettingsScreen(View view){
        startActivity(new Intent(MainScreenActivity.this, SettingsScreenActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void goToSkinsScreen(View view){
        startActivity(new Intent(MainScreenActivity.this, SkinsScreenActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void goToGameScreen(View view){
        startActivity(new Intent(MainScreenActivity.this, GameScreenActivity.class));
    }

    public void goToFriendlyGameLobbyScreen(View view){
        startActivity(new Intent(MainScreenActivity.this, FriendlyGameWaitingRoomActivity.class));
    }

    public void goToJoinOnlineGame(View view){
        dialogJoinOfflineGame = new Dialog(MainScreenActivity.this);
        dialogJoinOfflineGame.setContentView(R.layout.join_offline_game_dialog);
        dialogGameCodeEditText = dialogJoinOfflineGame.findViewById(R.id.editText_joinOfflineGameDialog_gameCodeEditText);
        dialogJoinGameImageView = findViewById(R.id.imageVIew_joinOfflineGameDialog_join);
        dialogJoinOfflineGame.show();
        dialogJoinOfflineGame.setCancelable(true);
        dialogJoinOfflineGame.setCanceledOnTouchOutside(true);

        dialogJoinGameImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogGameCodeTyped = dialogGameCodeEditText.getText().toString();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference().child("Pros").child("gameCodes").child(dialogGameCodeTyped);
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.getValue() == null){
                            Toast.makeText(MainScreenActivity.this, "Game code does not exist", Toast.LENGTH_LONG).show();
                        }
                        else{

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                /////////////////////////////////////////////////////////////////////////here to check if the code matches an existing code in the firebase databade
            }
        });
    }
}
