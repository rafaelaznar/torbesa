package net.ausiasmarch.kimetsu.service;

import net.ausiasmarch.kimetsu.dao.ScoreDao;
import net.ausiasmarch.kimetsu.model.ScoreDto;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import net.ausiasmarch.shared.connection.HikariPool;

public class ScoreService {
    public List<ScoreDto> getHighScores() throws SQLException {
        try (Connection oConnection = HikariPool.getConnection()) {
            ScoreDao oScoreDao = new ScoreDao(oConnection);
            return oScoreDao.getTop10();
        }
    }

    public void set(int userId, boolean correct) throws SQLException {
        try (Connection oConnection = HikariPool.getConnection()) {
            ScoreDao oScoreDao = new ScoreDao(oConnection);
            ScoreDto userScore = oScoreDao.get(userId);
            if (userScore == null) {
                int score = correct ? 1 : 0;
                int tries = 1;
                oScoreDao.insert(new ScoreDto(0, userId, score, tries, java.time.LocalDateTime.now()));
            } else {
                int score = userScore.getScore() + (correct ? 1 : 0);
                int tries = userScore.getTries() + 1;
                userScore.setScore(score);
                userScore.setTries(tries);
                userScore.setTimestamp(java.time.LocalDateTime.now());
                oScoreDao.update(userScore);
            }
        }
    }
}