package com.example.pros.model;

import com.example.pros.R;
import com.example.pros.db.Repository;
import com.example.pros.db.UserDao;
import com.example.pros.screens.MainScreenPresenter;
import com.example.pros.utils.Observer;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Map;

public class User implements Observer{

    private String userName;
    private int numOfWins;
    private int numOfSkins;
    private int chosenSkinImageId;
    private boolean musicOn;
    private String photoImageURL;
    private String firebaseUserId;
    private static User instance = null;
    private ArrayList<Observer> observers = new ArrayList<>();

    private User() {
        Repository.getInstance().register(this);
    }

    public static User getInstance(){
        if(instance == null){
            instance = new User();
        }
        return instance;
    }

//    public User(String userName, int numOfWins, int numOfSkins, int chosenSkinImageId, boolean musicOn) {
//        this.userName = userName;
//        this.numOfWins = numOfWins;
//        this.numOfSkins = numOfSkins;
//        this.chosenSkinImageId = chosenSkinImageId;
//        this.musicOn = musicOn;
//    }

    public String getUserName() {
        return userName;
    }

    public int getNumOfWins() {
        return numOfWins;
    }

    public int getNumOfSkins() {
        return numOfSkins;
    }

    public int getChosenSkinImageId() {
        return chosenSkinImageId;
    }

    public boolean isMusicOn() {
        return musicOn;
    }

    public String getPhotoImageURL() {
        return photoImageURL;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setNumOfWins(int numOfWins) {
        this.numOfWins = numOfWins;
    }

    public void setNumOfSkins(int numOfSkins) {
        this.numOfSkins = numOfSkins;
    }

    public void setChosenSkinImageId(int chosenSkinImageId) {
        this.chosenSkinImageId = chosenSkinImageId;
    }

    public void setMusicOn(boolean musicOn) {
        this.musicOn = musicOn;
    }

    public void setPhotoImageURL(String photoImageURL) {
        this.photoImageURL = photoImageURL;
    }

    public String getFirebaseUserId() {
        return firebaseUserId;
    }

    public void setFirebaseUserId(String firebaseUserId) {
        this.firebaseUserId = firebaseUserId;
    }

    public void register(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void update() {

        UserDao user = Repository.getInstance().getUser();
        this.userName = user.getUserName();
        this.chosenSkinImageId = user.getChosenSkinImageId();
        this.musicOn = user.isMusicOn();
        this.numOfSkins = user.getNumOfSkins();
        this.numOfWins = user.getNumOfWins();
        this.firebaseUserId = user.getFirebaseUserId();
        notifyObservers();
    }

    private void notifyObservers(){
        for(int i = 0; i < observers.size(); i++){
            observers.get(i).update();
        }
    }

    public void createNewUser(String userName){
        UserDao newUser = new UserDao();
        newUser.setUserName(userName);
        newUser.setChosenSkinImageId(R.drawable.skin_basic);
        newUser.setMusicOn(true);
        newUser.setNumOfSkins(1);
        newUser.setNumOfWins(0);
        newUser.setFirebaseUserId(FirebaseAuth.getInstance().getUid());
        Repository.getInstance().saveUser(newUser);
    }

}
