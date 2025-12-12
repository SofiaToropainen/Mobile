package com.example.choreapplication;

public class Chore {
    private String chore;
    private int score;
    private String familyCode;
    private String id;

    public Chore() {}

    public Chore(String chore, int score, String familyCode, String id) {
        this.chore = chore;
        this.score = score;
        this.familyCode = familyCode;
        this.id = id;
    }

    public String getChore() {
        return chore;
    }

    public int getScore() {
        return score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
