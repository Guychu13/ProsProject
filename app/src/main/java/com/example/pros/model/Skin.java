package com.example.pros.model;

/**
 * 	מחלקה זו מייצגת סקין במשחק.
 */
public class Skin {
    /**
     * 	שמו של הסקין.
     */
    private String name;
    /**
     * 	המשימה הדורשת השלמה על מנת לפתוח את הסקין.
     */
    private String mission;
    /**
     * 	האם הסקין הוא הסקין הנבחר באותו הרגע.
     */
    private Boolean selected;
    /**
     * 	האם המשתמש פתח את הסקין לשימוש או לא.
     */
    private Boolean unlocked;
    /**
     * 	מזהה התמונה של הסקין בקבצי האפליקציה.
     */
    private int image;
    /**
     * 	מספר הנצחונות כאשר הסקין היה הסקין הנבחר.
     */
    private int wins;

    public Skin(){

    }

    public Skin(String name, String mission, Boolean selected, Boolean unlocked, int image, int wins) {
        this.name = name;
        this.mission = mission;
        this.selected = selected;
        this.unlocked = unlocked;
        this.image = image;
        this.wins = wins;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public void setUnlocked(Boolean unlocked) {
        this.unlocked = unlocked;
    }

    public void setImage(int image) {
        this.image = image;
    }


    public void setWins(int wins) {
        this.wins = wins;
    }

    public String getName() {
        return name;
    }

    public String getMission() {
        return mission;
    }

    public Boolean getSelected() {
        return selected;
    }

    public Boolean getUnlocked() {
        return unlocked;
    }

    public int getImage() {
        return image;
    }


    public int getWins() {
        return wins;
    }
}
