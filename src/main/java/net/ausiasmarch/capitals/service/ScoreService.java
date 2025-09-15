package net.ausiasmarch.capitals.service;

import java.util.List;
import java.util.Objects;

import net.ausiasmarch.capitals.dao.ScoreDao;
import net.ausiasmarch.capitals.model.Score;

import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDateTime;

public class ScoreService {

    public void updateScore(int userId, boolean correct) {
        try (Connection conn = DatabaseService.getConnection()) {
            ScoreDao oScoreDao = new ScoreDao();

            if (oScoreDao.count(userId) > 1) {
                oScoreDao.sanitize();
            }

            Score oUserScore = oScoreDao.get(userId);
            if (!Objects.isNull(oUserScore)) {
                oUserScore.setTries(oUserScore.getTries() + 1);
                if (correct) {
                    oUserScore.setScore(oUserScore.getScore() + 1);
                }
            } else {

            }


            //-----------------------------


            int score = 0;
            int tries = 0;
            String checkSql = "SELECT score, tries FROM capitals_score WHERE user_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, userId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                score = rs.getInt("score");
                tries = rs.getInt("tries");
                tries++;
                if (correct) {
                    score++;
                }
                String updateSql = "UPDATE capitals_score SET score = ?, tries = ?, timestamp = NOW() WHERE user_id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setInt(1, score);
                updateStmt.setInt(2, tries);
                updateStmt.setInt(3, userId);
                updateStmt.executeUpdate();
                return;
            } else {
                if (correct) {
                    score = 1;
                } else {
                    score = 0;
                }
                String insertSql = "INSERT INTO capitals_score (user_id, score, tries, timestamp) VALUES (?, ?, ?, NOW())";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setInt(1, userId);
                insertStmt.setInt(2, score);
                insertStmt.setInt(3, 1);
                insertStmt.executeUpdate();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
