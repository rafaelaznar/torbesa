package net.ausiasmarch.capitals.services;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDateTime;
import net.ausiasmarch.capitals.models.Score;

public class ScoreService {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/dobralio";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "tiger";

    public void updateScore(int userId, boolean correct) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            // contar los reultados de la tabla score para el usuario
            // y si hay más de un registro hacer un sanitize
            if (countScoresByUser(userId) > 1) {
                sanitizeScores();
            }
            // if user_id is in score table, update score
            // if correct add one point, if not subtract one point but not less than 0
            // if user_id is not in score table, insert user_id with score 1 if correct, 0
            // if not
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

    private int countScoresByUser(int userId) {
        int count = 0;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT COUNT(*) AS score_count FROM capitals_score WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("score_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public Score getScoreByUser(int userId) {
        if (countScoresByUser(userId) > 1) {
            sanitizeScores();
        }
        Score oScore = new Score(0, userId, 0, 0, LocalDateTime.now());
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT * FROM capitals_score, users WHERE capitals_score.user_id = ? AND users.id = ? ORDER BY timestamp DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                oScore = new Score(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("score"),
                        rs.getInt("tries"),
                        rs.getTimestamp("timestamp").toLocalDateTime(),
                        rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return oScore;
    }

    private void sanitizeScores() {
        // if a user has more then one score
        // then remove all scores in database
        // except the last one
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            // Delete all scores except the most recent one for each user
            String sql = "DELETE s1 FROM capitals_score s1 " +
                    "INNER JOIN capitals_score s2 ON s1.user_id = s2.user_id " +
                    "WHERE s1.timestamp < s2.timestamp";
            PreparedStatement stmt = conn.prepareStatement(sql);
            int deletedRows = stmt.executeUpdate();
            System.out.println("Sanitized " + deletedRows + " duplicate scores");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Score> getHighScores() {

        // get for every score the username of the user_id
        List<Score> scores = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
                String sql = "SELECT * FROM capitals_score, users ";
                sql += "WHERE capitals_score.user_id = users.id ";
                sql += "ORDER BY capitals_score.score DESC, capitals_score.timestamp DESC LIMIT 10";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    scores.add(new Score(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getInt("score"),
                            rs.getInt("tries"),
                            rs.getTimestamp("timestamp").toLocalDateTime(),
                            rs.getString("username")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return scores;
    }
}
