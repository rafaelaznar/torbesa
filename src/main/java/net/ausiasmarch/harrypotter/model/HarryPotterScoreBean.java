package net.ausiasmarch.harrypotter.model;

import java.time.LocalDateTime;

public class HarryPotterScoreBean {

    private Integer id;
    private Integer userId;
    private int score;
    private int tries;
    private LocalDateTime timestamp;
    private String username;

    public HarryPotterScoreBean() {}

    public HarryPotterScoreBean(Integer id, Integer userId, int score, int tries, LocalDateTime timestamp, String username) {
        this.id = id;
        this.userId = userId;
        this.score = score;
        this.tries = tries;
        this.timestamp = timestamp;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
