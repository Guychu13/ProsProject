package com.example.pros.db;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.pros.model.User;
import com.example.pros.utils.Observer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
//    private Map<String, UserDao> users = new HashMap<>();
    private UserDao user;

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

//    public Map<String, UserDao> getUsers() {
//        return users;
//    }

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
//        DatabaseReference myRef = database.getReference("users/"+ FirebaseAuth.getInstance().getUid() + "/chosenSkinImageId");
        myRef.setValue(newChosenSkinImageID);
//        instance = new Repository();//////////////////שמתי את זה כדי שהוא יחזור לפיירבייס וישים פה בתכונה של היוזר את היוזר החדש
//        updateUserCodeDetails();
//        notifyObservers();
    }

//    public void updateUserCodeDetails(){
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("Pros").child("users").child(FirebaseAuth.getInstance().getUid());
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                user = snapshot.getValue(UserDao.class);
//                notifyObservers();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
}
