package com.example.pros.model;

import android.graphics.Bitmap;

import com.example.pros.model.GameObject;

/**
 *	מחלקה זו מייצגת את הרקע במסך המשחק.
 */
public class Background extends GameObject {

    public Background(Bitmap bitmap, int xPos, int yPos, int windowWidth, int windowHeight) {
        super(bitmap, xPos, yPos, windowWidth, windowHeight);
    }

    public void move() {

    }
}
