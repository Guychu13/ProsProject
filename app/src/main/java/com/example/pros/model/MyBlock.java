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
        int left = Math.max(xPos, other.getXPos());
        int right = Math.min(xPos + bitmap.getWidth(), other.getXPos() + other.getBitmap().getWidth());
        int top = Math.max(yPos, other.getYPos());
        int bottom = Math.min(yPos + bitmap.getHeight(), other.getYPos() + other.getBitmap().getHeight());
        for (int row = left; row < right; row++) {
            for (int col = top; col < bottom; col++) {
                if (bitmap.getPixel(row - xPos, col - yPos) != Color.TRANSPARENT &&
                        other.getBitmap().getPixel(row - other.getXPos(), col - other.getYPos()) != Color.TRANSPARENT) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getCollisionXLocation(GameObject other) {
        int left = Math.max(xPos, other.getXPos());
        int right = Math.min(xPos + bitmap.getWidth(), other.getXPos() + other.getBitmap().getWidth());
        int top = Math.max(yPos, other.getYPos());
        int bottom = Math.min(yPos + bitmap.getHeight(), other.getYPos() + other.getBitmap().getHeight());
        for (int row = left; row < right; row++) {
            for (int col = top; col < bottom; col++) {
                if (bitmap.getPixel(row - xPos, col - yPos) != Color.TRANSPARENT &&
                        other.getBitmap().getPixel(row - other.getXPos(), col - other.getYPos()) != Color.TRANSPARENT) {
                    return row;
                }
            }
        }
        return 0;
    }

    public String getSideCollisionWithBall(int xCollision) {

        if (xPos <= xCollision && xCollision < (xPos + bitmap.getWidth()) / 2) {
            return "left";
        } else if ((xPos + bitmap.getWidth()) / 2 <= xCollision && xCollision <= xPos + bitmap.getWidth()) {
            return "right";
        } else {
            return "none";
        }
    }

    public void goToTarget(float x, float y) {
        xTarget = x;
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
