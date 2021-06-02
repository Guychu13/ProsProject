package com.example.pros.db;

import com.example.pros.model.Skin;

import java.util.ArrayList;
import java.util.List;

/**
 * מחלקה זו היא בעצם Data Object, וטיפוס זה משמש לשמירה בבסיס הנתונים.
 * הנתונים נשמרים בבסיס הנתונים כטיפוס הזה, אך הוא אינו הטיפוס בו מתבצע השימוש בקוד.
 * מתבצעת בקוד המרה מהטיפוס הזה אל טיפוס זהה לחלוטין אליו בתכונותיו, ובו מתבצע השימוש בקוד.
 */
public class UserDao {

    /**
     * שם המשתמש.
     */
    private String userName;
    /**
     * מספר הנצחונות של השחקן.
     */
    private int numOfWins;
    /**
     * רשימה המכילה את כל הסקינים במשחק והאם המשתמש פתח אותם, המכילה עצמים מטיפוס Skin.
     */
    private ArrayList<Skin> allSkins;
    /**
     * 	המזהה בקבצי האפליקציה של תמונת הסקין הנבחר של השחקן.
     */
    private int chosenSkinImageId;
    private boolean musicOn;
    private String photoImageURL;
    /**
     * ה-id של המשתמש בבסיס הנתונים.
     */
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
