package net.ausiasmarch.juegoSalinas.dao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.ausiasmarch.juegoSalinas.model.ScoreDto;

public class ScoreDao {
    private static final List<ScoreDto> scores = new ArrayList<>();

    public void addScore(ScoreDto score) {
        scores.add(score);
    }

    public List<ScoreDto> getTopScores(int limit) {
        List<ScoreDto> sorted = new ArrayList<>(scores);
        sorted.sort(Comparator.comparingInt(ScoreDto::getScore).reversed());
        return sorted.subList(0, Math.min(limit, sorted.size()));
    }
}
