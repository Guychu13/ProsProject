package com.example.pros;

public class User {

    private String userName;
    private int numOfWins;
    private int numOfSkins;
    private int chosenSkinImageId;
    private boolean musicOn;
    private String photoImageURL;

    public User() {

    }

    public User(String userName, int numOfWins, int numOfSkins, int chosenSkinImageId, boolean musicOn) {
        this.userName = userName;
        this.numOfWins = numOfWins;
        this.numOfSkins = numOfSkins;
        this.chosenSkinImageId = chosenSkinImageId;
        this.musicOn = musicOn;
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

    public String getPhotoImageURL() {
        return photoImageURL;
    }

    public boolean isMusicOn() {
        return musicOn;
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
}
