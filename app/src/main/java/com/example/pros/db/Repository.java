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

public class Repository {

    private static Repository instance = null;
    private ArrayList<Observer> observers = new ArrayList<>();

    private UserDao user;
    private MultiPlayerGameDao multiPlayerGame;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

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

    public void register(Observer observer) {
        observers.add(observer);
    }

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

    public void saveUsernameGameDao(String gameCode, String name, int pNum){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        if(pNum == 1){
            DatabaseReference myRef = database.getReference("Pros").child("gameCodes").child(gameCode).child("p1PlayerName");
            myRef.setValue(name);
        }
        if(pNum == 2){
            DatabaseReference myRef = database.getReference("Pros").child("gameCodes").child(gameCode).child("p2PlayerName");
            myRef.setValue(name);
        }
    }

    public void saveSkinImageIdGameDao(String gameCode, int skinImageId, int pNum){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        if(pNum == 1){
            DatabaseReference myRef = database.getReference("Pros").child("gameCodes").child(gameCode).child("p1SkinImageID");
            myRef.setValue(skinImageId);
        }
        if(pNum == 2){
            DatabaseReference myRef = database.getReference("Pros").child("gameCodes").child(gameCode).child("p2SkinImageID");
            myRef.setValue(skinImageId);
        }
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

    public void saveP1BitmapXPos(String gameCode, int p1BitmapXPos) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pros").child("gameCodes").child(gameCode).child("p1BitmapXPos");
        myRef.setValue(p1BitmapXPos);
    }

    public void saveP2BitmapXPos(String gameCode, int p2BitmapXPos) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Pros").child("gameCodes").child(gameCode).child("p2BitmapXPos");
        myRef.setValue(p2BitmapXPos);
    }

    public MultiPlayerGameDao getMultiPlayerGameDao(){
        return multiPlayerGame;
    }
}