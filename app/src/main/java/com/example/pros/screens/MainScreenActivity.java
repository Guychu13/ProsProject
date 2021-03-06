package com.example.pros.screens;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.pros.utils.AppMusicService;
import com.example.pros.R;
import com.example.pros.db.MultiPlayerGameDao;
import com.example.pros.utils.SpotifyReceiver;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * מחלקה זו מייצגת את המסך הראשי באפליקציה והיא מקושרת אל קובץ ה-xml של המסך הראשי.
 */
public class MainScreenActivity extends AppCompatActivity {

    private Dialog joinGameDialog;
    private EditText gameCodeDialogEditText;
    private Button joinGameDialogButton;
    /**
     * קוד ההצטרפות של החדר אליו המשתמש ירצה להצטרף.
     */
    private String gameCodeTyped;
    boolean gameCodeExists;
    private MultiPlayerGameDao multiPlayerGameDao;

    private TextView userGreet;
    private ImageView currentSkinImage;
    private MainScreenPresenter mainScreenPresenter;

    private float xStart, yStart, xEnd, yEnd;

    private boolean codeExists;

    private SpotifyReceiver spotifyBroadcastReciever;
    private IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        mainScreenPresenter = new MainScreenPresenter(this);
        mainScreenPresenter.update();
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

    @Override
    protected void onPause() {//אולי יש צורך להעתיק את זה לכל מסך, צריך לבדוק את זה
        super.onPause();
        stopService(new Intent(getApplicationContext(), AppMusicService.class));
        unregisterReceiver(spotifyBroadcastReciever);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent touchEvent) {
        switch (touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                xStart = touchEvent.getX();
                yStart = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                xEnd = touchEvent.getX();
                yEnd = touchEvent.getY();
                if(xStart < xEnd && xEnd - xStart > 30){//Right Swipe
                    Intent intent = new Intent(MainScreenActivity.this, SettingsScreenActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
                if(xEnd < xStart && xStart - xEnd > 30){//Left Swipe
                    Intent intent = new Intent(MainScreenActivity.this, SkinsScreenActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                break;
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.main_screen, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

//    public void openMenu(View view){
//        this.openMenu(view);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.button_about:
//                startActivity(new Intent(MainScreenActivity.this, AboutScreenActivity.class));
//                return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    public void setUserNameGreetText(String userName) {//עדיין יש את החלק של הפיירבייס למעלה שעושה אותו דבר
        userGreet = findViewById(R.id.textView_mainScreen_usernameGreet);
        userGreet.setText(getResources().getString(R.string.mainScreen_usernameGreet) + " " + userName);
    }

    public void setChosenSkinImage(int chosenSkinImageId) {//עדיין יש את החלק של הפיירבייס למעלה שעושה אותו דבר
        currentSkinImage = findViewById(R.id.imageView_mainScreen_chosenSkin);
        currentSkinImage.setImageResource(chosenSkinImageId);
    }

    public void goToSettingsScreen(View view){
        startActivity(new Intent(MainScreenActivity.this, SettingsScreenActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void goToSkinsScreen(View view){
        startActivity(new Intent(MainScreenActivity.this, SkinsScreenActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void goToOfflineGameScreen(View view){
        Intent intent = new Intent(MainScreenActivity.this, GameScreenActivity.class);
        intent.putExtra("isMultiplayer", false);
        startActivity(intent);
    }

    public void goToFriendlyGameLobbyScreen(View view){
        Intent intent = new Intent(MainScreenActivity.this, FriendlyGameWaitingRoomActivity.class);
        intent.putExtra("isHost", true);
        startActivity(intent);
    }

    /**
     * פעולה זו בודקת האם אכן קיים חדר המתנה עם הקוד שהוקלד, ובמידה וכן, מצרפת את המשתמש.
     * @param view
     */
    public void goToJoinGameDialog(View view) {
        joinGameDialog = new Dialog(MainScreenActivity.this);
        joinGameDialog.setContentView(R.layout.dialog_join_online_game);
        joinGameDialog.setCanceledOnTouchOutside(true);
        joinGameDialog.setCancelable(true);
        joinGameDialog.show();

        gameCodeDialogEditText = joinGameDialog.findViewById(R.id.editText_joinGameDialog_codeEditText);
        joinGameDialogButton = joinGameDialog.findViewById(R.id.button_joinGameDialog_joinButton);

        joinGameDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Pros").child("gameCodes");

                gameCodeTyped = gameCodeDialogEditText.getText().toString();

                if(gameCodeTyped.equals("")){
                    Toast.makeText(MainScreenActivity.this, "Please type the game code", Toast.LENGTH_LONG).show();
                }
                else{
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(gameCodeTyped)){
                                Intent intent = new Intent(MainScreenActivity.this, FriendlyGameWaitingRoomActivity.class);
                                intent.putExtra("isHost", false);
                                intent.putExtra("gameCode", gameCodeTyped);
                                startActivity(intent);

//                                if(Repository.getInstance().lobbyIsFull(gameCodeTyped)){
//                                    Toast.makeText(MainScreenActivity.this, "Game is already full", Toast.LENGTH_LONG).show();
//                                }
//                                else {
//                                    Intent intent = new Intent(MainScreenActivity.this, FriendlyGameWaitingRoomActivity.class);
//                                    intent.putExtra("isHost", false);
//                                    intent.putExtra("gameCode", gameCodeTyped);
//                                    startActivity(intent);
//                                }
                            }
                            else{
                                Toast.makeText(MainScreenActivity.this, "Game code does not exist", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
}
