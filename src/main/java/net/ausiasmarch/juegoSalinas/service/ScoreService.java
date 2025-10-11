package net.ausiasmarch.juegoSalinas.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import net.ausiasmarch.juegoSalinas.dao.ScoreDao;
import net.ausiasmarch.juegoSalinas.model.ScoreDto;
import net.ausiasmarch.shared.connection.HikariPool; 

public class ScoreService {

    public boolean addScore(String username, int points) throws SQLException {
        try (Connection oConnection = HikariPool.getConnection()) {
            ScoreDao oScoreDao = new ScoreDao(oConnection);

            ScoreDto existing = oScoreDao.getByUsername(username);
            if (existing != null) {
                existing.setScore(existing.getScore() + points);
                existing.setTimestamp(LocalDateTime.now());
                return oScoreDao.update(existing) > 0;
            } else {
                ScoreDto newScore = new ScoreDto(username, points);
                newScore.setTimestamp(LocalDateTime.now());
                return oScoreDao.insert(newScore) > 0;
            }
        }
    }

    public List<ScoreDto> getTopScores(int limit) throws SQLException {
        try (Connection oConnection = HikariPool.getConnection()) {
            ScoreDao oScoreDao = new ScoreDao(oConnection);
            return oScoreDao.getTopScores(limit);
        }
    }
}
