package net.ausiasmarch.capitals.models;

import java.time.LocalDateTime;

public class GameSession {
    private int id;
    private int userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int score;

    public GameSession(int id, int userId, LocalDateTime startTime, LocalDateTime endTime, int score) {
        this.id = id;
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.score = score;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public int getScore() { return score; }

    public void setId(int id) { this.id = id; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public void setScore(int score) { this.score = score; }
}
