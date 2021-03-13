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
import com.example.pros.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class FriendlyGameWaitingRoomActivity extends AppCompatActivity {


    private TextView p1NameTextView, p2NameTextView, lobbyCodeTextView;
    private ArrayList<String> allExistingGameCodes;
    private String gameCode;
    private Button startGameButton;

    private MultiPlayerGameDao tempMultiPlayerGameDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendly_game_waiting_room);

        final MultiPlayerGameDao multiPlayerGameDao = new MultiPlayerGameDao();
        tempMultiPlayerGameDao = null;

        p1NameTextView = findViewById(R.id.textView_friendlyGameWaitingRoomScreen_myPlayerName);
        p2NameTextView = findViewById(R.id.textView_friendlyGameWaitingRoomScreen_enemyPlayerName);
        lobbyCodeTextView = findViewById(R.id.textView_friendlyGameWaitingRoomScreen_lobbyCode);

        allExistingGameCodes = new ArrayList<>();//To check if the code is already taken
        gameCode = createGameCode();
        multiPlayerGameDao.setGameCode(gameCode);
        lobbyCodeTextView.setText(gameCode);

        Intent intent = getIntent();
        boolean isHost = intent.getExtras().getBoolean("isHost");
        if(isHost){
            multiPlayerGameDao.setP1PlayerName(User.getInstance().getUserName());
            multiPlayerGameDao.setP1SkinImageID(User.getInstance().getChosenSkinImageId());
        }
        else{
            multiPlayerGameDao.setP2PlayerName(User.getInstance().getUserName());
            multiPlayerGameDao.setP2SkinImageID(User.getInstance().getChosenSkinImageId());
        }

        p1NameTextView.setText(multiPlayerGameDao.getP1PlayerName());
        if(multiPlayerGameDao.getP2PlayerName() == null){
            p2NameTextView.setText(getResources().getString(R.string.friendlyGameWaitingRoomScreen_questionMarks));
        }
        else{
            p2NameTextView.setText(multiPlayerGameDao.getP2PlayerName());
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pros").child("gameCodes").child(gameCode);
        myRef.setValue(multiPlayerGameDao);

        startGameButton = findViewById(R.id.button_friendlyGameWaitingRoomScreen_startGame);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FriendlyGameWaitingRoomActivity.this, "It worked! , " + multiPlayerGameDao.getP1PlayerName(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private String createGameCode(){//שום מושג אם הבדיקה של אם הקוד קיים או לא עובדת
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

//    private boolean gameCodeExists(final String newGameCode){
//        allExistingGameCodes.clear();
//        FirebaseDatabase.getInstance().getReference("Pros").child("gameCodes")
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {/////////////לטפל פה לברר איזה טיפוס אני מקבל מהפיירבייס כי זה לא סטרינג כנראה
//                            String code = snapshot.getValue(String.class);
//                            allExistingGameCodes.add(code);
//                        }
//                    }
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                    }
//                });
//        return allExistingGameCodes.contains(newGameCode);
//    }

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
}