package com.example.choreapplication;

import java.util.Date;

public class DocumentedChore {
    private String chore;
    private String familyCode;
    private int score;
    private int time;
    private String user;
    private Date timestamp;
    public DocumentedChore(){}

    public DocumentedChore(String chore, String familyCode, int time, int score, String user, Date timestamp) {
        this.chore = chore;
        this.familyCode = familyCode;
        this.score = score;
        this.time = time;
        this.user = user;
        this.timestamp = timestamp;
    }

    public String getChore() {
        return chore;
    }

    public void setChore(String chore) {
        this.chore = chore;
    }

    public String getFamilyCode() {
        return familyCode;
    }

    public void setFamilyCode(String familyCode) {
        this.familyCode = familyCode;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
