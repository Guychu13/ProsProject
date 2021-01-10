package com.example.pros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class MyBlockWinScreenActivity extends AppCompatActivity {

    private Button toMainScreenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_block_win_screen);

        toMainScreenButton = findViewById(R.id.button_myBlockWinScreen_toMainScreenButton);
        toMainScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        startActivity(new Intent(MyBlockWinScreenActivity.this, MainScreenActivity.class));
                    }
                }, 500);
            }
        });
    }
}