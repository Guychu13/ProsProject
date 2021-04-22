package com.example.pros.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.NonNull;

import com.example.pros.db.Repository;
import com.example.pros.db.UserDao;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyBlock extends GameObject

{

    private float xTarget;
    private int score;
    private int xSpeed;
    private boolean isMultiplayer;
    private boolean isP1;

    public MyBlock(Bitmap bitmap, int xPos, int yPos, int windowWidth, int windowHeight, boolean isMultiplayer) {
        super(bitmap, xPos, yPos, windowWidth, windowHeight);
        this.score = 0;
        xSpeed = 15;
        this.isMultiplayer = isMultiplayer;
    }

    public void move() {
        if (xPos < xTarget) {
            for (int i = 0; i < Math.abs(xSpeed); i++) {//כדי שאם יש לחיצה ארוכה הוא יזוז בצעד צעד עד שהוא לא יכול יותר. אם זה זז 20 20 אז הוא לא יסכים לזוז אם הוא נגיד רחוק 18 מהמסגרת
                if (xPos + bitmap.getWidth() < windowWidth) {
                    xPos += 1;
                    if(isMultiplayer){
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef;
                        if(isP1){
                            myRef = database.getReference("Pros").child("gameCodes").child(MultiPlayerGame.getInstance().getGameCode()).child("p1BitmapXPos");
                        }
                        else{
                            myRef = database.getReference("Pros").child("gameCodes").child(MultiPlayerGame.getInstance().getGameCode()).child("p2BitmapXPos");
                        }
                        myRef.setValue(xPos);
                    }
                }
            }
        }
        if (xPos > xTarget) {
            for (int i = 0; i < Math.abs(xSpeed); i++) {//כדי שאם יש לחיצה ארוכה הוא יזוז בצעד צעד עד שהוא לא יכול יותר. אם זה זז 20 20 אז הוא לא יסכים לזוז אם הוא נגיד רחוק 18 מהמסגרת
                if (xPos > 0) {
                    xPos -= 1;
                    if(isMultiplayer){
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef;
                        if(isP1){
                            myRef = database.getReference("Pros").child("gameCodes").child(MultiPlayerGame.getInstance().getGameCode()).child("p1BitmapXPos");
                        }
                        else{
                            myRef = database.getReference("Pros").child("gameCodes").child(MultiPlayerGame.getInstance().getGameCode()).child("p2BitmapXPos");
                        }
                        myRef.setValue(xPos);
                    }
                }
            }
        }
    }

    ///התיכנון שלי פה הוא כזה
    //הפעולה שפה למטה בעצם הולכת לפיירבייס ומביאה משם את המיקום X שלי
    //אחרי שהיא עושה את זה היא תתריע לאובזרבר שבעצם יהיה הקלאס הזה שמשהו השתנה
    //בפעולה אפדייט כאן אני אפעיל את הפעולת ציור שלי וכך הציור יתבצע רק כאשר אני קיבלתי את הנתונים שלי מהפיירבייס
    //הפעולה הרגילה של הדראו ריקה כי אני רוצה שכלום יצוייר עד שאני אקבל את הנתונים הדרושים

//    public float getXPosFromFirebase(){
//        if(isMultiplayer) {
//            FirebaseDatabase database = FirebaseDatabase.getInstance();
//            DatabaseReference myRef;
//            if (isP1) {
//                myRef = database.getReference("Pros").child("gameCodes").child(MultiPlayerGame.getInstance().getGameCode()).child("p1BitmapXPos");
//            } else {
//                myRef = database.getReference("Pros").child("gameCodes").child(MultiPlayerGame.getInstance().getGameCode()).child("p2BitmapXPos");
//            }
//            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    setXPos(snapshot.getValue(Float.class));
//                    notifyObservers();
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }
//    }

//    @Override
//    public void draw(Canvas canvas) {//לא רוצה שהדראו הזה יפעל, כי אני רוצה להפעיל את הדראו אני. מתי שאקבל את הנתונים מהפיירבייס, ולא מתי שכולם מופעלים
//        this.canvas = canvas;
//        getXPosFromFirebase();
//    }

//    public void realDraw(Canvas canvas) {
//        Paint paint = new Paint();
//        if (canvas != null) {
//            canvas.drawBitmap(bitmap, getXPos(), getYPos(), paint);
//        }
//    }

//    @Override
//    public void update() {
//        realDraw(canvas);
//    }

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

    public boolean isMultiplayer() {
        return isMultiplayer;
    }

    public void setMultiplayer(boolean multiplayer) {
        isMultiplayer = multiplayer;
    }

    public boolean isP1() {
        return isP1;
    }

    public void setP1(boolean p1) {
        isP1 = p1;
    }
}
