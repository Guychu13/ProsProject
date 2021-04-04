package com.example.pros.model;

import android.graphics.Bitmap;
import android.graphics.Color;

public class MyBlock extends GameObject {

    private float xTarget;
    private int score;
    private int xSpeed;

    public MyBlock(Bitmap bitmap, int xPos, int yPos, int windowWidth, int windowHeight) {
        super(bitmap, xPos, yPos, windowWidth, windowHeight);
        this.score = 0;
        xSpeed = 15;
    }

    public void move() {
        if (xPos < xTarget) {
            for (int i = 0; i < Math.abs(xSpeed); i++) {//כדי שאם יש לחיצה ארוכה הוא יזוז בצעד צעד עד שהוא לא יכול יותר. אם זה זז 20 20 אז הוא לא יסכים לזוז אם הוא נגיד רחוק 18 מהמסגרת
                if (xPos + bitmap.getWidth() < windowWidth) {
                    xPos += 1;
                }
            }
        }
        if (xPos > xTarget) {
            for (int i = 0; i < Math.abs(xSpeed); i++) {//כדי שאם יש לחיצה ארוכה הוא יזוז בצעד צעד עד שהוא לא יכול יותר. אם זה זז 20 20 אז הוא לא יסכים לזוז אם הוא נגיד רחוק 18 מהמסגרת
                if (xPos > 0) {
                    xPos -= 1;
                }
            }
        }
    }

    public boolean checkCollision(GameObject other) {
        float left = Math.max(xPos, other.getXPos());
        float right = Math.min(xPos + bitmap.getWidth(), other.getXPos() + other.getBitmap().getWidth());
        float top = Math.max(yPos, other.getYPos());
        float bottom = Math.min(yPos + bitmap.getHeight(), other.getYPos() + other.getBitmap().getHeight());
        for (float row = left; row < right; row++) {
            for (float col = top; col < bottom; col++) {
                if (bitmap.getPixel((int)(row - xPos), (int)(col - yPos)) != Color.TRANSPARENT &&
                        other.getBitmap().getPixel((int)(row - other.getXPos()), (int)(col - other.getYPos())) != Color.TRANSPARENT) {
                    return true;
                }
            }
        }
        return false;
    }

    public float getCollisionXLocation(GameObject other) {
        float left = Math.max(xPos, other.getXPos());
        float right = Math.min(xPos + bitmap.getWidth(), other.getXPos() + other.getBitmap().getWidth());
        float top = Math.max(yPos, other.getYPos());
        float bottom = Math.min(yPos + bitmap.getHeight(), other.getYPos() + other.getBitmap().getHeight());
        for (float row = left; row < right; row++) {
            for (float col = top; col < bottom; col++) {
                if (bitmap.getPixel((int)(row - xPos), (int)(col - yPos)) != Color.TRANSPARENT &&
                        other.getBitmap().getPixel((int)(row - other.getXPos()), (int)(col - other.getYPos())) != Color.TRANSPARENT) {
                    return col;
                }
            }
        }
        return 0;
    }

    public String getSideCollisionWithBall(float xCollision) {

        if (xPos <= xCollision && xCollision < (xPos + bitmap.getWidth()) / 2) {
            return "left";
        } else if ((xPos + bitmap.getWidth()) / 2 <= xCollision && xCollision <= xPos + bitmap.getWidth()) {
            return "right";
        } else {
            return "none";
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public float getXTarget() {
        return xTarget;
    }

    public void setXTarget(float xTarget) {
        this.xTarget = xTarget;
    }
}
