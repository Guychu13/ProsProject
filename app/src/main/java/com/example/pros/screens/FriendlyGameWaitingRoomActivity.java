package com.example.pros.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pros.utils.AppMusicService;
import com.example.pros.R;
import com.example.pros.db.MultiPlayerGameDao;
import com.example.pros.db.Repository;
import com.example.pros.model.MultiPlayerGame;
import com.example.pros.model.User;
import com.example.pros.utils.Observer;
import com.example.pros.utils.SpotifyReceiver;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

/**
 * מחלקה זו זה מייצג את מסך ההמתנה לפני משחק מקוון.
 */
public class FriendlyGameWaitingRoomActivity extends AppCompatActivity implements Observer{


    private TextView p1NameTextView, p2NameTextView, lobbyCodeTextView;
    /**
     * קוד ההצטרפות לחדר ההמתנה.
     */
    private String gameCode;
    private Button startGameButton;
    private MultiPlayerGameDao tempMultiPlayerGameDao;
    private String guestGameCodeEntered;
    private SpotifyReceiver spotifyBroadcastReciever;
    private IntentFilter filter;

    /**
     * פעולה זו היא הפעולה הראשונה שמופעלת בעת כניסה למסך, ובה מתבצעת ההפרדה בין המשתמש שיצר את החדר לבין המשתמש שהצטרף אל החדר.
     * @param savedInstanceState
     */
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
            gameCode = createGameCode();
            lobbyCodeTextView.setText(gameCode);

            MultiPlayerGame.getInstance().createNewMultiPlayerGame(gameCode, User.getInstance().getUserName(), User.getInstance().getChosenSkinImageId());
            //את זה אפשר להפריד ליצירה של משחק ואז לעשות סטים לשם של שחקן 1 ולסקין שלו אבל זה דרך קיצור
            //לעשות הכל בפעולה שיוצרת משחק כי תמיד יהיו לי התכונות האלו ביד כשנוצר משחק

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

            startGameButton.setVisibility(View.VISIBLE);
            startGameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(p2NameTextView.getText().toString().equals(getResources().getString(R.string.friendlyGameWaitingRoomScreen_questionMarks))){
                        Toast.makeText(FriendlyGameWaitingRoomActivity.this, "Can't play multiplayer by yourself ;)", Toast.LENGTH_LONG).show();
                    }
                    else{
                        MultiPlayerGame.getInstance().setGameStarted(MultiPlayerGame.getInstance().getGameCode(),true);
                        Intent intentToGameScreenActivity = new Intent(FriendlyGameWaitingRoomActivity.this, GameScreenActivity.class);
                        intentToGameScreenActivity.putExtra("isMultiplayer", true);
                        intentToGameScreenActivity.putExtra("isP1", true);
                        startActivity(intentToGameScreenActivity);
                    }
                }
            });
        }
        else{
            startGameButton.setVisibility(View.INVISIBLE);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Pros").child("gameCodes").child(guestGameCodeEntered);
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    tempMultiPlayerGameDao = snapshot.getValue(MultiPlayerGameDao.class);
                    Repository.getInstance().setMultiPlayerGame(tempMultiPlayerGameDao);
                    MultiPlayerGame.getInstance().update();
                    MultiPlayerGame.getInstance().setP2PlayerName(MultiPlayerGame.getInstance().getGameCode(), User.getInstance().getUserName());
                    MultiPlayerGame.getInstance().setP2SkinImageID(MultiPlayerGame.getInstance().getGameCode(), User.getInstance().getChosenSkinImageId());
                    lobbyCodeTextView.setText(MultiPlayerGame.getInstance().getGameCode());
                    update();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

//            Repository.getInstance().updateCodeMultiPlayerGame(guestGameCodeEntered);


            //בדיקה אם המשחק התחיל או לא
            myRef = database.getReference("Pros").child("gameCodes").child(guestGameCodeEntered).child("gameStarted");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Boolean started = snapshot.getValue(Boolean.class);
                    if(started){
                        Intent intentToGameScreenActivity = new Intent(FriendlyGameWaitingRoomActivity.this, GameScreenActivity.class);
                        intentToGameScreenActivity.putExtra("isMultiplayer", true);
                        intentToGameScreenActivity.putExtra("isP1", false);
                        startActivity(intentToGameScreenActivity);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    /**
     * הפעולה בוחרת באופן רנדומלי מספרים ויוצרת קוד משחק חדש שאינו קיים בבסיס הנתונים.
     * @return
     */
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

    /**
     * הפעולה בודקת האם קוד מסוים קיים כבר בבסיס הנתונים.
     * @param gameCode
     * @return
     */
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

    /**
     * פעולה זו היא הפעולה המופעלת כאשר המחלקה אליה מאזינה המחלקה הנ"ל, מעדכנת את המאזינים לה על שינוי.
     * הפעולה מקבלת את המסר שהיה שינוי, ומעדכנת את נתוניה.
     */
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

    @Override
    protected void onPause() {//אולי יש צורך להעתיק את זה לכל מסך, צריך לבדוק את זה
        super.onPause();
        stopService(new Intent(getApplicationContext(), AppMusicService.class));
        unregisterReceiver(spotifyBroadcastReciever);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!SettingsScreenActivity.musicMuted){
            startService(new Intent(getApplicationContext(), AppMusicService.class));
        }
        spotifyBroadcastReciever = new SpotifyReceiver();
        filter = new IntentFilter();
        filter.addAction("com.spotify.music.playbackstatechanged");
        filter.addAction("com.spotify.music.metadatachanged");
        filter.addAction("com.spotify.music.queuechanged");
        registerReceiver(spotifyBroadcastReciever, filter);
    }
}