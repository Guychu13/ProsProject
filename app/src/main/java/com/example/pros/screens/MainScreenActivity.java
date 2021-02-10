package com.example.pros.screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pros.R;
import com.example.pros.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainScreenActivity extends AppCompatActivity {

    private User user;
    private String userName;

    private TextView userGreet;
//    private ImageButton skinsButton;
//    private ImageButton joinButton;
    private ImageView currentSkinImage;
//    private FirebaseAuth mAuth;
//    private FirebaseUser firebaseUser;
//    private int currentSkinImageId;
    private MainScreenPresenter mainScreenPresenter;

    //private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        mainScreenPresenter = new MainScreenPresenter(this);


//        mAuth = FirebaseAuth.getInstance();
//        firebaseUser = mAuth.getCurrentUser();

//        currentSkinImage = findViewById(R.id.imageView_mainScreen_chosenSkin);
//        joinButton = findViewById(R.id.imageButton_mainScreen_joinButton);

//        Intent intentSkinChosen = getIntent();
//        final int skinImageIdFromSkinsScreen = intentSkinChosen.getIntExtra("chosenSkinImageId", 0);
//        final boolean isFromSkinsScreen = intentSkinChosen.getBooleanExtra("isFromSkinsScreen", false);

//        currentSkinImage.setOnClickListener(new View.OnClickListener() {זה היה למעבר למסך הסקינים מלחיצה על הסקין שנבחר בנוסף לכפתור סקינים
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainScreenActivity.this, SkinsScreenActivity.class));
//            }
//        });

//        skinsButton = findViewById(R.id.imageButton_mainScreen_skinsButton);
//        skinsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainScreenActivity.this, SkinsScreenActivity.class));
//            }
//        });

//        joinButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent toGameIntent = new Intent(MainScreenActivity.this, GameScreenActivity.class);
////                if (skinImageIdFromSkinsScreen == 0) {
////                    toGameIntent.putExtra("chosenSkinImageId", R.drawable.skin_basic);
////                } else {
////                    toGameIntent.putExtra("chosenSkinImageId", skinImageIdFromSkinsScreen);
////                }
////                toGameIntent.putExtra("chosenSkinImageId", skinImageIdFromSkinsScreen);
//                startActivity(toGameIntent);
//            }
//        });

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        final DatabaseReference myRef = database.getReference().child("Pros").child("users").child(firebaseUser.getUid());
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                user = snapshot.getValue(User.class);
//                userName = user.getUserName();
//                userGreet = findViewById(R.id.textView_mainScreen_usernameGreet);
//                userGreet.setText(getResources().getString(R.string.mainScreen_usernameGreet) + " " + userName);
//
//                if (skinImageIdFromSkinsScreen != 0 && isFromSkinsScreen) {
//                    currentSkinImageId = skinImageIdFromSkinsScreen;
//                    user.setChosenSkinImageId(currentSkinImageId);
//                    FirebaseDatabase database = FirebaseDatabase.getInstance();
//                    database.getReference().child("Pros").child("users").child(firebaseUser.getUid()).child("chosenSkinImageId").setValue(currentSkinImageId);
//                }
//
//                currentSkin.setImageResource(user.getChosenSkinImageId());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

//    public void onSettingsClick(View view) {
//        startActivity(new Intent(MainScreenActivity.this, SettingsScreenActivity.class));
//    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onStart() {
        super.onStart();
    }


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
    }

    public void goToSkinsScreen(View view){
        startActivity(new Intent(MainScreenActivity.this, SkinsScreenActivity.class));
    }

    public void goToGameScreen(View view){
        startActivity(new Intent(MainScreenActivity.this, GameScreenActivity.class));
    }
}
