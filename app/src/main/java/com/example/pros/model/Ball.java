package com.example.pros.model;

import android.graphics.Bitmap;
import android.graphics.Color;

public class Ball extends GameObject {

    private int lastPlayerTouched;
    private int xSpeed, ySpeed;

    public Ball(Bitmap bitmap, int xPos, int yPos, int windowWidth, int windowHeight) {
        super(bitmap, xPos, yPos, windowWidth, windowHeight);
        lastPlayerTouched = 0;
        xSpeed = 0;
        ySpeed = 0;
    }

    public int getLastPlayerTouched() {
        return lastPlayerTouched;
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }

    public void setLastPlayerTouched(int lastPlayerTouched) {
        this.lastPlayerTouched = lastPlayerTouched;
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    //    @Override
    public void move() {

        checkIfCollisionSideBoarders(windowWidth, windowHeight);
        for (int i = 0; i < Math.abs(xSpeed); i++) {//כדי שאם יש לחיצה ארוכה הוא יזוז בצעד צעד עד שהוא לא יכול יותר. אם זה זז 20 20 אז הוא לא יסכים לזוז אם הוא נגיד רחוק 18 מהמסגרת
            if (xSpeed > 0 && xPos + bitmap.getWidth() < windowWidth) {
                xPos += 1;
            } else if (xSpeed < 0 && xPos > 0) {
                xPos -= 1;
            }
        }
        for (int i = 0; i < Math.abs(ySpeed); i++) {
            if (ySpeed > 0 && yPos + bitmap.getHeight() < windowHeight) {
                yPos += 1;
            } else if (ySpeed < 0 && yPos > 0) {
                yPos -= 1;
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

    public void checkIfCollisionSideBoarders(int boardWidth, int boardHeight) {

        if (xPos + bitmap.getWidth() >= boardWidth && xSpeed > 0) {
            xSpeed *= -1;
        }
        if (xPos <= 0 && xSpeed < 0) {
            xSpeed *= -1;
        }
//        if(yPos + bitmap.getHeight() >= boardHeight || yPos <= 0){
//            ySpeed *= -1;
//        }
    }

    public int checkWhoScored() {
        if (yPos + bitmap.getHeight() >= windowHeight) {
            return 2;
        }
        if (yPos <= 0) {
            return 1;
        }
        return 0;
    }
}
