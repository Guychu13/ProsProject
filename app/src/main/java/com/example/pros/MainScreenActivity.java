package com.example.pros;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainScreenActivity extends AppCompatActivity {

    private User user;
    private String userName;

    private TextView userGreet;
    private ImageButton skinsButton;
    private ImageButton joinButton;
    private ImageView currentSkin;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private int currentSkinImageId;

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        currentSkin = findViewById(R.id.imageView_mainScreen_chosenSkin);
        Intent intentSkinChosen = getIntent();
        final int skinImageIdFromSkinsScreen = intentSkinChosen.getIntExtra("chosenSkinImageId", 0);
        final boolean isFromSkinsScreen = intentSkinChosen.getBooleanExtra("isFromSkinsScreen", false);

        currentSkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainScreenActivity.this, SkinsScreenActivity.class));
            }
        });

        skinsButton = findViewById(R.id.imageButton_mainScreen_skinsButton);
        skinsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainScreenActivity.this, SkinsScreenActivity.class));
            }
        });

        joinButton = findViewById(R.id.imageButton_mainScreen_joinButton);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toGameIntent = new Intent(MainScreenActivity.this, GameScreenActivity.class);
                if(skinImageIdFromSkinsScreen == 0){
                    toGameIntent.putExtra("chosenSkinImageId", R.drawable.skin_basic);
                }
                else{
                    toGameIntent.putExtra("chosenSkinImageId", skinImageIdFromSkinsScreen);
                }
                startActivity(toGameIntent);
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference().child("Pros").child("users").child(firebaseUser.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                user = snapshot.getValue(User.class);
                userName = user.getUserName();
                userGreet = findViewById(R.id.textView_mainScreen_usernameGreet);
                userGreet.setText("Hello, " + userName);

                if(skinImageIdFromSkinsScreen != 0 && isFromSkinsScreen){
                    currentSkinImageId = skinImageIdFromSkinsScreen;
                    user.setChosenSkinImageId(currentSkinImageId);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    database.getReference().child("Pros").child("users").child(firebaseUser.getUid()).child("chosenSkinImageId").setValue(currentSkinImageId);
                }

                currentSkin.setImageResource(user.getChosenSkinImageId());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

          }
        });
    }

    public void onSettingsClick(View view){
        startActivity(new Intent(MainScreenActivity.this, SettingsScreenActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onBackPressed() {

    }


}
