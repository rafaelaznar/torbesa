package net.ausiasmarch.trivia.model;

import java.time.LocalDateTime;

public class TriviaLeaderboardItem {
    private String username;
    private int bestScore;
    private int bestStreak;
    private LocalDateTime updatedAt;

    public String getUsername() { return username; }
    public int getBestScore() { return bestScore; }
    public int getBestStreak() { return bestStreak; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setUsername(String username) { this.username = username; }
    public void setBestScore(int bestScore) { this.bestScore = bestScore; }
    public void setBestStreak(int bestStreak) { this.bestStreak = bestStreak; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}