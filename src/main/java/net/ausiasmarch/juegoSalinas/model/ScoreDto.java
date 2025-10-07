package net.ausiasmarch.juegoSalinas.model;

public class ScoreDto {
    private String username;
    private int score;

    public ScoreDto(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() { return username; }
    public int getScore() { return score; }
}