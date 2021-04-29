package com.example.pros.model;

import android.graphics.Bitmap;

public abstract class EnemyBlock extends GameObject {

    protected int score;
    protected int xSpeed;
    protected float xTarget;

    public EnemyBlock(Bitmap bitmap, int xPos, int yPos, int windowWidth, int windowHeight) {
        super(bitmap, xPos, yPos, windowWidth, windowHeight);
        this.score = 0;
        this.xSpeed = 12;
    }

    public void move(){
        if (xPos + bitmap.getWidth() / 2 < xTarget) {
            for (int i = 0; i < Math.abs(xSpeed); i++) {//כדי שאם יש לחיצה ארוכה הוא יזוז בצעד צעד עד שהוא לא יכול יותר. אם זה זז 20 20 אז הוא לא יסכים לזוז אם הוא נגיד רחוק 18 מהמסגרת
                if (xPos + bitmap.getWidth() < windowWidth) {
                    setXPos(xPos + 1);
                }
            }
        }
        if (xPos + bitmap.getWidth() / 2 > xTarget) {
            for (int i = 0; i < Math.abs(xSpeed); i++) {//כדי שאם יש לחיצה ארוכה הוא יזוז בצעד צעד עד שהוא לא יכול יותר. אם זה זז 20 20 אז הוא לא יסכים לזוז אם הוא נגיד רחוק 18 מהמסגרת
                if (xPos > 0) {
                    setXPos(xPos - 1);
                }
            }
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public float getxTarget() {
        return xTarget;
    }

    public void setxTarget(float xTarget) {
        this.xTarget = xTarget;
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }
}
