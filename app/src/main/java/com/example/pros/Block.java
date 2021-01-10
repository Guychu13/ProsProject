package com.example.pros;

import android.graphics.Bitmap;
import android.graphics.Color;

public abstract class Block extends GameObject {

    public float xTarget;

    public Block(Bitmap bitmap, int xPos, int yPos, int windowWidth, int windowHeight) {
        super(bitmap, xPos, yPos, windowWidth, windowHeight);
        xTarget = xPos;
    }

    public boolean checkCollision(GameObject other) {//לנסות להחזיר כאן מערך שמחזיר את האיקס וואי של מיקום הפגיעה ואז לעשות במהלך המשחק בדיקה אצל הבלוק אם הכדור פגע בצד הימני או השמאלי שלו ואז שהכדור יזוז בהתאם
        int left = Math.max(xPos, other.xPos);
        int right = Math.min(xPos + bitmap.getWidth(), other.xPos + other.bitmap.getWidth());
        int top = Math.max(yPos, other.yPos);
        int bottom = Math.min(yPos + bitmap.getHeight(), other.yPos + other.bitmap.getHeight());
        for (int row = left; row < right; row++) {
            for (int col = top; col < bottom; col++) {
                if (bitmap.getPixel(row - xPos, col - yPos) != Color.TRANSPARENT &&
                        other.bitmap.getPixel(row - other.xPos, col - other.yPos) != Color.TRANSPARENT) {
                    return true;
                }
            }
        }
        return false;
    }

    public int[] getCollisionLocation(GameObject other) {
        int left = Math.max(xPos, other.xPos);
        int right = Math.min(xPos + bitmap.getWidth(), other.xPos + other.bitmap.getWidth());
        int top = Math.max(yPos, other.yPos);
        int bottom = Math.min(yPos + bitmap.getHeight(), other.yPos + other.bitmap.getHeight());
        for (int row = left; row < right; row++) {
            for (int col = top; col < bottom; col++) {
                if (bitmap.getPixel(row - xPos, col - yPos) != Color.TRANSPARENT &&
                        other.bitmap.getPixel(row - other.xPos, col - other.yPos) != Color.TRANSPARENT) {

                    return new int[]{row, col};
                }
            }
        }

        return new int[] {0, 0};
    }

    public void goToTarget(float x, float y) {
        xTarget = x;
    }

    public abstract void move();
}
