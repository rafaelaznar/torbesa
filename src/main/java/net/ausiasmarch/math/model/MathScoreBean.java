package net.ausiasmarch.math.model;

import java.sql.Timestamp;

public class MathScoreBean {

    private int id;
    private int userId;
    private String username;
    private int score;
    private int tries;
    private Timestamp timestamp;

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public int getTries() { return tries; }
    public void setTries(int tries) { this.tries = tries; }

    public Timestamp getTimestamp() { return timestamp; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }
}
