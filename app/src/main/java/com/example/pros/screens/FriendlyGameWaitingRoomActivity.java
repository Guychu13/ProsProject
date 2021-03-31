package com.example.pros.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pros.R;
import com.example.pros.db.MultiPlayerGameDao;
import com.example.pros.db.Repository;
import com.example.pros.model.MultiPlayerGame;
import com.example.pros.model.User;
import com.example.pros.utils.Observer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

public class FriendlyGameWaitingRoomActivity extends AppCompatActivity implements Observer{


    private TextView p1NameTextView, p2NameTextView, lobbyCodeTextView;
    private String gameCode;
    private Button startGameButton;
    private MultiPlayerGameDao tempMultiPlayerGameDao;
    String guestGameCodeEntered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendly_game_waiting_room);

        lobbyCodeTextView = findViewById(R.id.textView_friendlyGameWaitingRoomScreen_lobbyCode);
        p1NameTextView = findViewById(R.id.textView_friendlyGameWaitingRoomScreen_myPlayerName);
        p2NameTextView = findViewById(R.id.textView_friendlyGameWaitingRoomScreen_enemyPlayerName);
        startGameButton = findViewById(R.id.button_friendlyGameWaitingRoomScreen_startGame);

        Intent intent = getIntent();
        boolean isHost = intent.getExtras().getBoolean("isHost");
        guestGameCodeEntered = intent.getExtras().getString("gameCode");

        MultiPlayerGame.getInstance().register(this);

        if(isHost){
            startGameButton.setVisibility(View.VISIBLE);
            startGameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(p2NameTextView.getText().toString().equals(R.string.friendlyGameWaitingRoomScreen_questionMarks)){
                        Toast.makeText(FriendlyGameWaitingRoomActivity.this, "Can't play multiplayer by yourself ;)", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Intent intentToGameScreenActivity = new Intent(FriendlyGameWaitingRoomActivity.this, GameScreenActivity.class);
                        intentToGameScreenActivity.putExtra("is_multiplayer", true);
                    }
                }
            });

            gameCode = createGameCode();
            lobbyCodeTextView.setText(gameCode);

            MultiPlayerGame.getInstance().createNewMultiPlayerGame(gameCode, User.getInstance().getUserName(), User.getInstance().getChosenSkinImageId());

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Pros").child("gameCodes").child(gameCode).child("p2PlayerName");

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.getValue(String.class).equals("")){
                        p2NameTextView.setText(getResources().getString(R.string.friendlyGameWaitingRoomScreen_questionMarks));
                    }
                    else{
                        p2NameTextView.setText(snapshot.getValue(String.class));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else{
            startGameButton.setVisibility(View.INVISIBLE);

            Repository.getInstance().updateCodeMultiPlayerGame(guestGameCodeEntered);
            MultiPlayerGame.getInstance().setP2PlayerName(guestGameCodeEntered, User.getInstance().getUserName());
            MultiPlayerGame.getInstance().setP2SkinImageID(guestGameCodeEntered, User.getInstance().getChosenSkinImageId());
            lobbyCodeTextView.setText(guestGameCodeEntered);
            update();
        }
    }


    private String createGameCode(){
        StringBuilder code = new StringBuilder();
        int limit;
        if (new Random().nextInt(10) == 0){
            limit = 5;
        }
        else{
            limit = 6;
        }
        for (int i=0; i< limit; i++){
            code.append(new Random().nextInt(10));
        }
        if(!GameCodeExists(code.toString())){
            return code.toString();
        }
        return createGameCode();
    }
    public boolean GameCodeExists(String gameCode){
        tempMultiPlayerGameDao = null;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pros").child("gameCodes").child(gameCode);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tempMultiPlayerGameDao = snapshot.getValue(MultiPlayerGameDao.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return tempMultiPlayerGameDao != null;
    }

    @Override
    public void update() {
        p1NameTextView.setText(MultiPlayerGame.getInstance().getP1PlayerName());
        if(MultiPlayerGame.getInstance().getP2PlayerName().equals(null)){
            p2NameTextView.setText(getResources().getString(R.string.friendlyGameWaitingRoomScreen_questionMarks));
        }
        else{
            p2NameTextView.setText(MultiPlayerGame.getInstance().getP2PlayerName());
        }
    }
}