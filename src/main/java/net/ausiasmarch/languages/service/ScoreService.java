package net.ausiasmarch.languages.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import net.ausiasmarch.languages.dao.ScoreDAO;
import net.ausiasmarch.languages.model.ScoreDTO;
import net.ausiasmarch.shared.connection.HikariPool;

public class ScoreService {

    public boolean set(int userId, boolean correct) throws SQLException {
        
        try (Connection con = HikariPool.getConnection()) {

            ScoreDAO scoreDAO = new ScoreDAO(con);
            if (scoreDAO.count(userId) > 1) {
                scoreDAO.sanitize();
            }

            ScoreDTO userScore = scoreDAO.get(userId);
            if (!Objects.isNull(userScore)) {
                userScore.setTries(userScore.getTries() + 1);
                if (correct) {
                    userScore.setScore(userScore.getScore() + 1);
                }
                return scoreDAO.update(userScore) > 0;

            } else {
                userScore = new ScoreDTO();
                userScore.setUserId(userId);
                userScore.setTries(1);
                if (correct) {
                    userScore.setScore(1);
                } else {
                    userScore.setScore(0);
                }
                userScore.setTimestamp(LocalDateTime.now());
                return scoreDAO.insert(userScore) > 0;
            }
        }
    }

    public List<ScoreDTO> getHighScores() throws SQLException {        
        try (Connection con = HikariPool.getConnection()) {
            ScoreDAO scoreDAO = new ScoreDAO(con);
            return scoreDAO.getTop10();
        }
    }

}