package com.example.pros.screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pros.FriendlyGameWaitingRoomActivity;
import com.example.pros.R;
import com.example.pros.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainScreenActivity extends AppCompatActivity {

    private User user;
//    private String userName;

    private TextView userGreet;
//    private ImageButton skinsButton;
//    private ImageButton joinButton;
    private ImageView currentSkinImage;
//    private FirebaseAuth mAuth;
//    private FirebaseUser firebaseUser;
//    private int currentSkinImageId;
    private MainScreenPresenter mainScreenPresenter;

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
    }

    public void goToSkinsScreen(View view){
        startActivity(new Intent(MainScreenActivity.this, SkinsScreenActivity.class));
    }

    public void goToGameScreen(View view){
        startActivity(new Intent(MainScreenActivity.this, GameScreenActivity.class));
    }

    public void goToFriendlyGameLobbyScreen(View view){
        startActivity(new Intent(MainScreenActivity.this, FriendlyGameWaitingRoomActivity.class));
    }
}
