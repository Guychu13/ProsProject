package com.example.pros.model;

import com.example.pros.R;
import com.example.pros.db.MultiPlayerGameDao;
import com.example.pros.db.Repository;
import com.example.pros.db.UserDao;
import com.example.pros.utils.Observer;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MultiPlayerGame implements Observer {

    public float p1BitmapXPos, p2BitmapXPos;
    public String  p1PlayerName, p2PlayerName;
    public int p1SkinImageID, p2SkinImageID;
    public String gameCode;
    private ArrayList<Observer> observers = new ArrayList<>();
    public boolean gameStarted;

    private static MultiPlayerGame instance = null;

    private MultiPlayerGame(){
        Repository.getInstance().register(this);
    }

    public static MultiPlayerGame getInstance(){
        if(instance == null){
            instance = new MultiPlayerGame();
        }
        return instance;
    }

    public float getP1BitmapXPos() {
        return p1BitmapXPos;
    }

    public void setP1BitmapXPos(String gameCode, float p1BitmapXPos) {
        this.p1BitmapXPos = p1BitmapXPos;
        Repository.getInstance().saveP1BitmapXPos(gameCode, p1BitmapXPos);
    }

    public float getP2BitmapXPos() {
        return p2BitmapXPos;
    }

    public void setP2BitmapXPos(String gameCode, float p2BitmapXPos) {
        this.p2BitmapXPos = p2BitmapXPos;
        Repository.getInstance().saveP2BitmapXPos(gameCode, p2BitmapXPos);
    }

    public String getP1PlayerName() {
        return p1PlayerName;
    }

    public void setP1PlayerName(String p1PlayerName) {
        this.p1PlayerName = p1PlayerName;
    }

    public String getP2PlayerName() {
        return p2PlayerName;
    }

    public void setP2PlayerName(String gameCode , String p2PlayerName) {
        this.p2PlayerName = p2PlayerName;
        Repository.getInstance().saveP2PlayerName(gameCode, p2PlayerName);
    }

    public int getP1SkinImageID() {
        return p1SkinImageID;
    }

    public void setP1SkinImageID(int p1SkinImageID) {
        this.p1SkinImageID = p1SkinImageID;
    }

    public int getP2SkinImageID() {
        return p2SkinImageID;
    }

    public void setP2SkinImageID(String gameCode, int p2SkinImageID) {
        this.p2SkinImageID = p2SkinImageID;
        Repository.getInstance().saveP2SkinImageId(gameCode, p2SkinImageID);
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(String gameCode ,boolean gameStarted) {
        this.gameStarted = gameStarted;
        Repository.getInstance().saveGameStarted(gameCode, gameStarted);
    }

    @Override
    public void update() {
        MultiPlayerGameDao multiPlayerGameDao = Repository.getInstance().getMultiPlayerGameDao();
        this.gameCode = multiPlayerGameDao.getGameCode();
        this.p1BitmapXPos = multiPlayerGameDao.getP1BitmapXPos();
        this.p1PlayerName = multiPlayerGameDao.getP1PlayerName();
        this.p1SkinImageID = multiPlayerGameDao.getP1SkinImageID();
        this.p2BitmapXPos = multiPlayerGameDao.getP2BitmapXPos();
        this.p2PlayerName = multiPlayerGameDao.getP2PlayerName();
        this.p2SkinImageID = multiPlayerGameDao.getP2SkinImageID();
        this.gameStarted = multiPlayerGameDao.isGameStarted();
        notifyObservers();
    }

    private void notifyObservers(){
        for(int i = 0; i < observers.size(); i++){
            observers.get(i).update();
        }
    }

    public void createNewMultiPlayerGame(String gameCode, String p1Name, int p1ChosenSkinImageId){
        MultiPlayerGameDao newMultiPlayerGameDao = new MultiPlayerGameDao();
        newMultiPlayerGameDao.setGameCode(gameCode);
        newMultiPlayerGameDao.setP1PlayerName(gameCode, p1Name);
        newMultiPlayerGameDao.setP2PlayerName(gameCode, "");
        newMultiPlayerGameDao.setP1SkinImageID(gameCode, p1ChosenSkinImageId);
        newMultiPlayerGameDao.setP1BitmapXPos(0);
        newMultiPlayerGameDao.setP2BitmapXPos(0);
        newMultiPlayerGameDao.setP2SkinImageID(gameCode, 0);
        newMultiPlayerGameDao.setGameStarted(false);
        Repository.getInstance().saveMultiPlayerGame(newMultiPlayerGameDao);
    }

    public void register(Observer observer) {
        observers.add(observer);
    }
}
