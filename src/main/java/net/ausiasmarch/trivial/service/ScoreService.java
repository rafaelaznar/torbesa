package net.ausiasmarch.trivial.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.trivial.dao.ScoreDao2;
import net.ausiasmarch.trivial.model.ScoreDto2;

public class ScoreService {
     public boolean set(int userId, boolean correct) throws SQLException {
        
        try (Connection oConnection = HikariPool.getConnection()) {

            ScoreDao2 oScoreDao = new ScoreDao2(oConnection);
            if (oScoreDao.count(userId) > 1) {
                oScoreDao.sanitize();
            }
            ScoreDto2 oUserScore = oScoreDao.get(userId);
            if (!Objects.isNull(oUserScore)) {
                oUserScore.setTries(oUserScore.getTries() + 1);
                if (correct) {
                    oUserScore.setScore(oUserScore.getScore() + 1);
                }
                return oScoreDao.update(oUserScore) > 0;
            } else {
                oUserScore = new ScoreDto2();
                oUserScore.setUserId(userId);
                oUserScore.setTries(1);
                if (correct) {
                    oUserScore.setScore(1);
                } else {
                    oUserScore.setScore(0);
                }
                oUserScore.setTimestamp(LocalDateTime.now());
                return oScoreDao.insert(oUserScore) > 0;
            }
        }

    }

    public List<ScoreDto2> getHighScores() throws SQLException {        
        try (Connection oConnection = HikariPool.getConnection()) {
            ScoreDao2 oScoreDao = new ScoreDao2(oConnection);
            return (List<ScoreDto2>) oScoreDao.getTop10();
        }
    }
}
