package com.example.pros.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.pros.AppMusicService;
import com.example.pros.R;
import com.example.pros.model.User;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsScreenActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ImageButton logOutButton, musicOnButton, musicOffButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);
        logOutButton = findViewById(R.id.imageButton_settingsScreen_loginButton);

        musicOffButton = findViewById(R.id.imageButton_settingsScreen_musicOff);
        musicOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicOffButton.setForeground(getDrawable(R.drawable.pressed_off_music_button));
                musicOnButton.setForeground(getDrawable(R.drawable.unpressed_on_music_button));
                stopService(new Intent(getApplicationContext(), AppMusicService.class));
                User.getInstance().setMusicOn(false);
            }
        });
        musicOnButton = findViewById(R.id.imageButton_settingsScreen_musicOn);
        musicOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicOffButton.setForeground(getDrawable(R.drawable.unpressed_off_music_button));
                musicOnButton.setForeground(getDrawable(R.drawable.pressed_on_music_button));
                startService(new Intent(getApplicationContext(), AppMusicService.class));
                User.getInstance().setMusicOn(true);
            }
        });
    }

    public void onLogOut(View view) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        startActivity(new Intent(SettingsScreenActivity.this, LoginSignUpActivity.class));
    }


    @Override
    public void onBackPressed() {
        finish();
    }

}
