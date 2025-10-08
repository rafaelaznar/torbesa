package net.ausiasmarch.harrypotter.service;

import net.ausiasmarch.harrypotter.dao.ScoreDAO;
import net.ausiasmarch.harrypotter.model.HarryPotterScoreBean;
import net.ausiasmarch.shared.connection.HikariPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ScoreService {

    public List<HarryPotterScoreBean> getHighScores() throws SQLException {
        try (Connection connection = HikariPool.getConnection()) {
            ScoreDAO scoreDAO = new ScoreDAO(connection);
            return scoreDAO.getTop10();
        }
    }

    public HarryPotterScoreBean getUserScore(int userId) throws SQLException {
        try (Connection connection = HikariPool.getConnection()) {
            ScoreDAO scoreDAO = new ScoreDAO(connection);
            return scoreDAO.get(userId);
        }
    }

    public void saveScore(HarryPotterScoreBean score) throws SQLException {
        try (Connection connection = HikariPool.getConnection()) {
            ScoreDAO scoreDAO = new ScoreDAO(connection);
            if (scoreDAO.count(score.getUserId()) > 0) {
                scoreDAO.update(score);
            } else {
                scoreDAO.insert(score);
            }
        }
    }
}

