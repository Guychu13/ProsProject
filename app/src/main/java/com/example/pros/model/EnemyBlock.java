package com.example.pros.model;

import android.graphics.Bitmap;

/**
 * מחלקה זו היא מחלקת האב של שני מחלקות האויבים השונים במשחק עליהם אפרט בהמשך(אויב מקוון ואויב מתוכנת).
 * מחלקה זו יורשת מהמחלקה GameObject כמו כל שאר האובייקטים במשחק.
 */
public abstract class EnemyBlock extends GameObject {

    /**
     * ניקוד האויב בעת משחק.
     */
    protected int score;
    /**
     * מהירות האויב.
     */
    protected int xSpeed;
    /**
     * שיעור ה-X שלכיוונו האויב יתקדם.
     */
    protected float xTarget;

    public EnemyBlock(Bitmap bitmap, int xPos, int yPos, int windowWidth, int windowHeight) {
        super(bitmap, xPos, yPos, windowWidth, windowHeight);
        this.score = 0;
        this.xSpeed = 12;
    }

    /**
     * הפעולה האחראית על תזוזת הבלוקים.
     */
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
