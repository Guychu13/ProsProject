package com.example.pros.screens;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.pros.R;
import com.example.pros.model.User;
import com.example.pros.utils.Observer;

/**
 * מחלקה זו מייצגת את הPresenter- של המסך הראשי באפליקציה. מחלקה זו מממשת את מודל השכבות עליו מבוסס הקוד בפרויקט.
 * תפקידה של מחלקה זו הוא לעדכן את המוצג במסך אליו היא שייכת.
 */
public class MainScreenPresenter implements Observer {
    /**
     * המחלקה שאותה מחלקה זו תנהל.
     */
    private MainScreenActivity activity;

    /**
     * פעולה זו היא הפעולה הבונה של המחלקה, ובעת הפעלתה היא רושמת את עצמה כמאזינה למחלקה User(עליה פירטתי מעלה) ולשינויים בה.
     * @param activity
     */
    public MainScreenPresenter(MainScreenActivity activity){
        this.activity = activity;
        User.getInstance().register(this);
    }

    /**
     * פעולה זו תפעל כאשר המחלקה אליה מאזינה המחלקה הזו תתעדכן.
     */
    @Override
    public void update() {
        String userName = User.getInstance().getUserName();
        int chosenSkinImageId = User.getInstance().getChosenSkinImageId();

        activity.setUserNameGreetText(userName);
        activity.setChosenSkinImage(chosenSkinImageId);
    }
}
