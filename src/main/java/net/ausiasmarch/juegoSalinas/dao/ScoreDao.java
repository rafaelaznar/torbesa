package net.ausiasmarch.juegoSalinas.dao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.ausiasmarch.juegoSalinas.model.ScoreDto;

public class ScoreDao {
    private static final List<ScoreDto> scores = new ArrayList<>();

    public void addScore(ScoreDto newScore) {
        boolean updated = false;

        for (ScoreDto score : new ArrayList<>(scores)) { // Evitar ConcurrentModification
            if (score.getUsername().equals(newScore.getUsername())) {
                int newTotal = score.getScore() + newScore.getScore();
                scores.remove(score);
                scores.add(new ScoreDto(score.getUsername(), newTotal));
                updated = true;
                break;
            }
        }

        if (!updated) {
            scores.add(newScore);
        }
    }

    public List<ScoreDto> getTopScores(int limit) {
        List<ScoreDto> sorted = new ArrayList<>(scores);
        sorted.sort(Comparator.comparingInt(ScoreDto::getScore).reversed());
        return sorted.subList(0, Math.min(limit, sorted.size()));
    }
}
