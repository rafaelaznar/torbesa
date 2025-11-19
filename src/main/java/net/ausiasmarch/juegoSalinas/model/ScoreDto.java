package net.ausiasmarch.juegoSalinas.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class ScoreDto {

    private int id;
    private String username;
    private int score;
    private LocalDateTime timestamp;

    public ScoreDto() {
        this.id = 0;
        this.username = "";
        this.score = 0;
        this.timestamp = null;
    }

    public ScoreDto(String username, int score) {
        this.username = username;
        this.score = score;
        this.timestamp = LocalDateTime.now();
    }

    public ScoreDto(int id, String username, int score, LocalDateTime timestamp) {
        this.id = id;
        this.username = username;
        this.score = score;
        this.timestamp = timestamp;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Date getTimestampAsDate() {
        if (timestamp == null) return null;
        return Date.from(timestamp.atZone(ZoneId.systemDefault()).toInstant());
    }
}
