package net.ausiasmarch.juegoSalinas.service;

import java.util.List;
import net.ausiasmarch.juegoSalinas.model.ScoreDto;
import net.ausiasmarch.juegoSalinas.dao.ScoreDao;

public class ScoreService {
    private final ScoreDao scoreDao = new ScoreDao();

    public void addScore(ScoreDto score) {
        scoreDao.addScore(score);
    }

    public List<ScoreDto> getTopScores(int limit) {
        return scoreDao.getTopScores(limit);
    }
}