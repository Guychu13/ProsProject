package com.example.pros.db;

public class MultiPlayerGameDao {

    public int p1BitmapXPos, p2BitmapXPos;
    public String  p1PlayerName, p2PlayerName;
    public int p1SkinImageID, p2SkinImageID;
    //אם צריך לשמור כאן גם את התוצאה של המשחק בלייב או לא, לא יודע אם יש בזה צורך

    public MultiPlayerGameDao() {
        p2PlayerName = null;
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
        Repository.getInstance().saveUsernameGameDao(gameCode, p1PlayerName, 1);
    }

    public String getP2PlayerName() {
        return p2PlayerName;
    }

    public void setP2PlayerName(String gameCode, String p2PlayerName) {
        this.p2PlayerName = p2PlayerName;
        Repository.getInstance().saveUsernameGameDao(gameCode, p2PlayerName, 2);
    }

    public int getP1SkinImageID() {
        return p1SkinImageID;
    }

    public void setP1SkinImageID(String gameCode, int p1SkinImageID) {
        this.p1SkinImageID = p1SkinImageID;
        Repository.getInstance().saveSkinImageIdGameDao(gameCode, p1SkinImageID, 1);
    }

    public int getP2SkinImageID() {
        return p2SkinImageID;
    }

    public void setP2SkinImageID(String gameCode, int p2SkinImageID) {
        this.p2SkinImageID = p2SkinImageID;
        Repository.getInstance().saveSkinImageIdGameDao(gameCode, p2SkinImageID, 2);
    }
}
