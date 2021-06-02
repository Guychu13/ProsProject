package com.example.pros.model;

import android.graphics.Bitmap;

/**
 * מחלקה זו היא המחלקה המייצגת את הבלוק של אויבו של המשתמש באפליקציה בזמן משחק לא מקוון.
 */
public class EnemyCpuBlock extends EnemyBlock{


    public EnemyCpuBlock(Bitmap bitmap, int xPos, int yPos, int windowWidth, int windowHeight) {
        super(bitmap, xPos, yPos, windowWidth, windowHeight);
    }

}
