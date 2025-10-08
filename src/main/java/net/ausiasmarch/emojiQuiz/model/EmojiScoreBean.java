package net.ausiasmarch.emojiQuiz.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class EmojiScoreBean {
    private int id;
    private int userId;
    private String username;
    private int score;
    private int tries;
    private LocalDateTime timestamp;

    public EmojiScoreBean() {}

    public EmojiScoreBean(int id, int userId, String username, int score, int tries, LocalDateTime timestamp) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.score = score;
        this.tries = tries;
        this.timestamp = timestamp;
    }

    public EmojiScoreBean(int userId, int score, int tries, LocalDateTime timestamp) {
        this.userId = userId;
        this.score = score;
        this.tries = tries;
        this.timestamp = timestamp;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public int getScore() { return score; }
    public int getTries() { return tries; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public Date getTimestampAsDate() {
        if (timestamp == null) {
            return null;
        }
            return Date.from(timestamp.atZone(ZoneId.systemDefault()).toInstant());
    }

    public void setId(int id) { this.id = id; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setUsername(String username) { this.username = username; }
    public void setScore(int score) { this.score = score; }
    public void setTries(int tries) { this.tries = tries; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
