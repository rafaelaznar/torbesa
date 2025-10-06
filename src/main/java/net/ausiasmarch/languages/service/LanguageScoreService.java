package net.ausiasmarch.languages.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import net.ausiasmarch.languages.dao.LanguageScoreDao;
import net.ausiasmarch.languages.model.LanguageScoreDto;
import net.ausiasmarch.shared.connection.HikariPool;

public class LanguageScoreService {

    public boolean set(int userId, boolean correct) throws SQLException {
        
        try (Connection oConnection = HikariPool.getConnection()) {

            LanguageScoreDao oScoreDao = new LanguageScoreDao(oConnection);
            if (oScoreDao.count(userId) > 1) {
                oScoreDao.sanitize();
            }
            LanguageScoreDto oUserScore = oScoreDao.get(userId);
            if (!Objects.isNull(oUserScore)) {
                oUserScore.setTries(oUserScore.getTries() + 1);
                if (correct) {
                    oUserScore.setScore(oUserScore.getScore() + 1);
                }
                return oScoreDao.update(oUserScore) > 0;
            } else {
                oUserScore = new LanguageScoreDto();
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

    public List<LanguageScoreDto> getHighScores() throws SQLException {        
        try (Connection oConnection = HikariPool.getConnection()) {
            LanguageScoreDao oScoreDao = new LanguageScoreDao(oConnection);
            return oScoreDao.getTop10();
        }
    }

}