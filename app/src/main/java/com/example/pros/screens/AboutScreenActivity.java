package com.example.pros.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pros.R;

public class AboutScreenActivity extends AppCompatActivity {

    Button toMainScreenButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_screen);

//        toMainScreenButton = findViewById(R.id.button_aboutScreen_toMainScreenButton);
//        toMainScreenButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }
}