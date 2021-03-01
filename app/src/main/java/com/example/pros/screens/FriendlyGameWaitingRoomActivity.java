package com.example.pros.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.pros.R;
import com.example.pros.model.MultiPlayerGameDao;
import com.example.pros.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class FriendlyGameWaitingRoomActivity extends AppCompatActivity {


    private TextView myPlayerNameTextView, enemyPlayerNameTextView, lobbyCodeTextView;
    private ArrayList<String> allExistingGameCodes;
    private String gameCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendly_game_waiting_room);

        myPlayerNameTextView = findViewById(R.id.textView_friendlyGameWaitingRoomScreen_myPlayerName);
        enemyPlayerNameTextView = findViewById(R.id.textView_friendlyGameWaitingRoomScreen_enemyPlayerName);
        lobbyCodeTextView = findViewById(R.id.textView_friendlyGameWaitingRoomScreen_lobbyCode);

        myPlayerNameTextView.setText(User.getInstance().getUserName());
        enemyPlayerNameTextView.setText(getResources().getString(R.string.friendlyGameWaitingRoomScreen_questionMarks));
        allExistingGameCodes = new ArrayList<>();//לצורך הבדיקה אם יש כבר קוד כזה
        gameCode = createGameCode();
        lobbyCodeTextView.setText(gameCode);

//        MultiPlayerGameDao multiPlayerGameDao = new MultiPlayerGameDao()



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pros").child("gameCodes").child(gameCode);
//        myRef.setValue()פה לשים בווליו את הערכים שיהיו במשחק מולטיפלייר שצריך לגשת אליהם כל הזמן
// אולי לעשות מחלקה שיהיה לה תכונות של פרטים כללים כמו ניקוד ושמות שחקנים וסקינים וזה וגם פרטים יותר ספציפיים כמו מיקום כדור ודברים שצריך לעדכן קבוע
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
        if(!gameCodeExists(code.toString())){
            return code.toString();
        }
        return createGameCode();
    }

    private boolean gameCodeExists(final String newGameCode){
        allExistingGameCodes.clear();
        FirebaseDatabase.getInstance().getReference("Pros").child("gameCodes")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String code = snapshot.getValue(String.class);
                            allExistingGameCodes.add(code);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
        return allExistingGameCodes.contains(newGameCode);
    }
}