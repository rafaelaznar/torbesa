package net.ausiasmarch.trivia.model;

import java.time.LocalDateTime;

public class TriviaScoreDto {
    private int userId;
    private int score;
    private int streak;
    private int bestScore;
    private int bestStreak;
    private LocalDateTime updatedAt;

    public TriviaScoreDto() {}

    public TriviaScoreDto(int userId, int score, int streak, int bestScore, int bestStreak, LocalDateTime updatedAt) {
        this.userId = userId;
        this.score = score;
        this.streak = streak;
        this.bestScore = bestScore;
        this.bestStreak = bestStreak;
        this.updatedAt = updatedAt;
    }

    public int getUserId() { return userId; }
    public int getScore() { return score; }
    public int getStreak() { return streak; }
    public int getBestScore() { return bestScore; }
    public int getBestStreak() { return bestStreak; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setUserId(int userId) { this.userId = userId; }
    public void setScore(int score) { this.score = score; }
    public void setStreak(int streak) { this.streak = streak; }
    public void setBestScore(int bestScore) { this.bestScore = bestScore; }
    public void setBestStreak(int bestStreak) { this.bestStreak = bestStreak; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}