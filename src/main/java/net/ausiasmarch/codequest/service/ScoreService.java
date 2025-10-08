package net.ausiasmarch.codequest.service;

import java.sql.Connection;
import java.sql.SQLException;

import net.ausiasmarch.codequest.dao.ScoreDao;
import net.ausiasmarch.codequest.model.ScoreDto;
import net.ausiasmarch.shared.connection.HikariPool;

public class ScoreService {

    public void set(int userId, boolean isCorrect) throws SQLException {
        try (Connection oConnection = HikariPool.getConnection()) {
            ScoreDao oScoreDao = new ScoreDao(oConnection);
            ScoreDto existingScore = oScoreDao.get(userId);
            
            if (existingScore != null) {
                // Usuario ya tiene puntuación, actualizar
                int newScore = existingScore.getScore();
                int newTries = existingScore.getTries() + 1;
                
                if (isCorrect) {
                    newScore++;
                }
                
                ScoreDto updatedScore = new ScoreDto(userId, newScore, newTries);
                oScoreDao.update(updatedScore);
            } else {
                // Usuario nuevo, crear puntuación
                int initialScore = isCorrect ? 1 : 0;
                ScoreDto newScore = new ScoreDto(userId, initialScore, 1);
                oScoreDao.insert(newScore);
            }
        }
    }

    public ScoreDto getScore(int userId) throws SQLException {
        try (Connection oConnection = HikariPool.getConnection()) {
            ScoreDao oScoreDao = new ScoreDao(oConnection);
            return oScoreDao.get(userId);
        }
    }

    public int getTotalScores() throws SQLException {
        try (Connection oConnection = HikariPool.getConnection()) {
            ScoreDao oScoreDao = new ScoreDao(oConnection);
            return oScoreDao.count();
        }
    }
}