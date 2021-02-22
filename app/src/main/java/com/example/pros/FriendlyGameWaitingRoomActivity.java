package com.example.pros;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.pros.model.User;

import java.util.Random;

public class FriendlyGameWaitingRoomActivity extends AppCompatActivity {


    private TextView myPlayerNameTextView, enemyPlayerNameTextView, lobbyCodeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendly_game_waiting_room);

        myPlayerNameTextView = findViewById(R.id.textView_friendlyGameWaitingRoomScreen_myPlayerName);
        enemyPlayerNameTextView = findViewById(R.id.textView_friendlyGameWaitingRoomScreen_enemyPlayerName);
        lobbyCodeTextView = findViewById(R.id.textView_friendlyGameWaitingRoomScreen_lobbyCode);

        myPlayerNameTextView.setText(User.getInstance().getUserName());
        enemyPlayerNameTextView.setText(getResources().getString(R.string.friendlyGameWaitingRoomScreen_questionMarks));
        lobbyCodeTextView.setText(createLobbyCode());
    }


    private String createLobbyCode(){//אין כרגע בדיקה אם הקוד קיים או לא
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