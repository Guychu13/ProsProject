package com.example.pros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class WinAndLossScreenActivity extends AppCompatActivity {

    private Button toMainScreenButton;
    private TextView titleTextView;
    private TextView secondTextTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_and_loss_screen);

        titleTextView = findViewById(R.id.textView_WinAndLossScreen_title);
        secondTextTextView = findViewById(R.id.textView_WinAndLossScreen_secondText);
        toMainScreenButton = findViewById(R.id.button_WinAndLossScreen_toMainScreenButton);

        Intent intent = getIntent();
        String winner = intent.getStringExtra("winner");
        if(winner.equals("myBlock")){
            titleTextView.setText(getResources().getString(R.string.WinAndLossScreen_myBlockWin_title));
            secondTextTextView.setText(getResources().getString(R.string.WinAndLossScreen_myBlockWin_secondText));
        }
        else{
            titleTextView.setText(getResources().getString(R.string.WinAndLossScreen_enemyCpuBlockWin_title));
            secondTextTextView.setText(getResources().getString(R.string.WinAndLossScreen_enemyCpuBlockWin_secondText));
        }
        toMainScreenButton.setText(getResources().getString(R.string.WinAndLossScreen_toMainScreenButtonText));
        toMainScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        startActivity(new Intent(WinAndLossScreenActivity.this, MainScreenActivity.class));
                    }
                }, 500);
            }
        });
    }
}