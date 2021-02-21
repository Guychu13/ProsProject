package com.example.pros;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Random;

public class FriendlyGameWaitingRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendly_game_waiting_room);
    }


    private String createLobbyCode(){
        StringBuilder code = new StringBuilder();
        int limit;
        if (new Random().nextInt(10) == 0)
            limit = 5;
        else
            limit = 6;
        for (int i=0; i< limit; i++)
            code.append(new Random().nextInt(10));
        return code.toString();
    }
}