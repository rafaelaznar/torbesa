package net.ausiasmarch.sempertegui.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import net.ausiasmarch.sempertegui.dao.LanguageScoreDao;
import net.ausiasmarch.sempertegui.model.LanguageScoreDto;
import net.ausiasmarch.shared.connection.HikariPool;

public class LanguageScoreService {

    public boolean set(int userId, int score) throws SQLException {
        
        try (Connection oConnection = HikariPool.getConnection()) {

            LanguageScoreDao oScoreDao = new LanguageScoreDao(oConnection);
            if (oScoreDao.count(userId) > 1) {
                oScoreDao.sanitize();
            }

            LanguageScoreDto oUserScore = oScoreDao.get(userId);
            if (Objects.isNull(oUserScore)) {
                oUserScore = new LanguageScoreDto();
                oUserScore.setUserId(userId);
                oUserScore.setScore(score);
                oUserScore.setTries(1);
                oUserScore.setTimestamp(LocalDateTime.now());
                return oScoreDao.insert(oUserScore) > 0;
            } else {
                oUserScore.setScore(oUserScore.getScore() + score);
                oUserScore.setTries(oUserScore.getTries() + 1);
                return oScoreDao.update(oUserScore) > 0; //
            }
        }
    }

    public List<LanguageScoreDto> getHighScores() throws SQLException {        
        try (Connection oConnection = HikariPool.getConnection()) {
            LanguageScoreDao oScoreDao = new LanguageScoreDao(oConnection);
            return oScoreDao.getTop10();
        }
    }

}