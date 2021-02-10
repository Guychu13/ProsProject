package com.example.pros.screens;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.pros.R;
import com.example.pros.model.User;
import com.example.pros.utils.Observer;

public class MainScreenPresenter implements Observer {

    private MainScreenActivity activity;
    private ImageButton toSkinsScreenButton;
    private ImageButton toJoinGameButton;
    private ImageButton toSettingsScreenButton;

    public MainScreenPresenter(MainScreenActivity activity){
        this.activity = activity;
        User.getInstance().register(this);


    }


    @Override
    public void update() {
        String userName = User.getInstance().getUserName();
        int chosenSkinImageId = User.getInstance().getChosenSkinImageId();

        activity.setUserNameGreetText(userName);
        activity.setChosenSkinImage(chosenSkinImageId);

//        toSettingsScreenButton = (activity).findViewById(R.id.imageButton_mainScreen_settingsButton);
//        toSettingsScreenButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activity.goToSettingsScreen();
//            }
//        });
//        toSkinsScreenButton = (activity).findViewById(R.id.imageButton_mainScreen_skinsButton);
//        toSkinsScreenButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activity.goToSkinsScreen();
//            }
//        });
//        toJoinGameButton = (activity).findViewById(R.id.imageButton_mainScreen_joinButton);
//        toJoinGameButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activity.goToGameScreen();
//            }
//        });
    }
}
