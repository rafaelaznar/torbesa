package net.ausiasmarch.capitals.service;

import java.util.Objects;

import net.ausiasmarch.capitals.dao.ScoreDao;
import net.ausiasmarch.capitals.model.ScoreDto;
import net.ausiasmarch.shared.connection.HikariPool;

import java.sql.*;
import java.time.LocalDateTime;

public class ScoreService {

    public boolean set(int userId, boolean correct) throws SQLException {

        HikariPool oPool = new HikariPool();
        try (Connection oConnection = oPool.getConnection()) {

            ScoreDao oScoreDao = new ScoreDao(oConnection);
            if (oScoreDao.count(userId) > 1) {
                oScoreDao.sanitize();
            }
            ScoreDto oUserScore = oScoreDao.get(userId);
            if (!Objects.isNull(oUserScore)) {
                oUserScore.setTries(oUserScore.getTries() + 1);
                if (correct) {
                    oUserScore.setScore(oUserScore.getScore() + 1);
                }
                return oScoreDao.update(oUserScore) > 0;
            } else {
                oUserScore = new ScoreDto();
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
        } finally {
            oPool.disposeConnection();
        }

    }

}
