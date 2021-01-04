package com.example.pros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

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
                stopService(new Intent(getApplicationContext(), AppService.class));
            }
        });
        musicOnButton = findViewById(R.id.imageButton_settingsScreen_musicOn);
        musicOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicOffButton.setForeground(getDrawable(R.drawable.unpressed_off_music_button));
                musicOnButton.setForeground(getDrawable(R.drawable.pressed_on_music_button));
                startService(new Intent(getApplicationContext(), AppService.class));
            }
        });
    }

    public void onLogOut(View view){
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        startActivity(new Intent(SettingsScreenActivity.this, AfterLoadingActivity.class));
    }


    @Override
    public void onBackPressed() {
        finish();
    }

}
