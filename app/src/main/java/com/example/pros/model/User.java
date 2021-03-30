package com.example.pros.model;

import com.example.pros.R;
import com.example.pros.db.Repository;
import com.example.pros.db.UserDao;
import com.example.pros.screens.MainScreenActivity;
import com.example.pros.screens.MainScreenPresenter;
import com.example.pros.utils.Observer;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class User implements Observer{

    private String userName;
    private int numOfWins;
    private ArrayList<Skin> allSkins;
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

    public ArrayList<Skin> getAllSkins() {
        return allSkins;
    }

    public void setAllSkins(ArrayList<Skin> allSkins) {
        this.allSkins = allSkins;
    }

    public ArrayList<Skin> returnNewAllSkinsArrayList(){
        Skin basic = new Skin("Basic", "Your Default Skin.", true, true, R.drawable.skin_basic, 0);
        Skin peace = new Skin("Peace", "Play 3 matches.", false, false, R.drawable.skin_peace, 0);
        Skin pizza = new Skin("Pizza?", "I don't know yet", false, false, R.drawable.skin_pizza, 0);
        Skin musical = new Skin("Musical", "Win 5 matches.", false, false, R.drawable.skin_musical, 0);
        Skin hamburger = new Skin("Burger", "I don't know yet", false, false, R.drawable.skin_hamburger, 0);
        Skin smile = new Skin("Smile", "Win in Under 1 minute \nand 30 seconds.", false, false, R.drawable.skin_smile, 0);
        Skin ocean = new Skin("Ocean", "I don't know yet", false, false, R.drawable.skin_ocean, 0);
        Skin telescope = new Skin("Telescope", "Win Without Taking Any Goals.", false, false, R.drawable.skin_telescope, 0);
        Skin playin = new Skin("Playin'", "Win 10 matches.", false, false, R.drawable.skin_playin, 0);
        Skin crystal = new Skin("Crystal", "I don't know yet", false, false, R.drawable.skin_crystal, 0);
        Skin master = new Skin("Master", "Win in Under 30 seconds.", false, false, R.drawable.skin_master, 0);

        ArrayList<Skin> allSkins = new ArrayList<>();
        allSkins.add(basic);
        allSkins.add(peace);
        allSkins.add(pizza);
        allSkins.add(musical);
        allSkins.add(hamburger);
        allSkins.add(smile);
        allSkins.add(ocean);
        allSkins.add(telescope);
        allSkins.add(playin);
        allSkins.add(crystal);
        allSkins.add(master);

        return allSkins;
    }

    public void setChosenSkinImageId(int chosenSkinImageId) {
        this.chosenSkinImageId = chosenSkinImageId;
        Repository.getInstance().saveNewChosenSkinImageID(this.chosenSkinImageId);
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
        this.allSkins= user.getAllSkins();
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
        newUser.setAllSkins(returnNewAllSkinsArrayList());
        newUser.setNumOfWins(0);
        newUser.setFirebaseUserId(FirebaseAuth.getInstance().getUid());
        Repository.getInstance().saveUser(newUser);
    }

}
