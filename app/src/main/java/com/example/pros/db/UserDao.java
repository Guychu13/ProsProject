package com.example.pros.db;

public class UserDao {

    private String userName;
    private int numOfWins;
    private int numOfSkins;
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
}
