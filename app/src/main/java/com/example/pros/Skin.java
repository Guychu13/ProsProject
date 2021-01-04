package com.example.pros;

public class Skin {

    private String name;
    private String mission;
    private Boolean selected;
    private Boolean unlocked;
    private int image;
    private int wins;

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
