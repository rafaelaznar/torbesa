package net.ausiasmarch.math.model;

import java.util.Date;

public class MathScoreBean {
    private String username; // nuevo atributo
    private int score;
    private int tries;
    private Date timestamp;
    private int userId;

    public MathScoreBean() {
        this.score = 0;
        this.tries = 0;
        this.timestamp = new Date();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTries() {
        return tries;
    }

    public void setTries(int tries) {
        this.tries = tries;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
