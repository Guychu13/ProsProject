package com.example.pros;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class GameObject {

    protected Bitmap bitmap;
    protected int xPos, yPos, windowWidth, windowHeight;

    public GameObject(Bitmap bitmap, int xPos, int yPos, int windowWidth, int windowHeight) {
        this.bitmap = bitmap;
        this.xPos = xPos;
        this.yPos = yPos;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        if(canvas != null){
            canvas.drawBitmap(bitmap, xPos, yPos, paint);
        }
    }

//    public abstract void move();

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }
}
