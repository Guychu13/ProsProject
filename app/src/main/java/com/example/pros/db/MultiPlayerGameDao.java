package com.example.pros.db;

import com.example.pros.screens.FriendlyGameWaitingRoomActivity;

/**
 *מחלקה זו היא בעצם Data Object, וטיפוס זה משמש לשמירה בבסיס הנתונים.
 *  הנתונים נשמרים בבסיס הנתונים כטיפוס הזה, אך הוא אינו הטיפוס בו מתבצע השימוש בקוד.
 *  מתבצעת בקוד המרה מהטיפוס הזה אל טיפוס זהה לחלוטין אליו בתכונותיו, ובו מתבצע השימוש בקוד.
 */
public class MultiPlayerGameDao {

    /**
     * מיקום הבלוקים של השחקנים על ציר ה-X במסך.
     */
    public int p1BitmapXPos, p2BitmapXPos;
    /**
     * שמות השחקנים המשתתפים במשחק.
     */
    public String  p1PlayerName, p2PlayerName;
    /**
     * המזהה בקבצי האפליקציה של תמונת הסקין הנוכחי של כל אחד מהשחקנים.
     */
    public int p1SkinImageID, p2SkinImageID;
    /**
     *קוד המשחק.
     */
    public String gameCode;
    /**
     * 	משתנה בוליאני המסמל אם המשחק התחיל.
     */
    public boolean gameStarted;
    public boolean ballIsMoving;

    public MultiPlayerGameDao() {

    }

    public int getP1BitmapXPos() {
        return p1BitmapXPos;
    }

    public void setP1BitmapXPos(int p1BitmapXPos) {
        this.p1BitmapXPos = p1BitmapXPos;
    }

    public int getP2BitmapXPos() {
        return p2BitmapXPos;
    }

    public void setP2BitmapXPos(int p2BitmapXPos) {
        this.p2BitmapXPos = p2BitmapXPos;
    }

    public String getP1PlayerName() {
        return p1PlayerName;
    }

    public void setP1PlayerName(String gameCode, String p1PlayerName) {
        this.p1PlayerName = p1PlayerName;
    }

    public String getP2PlayerName() {
        return p2PlayerName;
    }

    public void setP2PlayerName(String gameCode, String p2PlayerName) {
        this.p2PlayerName = p2PlayerName;
    }

    public int getP1SkinImageID() {
        return p1SkinImageID;
    }

    public void setP1SkinImageID(String gameCode, int p1SkinImageID) {
        this.p1SkinImageID = p1SkinImageID;
    }

    public int getP2SkinImageID() {
        return p2SkinImageID;
    }

    public void setP2SkinImageID(String gameCode, int p2SkinImageID) {
        this.p2SkinImageID = p2SkinImageID;
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

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public boolean isBallIsMoving() {
        return ballIsMoving;
    }

    public void setBallIsMoving(boolean ballIsMoving) {
        this.ballIsMoving = ballIsMoving;
    }
}
