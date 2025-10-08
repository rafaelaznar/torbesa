package net.ausiasmarch.f1.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import net.ausiasmarch.capitals.model.ScoreDto;
import net.ausiasmarch.f1.dao.F1ScoreDao;
import net.ausiasmarch.shared.connection.HikariPool;

public class F1ScoreService {

    public boolean set(int userId, boolean correct) throws SQLException {
        try (Connection oConnection = HikariPool.getConnection()) {
            F1ScoreDao oScoreDao = new F1ScoreDao(oConnection);
            ScoreDto oUserScore = oScoreDao.get(userId);
            if (oUserScore != null) {
                oUserScore.setTries(oUserScore.getTries() + 1);
                if (correct) {
                    oUserScore.setScore(oUserScore.getScore() + 1);
                }
                return oScoreDao.update(oUserScore) > 0;
            } else {
                oUserScore = new ScoreDto();
                oUserScore.setUserId(userId);
                oUserScore.setTries(1);
                oUserScore.setScore(correct ? 1 : 0);
                oUserScore.setTimestamp(LocalDateTime.now());
                return oScoreDao.insert(oUserScore) > 0;
            }
        }
    }

    public List<ScoreDto> getHighScores() throws SQLException {
        try (Connection oConnection = HikariPool.getConnection()) {
            F1ScoreDao oScoreDao = new F1ScoreDao(oConnection);
            return oScoreDao.getTop10();
        }
    }
}
