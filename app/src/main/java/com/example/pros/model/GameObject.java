package com.example.pros.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 	מחלקה זו היא מחלקה אבסטרקטית המורישה לכל האובייקטים במשחק.
 */
public abstract class GameObject {
    /**
     * 	תמונת הסקין.
     */
    protected Bitmap bitmap;
    /**
     * 	שיעורי ה-X וה-Y של האובייקט.
     */
    protected float xPos, yPos;
    /**
     * האורך והרוחב של מרחב המשחק.
     */
    protected int windowWidth, windowHeight;

    public GameObject(Bitmap bitmap, float xPos, float yPos, int windowWidth, int windowHeight) {
        this.bitmap = bitmap;
        this.xPos = xPos;
        this.yPos = yPos;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

    /**
     * הפעולה מציירת את האובייקט על לוח המשחק.
     * @param canvas
     */
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        if (canvas != null) {
            canvas.drawBitmap(bitmap, getXPos(), getYPos(), paint);
        }
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public float getXPos() {
        return xPos;
    }

    public void setXPos(float xPos) {
        this.xPos = xPos;
    }

    public void setYPos(float yPos) {
        this.yPos = yPos;
    }

    public float getYPos() {
        return yPos;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }
}
