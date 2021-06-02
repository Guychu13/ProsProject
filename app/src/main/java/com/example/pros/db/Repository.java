package com.example.pros.db;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pros.model.MultiPlayerGame;
import com.example.pros.model.User;
import com.example.pros.utils.Observer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * מחלקה זו היא מהמחלקות המרכזיות ביותר באפליקצייה.
 * היא מהווה את המקום היחידי בו נוצר קשר עם בסיס הנתונים, והיא האחראית הבלעדית על האזנה אליו ועל עדכונו.
 */
public class Repository {

    /**
     * המחלקה היא Singleton לכן יש צורך בתכונה סטטית מאותו הטיפוס של המחלקה.
     */
    private static Repository instance = null;
    /**
     * רשימה המכילה את כל הקלאסים(שמממשים את המחלקה Observer) שצריכים להיות מעודכנים כאשר מתבצע שינוי במחלקה הזו.
     */
    private ArrayList<Observer> observers = new ArrayList<>();

    /**
     * הנתונים הנשמרים בבסיס הנתונים המשמשים כ- Data Object’s(Dao), מהם מתבצעת המרה לטיפוסים איתם מתבצעת העבודה בקוד.
     */
    private UserDao user;
    private MultiPlayerGameDao multiPlayerGame;

//    private FirebaseAuth mAuth;
//    private FirebaseUser currentUser;

//    private float enemyXPos;

    /**
     * הפעולה מחזירה את העצם הקיים של המחלקה, או בונה עצם חדש במקרה ואין אחד קיים כבר בקוד.
     * @return
     */
    public static Repository getInstance(){
        if(instance == null){
            instance = new Repository();
        }
        return instance;
    }

    private Repository(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pros").child("users").child(FirebaseAuth.getInstance().getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(UserDao.class);
                notifyObservers();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * פעולה זו היא פעולה שבעזרתה מחלקות אחרות המממשות את הממשק Observer יכולות להאזין לשינויים המתבצעים במחלקה הזו כאשר מופעלת הפעולה notifyObservers.
     * @param observer
     */
    public void register(Observer observer) {
        observers.add(observer);
    }

    /**
     * פעולה זו מעדכנת את כל ה-Observers הרשומים אצל המחלקה שהתבצע שינוי, מה שמפעיל במחלקות המאזינות את הפעולה update().
     */
    private void notifyObservers(){
        for(int i = 0; i < observers.size(); i++){
            observers.get(i).update();
        }
    }

    public UserDao getUser() {
        return this.user;
    }

    public void saveUser(UserDao userDao){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pros").child("users").child(FirebaseAuth.getInstance().getUid());
        myRef.setValue(userDao);
    }

    public void saveNewChosenSkinImageID(int newChosenSkinImageID){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pros").child("users").child(FirebaseAuth.getInstance().getUid()).child("chosenSkinImageId");
        myRef.setValue(newChosenSkinImageID);
    }


    public void saveMultiPlayerGame(MultiPlayerGameDao multiPlayerGameDao){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pros").child("gameCodes").child(multiPlayerGameDao.getGameCode());
        myRef.setValue(multiPlayerGameDao);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                multiPlayerGame = snapshot.getValue(MultiPlayerGameDao.class);
                notifyObservers();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateCodeMultiPlayerGame(String gameCode){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pros").child("gameCodes").child(gameCode);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                multiPlayerGame = snapshot.getValue(MultiPlayerGameDao.class);
                notifyObservers();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void saveP2PlayerName(String gameCode , String p2PlayerName){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pros").child("gameCodes").child(gameCode).child("p2PlayerName");
        myRef.setValue(p2PlayerName);
    }

    public void saveP2SkinImageId(String gameCode, int p2SkinImageID){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pros").child("gameCodes").child(gameCode).child("p2SkinImageID");
        myRef.setValue(p2SkinImageID);
    }

    public void saveP1BitmapXPos(String gameCode, float p1BitmapXPos) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pros").child("gameCodes").child(gameCode).child("p1BitmapXPos");
        myRef.setValue(p1BitmapXPos);
    }

    public void saveP2BitmapXPos(String gameCode, float p2BitmapXPos) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pros").child("gameCodes").child(gameCode).child("p2BitmapXPos");
        myRef.setValue(p2BitmapXPos);
    }

    public MultiPlayerGameDao getMultiPlayerGameDao(){
        return multiPlayerGame;
    }

    public void setMultiPlayerGame(MultiPlayerGameDao multiPlayerGame) {
        this.multiPlayerGame = multiPlayerGame;
    }

    public void saveGameStarted(String gameCode, boolean gameStarted) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pros").child("gameCodes").child(gameCode).child("gameStarted");
        myRef.setValue(gameStarted);
    }

    public void saveBallIsMoving(String gameCode, boolean ballIsMoving) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pros").child("gameCodes").child(gameCode).child("ballIsMoving");
        myRef.setValue(ballIsMoving);
    }

    public void setListenerOnBallIsMoving(String gameCode){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pros").child("gameCodes").child(gameCode).child("ballIsMoving");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                multiPlayerGame.setBallIsMoving(snapshot.getValue(Boolean.class));
                notifyObservers();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}