package com.example.choreapplication.fragments;

public class Statistics {
    private String user;
    private int totalScore;
    private int totalMinutes;

    public Statistics() {}

    public Statistics(String user, int totalScore, int totalMinutes) {
        this.user = user;
        this.totalScore = totalScore;
        this.totalMinutes = totalMinutes;
    }

    public String getUser() {
        return user;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public String getTotalTime() {
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;

        if(minutes == 0) {
            return hours + "h";
        }

        if(hours == 0) {
            return minutes + " min";
        }

        return hours + "h " + minutes + " min";
    }
}

