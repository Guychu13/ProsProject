package com.example.pros.db;

import com.example.pros.model.Skin;

import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private String userName;
    private int numOfWins;
    private ArrayList<Skin> allSkins;
    private int chosenSkinImageId;
    private boolean musicOn;
    private String photoImageURL;
    private String firebaseUserId;

    public UserDao() {
    }

    public String getUserName() {
        return userName;
    }

    public int getNumOfWins() {
        return numOfWins;
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

    public ArrayList<Skin> getAllSkins() {
        return allSkins;
    }

    public void setAllSkins(ArrayList<Skin> allSkins) {
        this.allSkins = allSkins;
    }
}
