package com.example.pros.model;

import android.content.Intent;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.example.pros.db.Repository;
import com.example.pros.utils.Observer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EnemyMultiplayerBlock extends EnemyBlock {

    boolean isP1;
    public EnemyMultiplayerBlock(Bitmap bitmap, int xPos, int yPos, int windowWidth, int windowHeight) {
        super(bitmap, xPos, yPos, windowWidth, windowHeight);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef;
        if(isP1){
            myRef = database.getReference("Pros").child("gameCodes").child(MultiPlayerGame.getInstance().getGameCode()).child("p1BitmapXPos");
        }
        else{
            myRef = database.getReference("Pros").child("gameCodes").child(MultiPlayerGame.getInstance().getGameCode()).child("p2BitmapXPos");
        }
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                setXPos(snapshot.getValue(Float.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public boolean isP1() {
        return isP1;
    }

    public void setP1(boolean p1) {
        isP1 = p1;
    }

    @Override
    public void move() {//המיקום שלו מתעדכן אוטמטית מהפעולה הבונה כבר אז אין צורך בפעולה של מוב כאן

    }


    //    public void setNewXTargetFromFirebase() {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("Pros").child("gameCodes").child(MultiPlayerGame.getInstance().getGameCode()).child("p2BitmapXPos");
//        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                setXPos(snapshot.getValue(Float.class));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }


//    @Override
//    public void setXPos(float xPos) {//לא צריך כתיבה של האויב, כל אחד כותב את שלו וקורא את של האוייב
//        this.xPos = xPos;
////        FirebaseDatabase database = FirebaseDatabase.getInstance();
////        DatabaseReference myRef;
////        if(isP1){
////            myRef = database.getReference("Pros").child("gameCodes").child(MultiPlayerGame.getInstance().getGameCode()).child("p1BitmapXPos");
////        }
////        else{
////            myRef = database.getReference("Pros").child("gameCodes").child(MultiPlayerGame.getInstance().getGameCode()).child("p2BitmapXPos");
////        }
////        myRef.setValue(this.xPos);
//    }


//    @Override
//    public void update() {
//        this.xPos = Repository.getInstance().getEnemyXPos();
//    }
}
