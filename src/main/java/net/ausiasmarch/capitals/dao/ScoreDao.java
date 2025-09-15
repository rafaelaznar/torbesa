package net.ausiasmarch.capitals.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import net.ausiasmarch.capitals.model.Score;
import net.ausiasmarch.capitals.service.DatabaseService;

public class ScoreDao {

    public Score get(int userId) {
        if (count(userId) > 1) {
            sanitize();
        }
        Score oScore = new Score(0, userId, 0, 0, LocalDateTime.now());
        try (Connection conn = DatabaseService.getConnection()) {
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

    public int count(int userId) {
        int count = 0;
        try (Connection conn = DatabaseService.getConnection()) {
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

    public int count() throws SQLException {
        try (Connection conn = DatabaseService.getConnection()) {




        } catch (SQLException e) {
            throw e;
        }
        return 0;
    }

    public void sanitize() {
        // if a user has more then one score
        // then remove all scores in database
        // except the last one
        try (Connection conn = DatabaseService.getConnection()) {
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

    public List<Score> getTop10() {
        List<Score> scores = new ArrayList<>();
        try (Connection conn = DatabaseService.getConnection()) {
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
        return scores;
    }

    public List<Score> getAll() throws SQLException {
        try (Connection conn = DatabaseService.getConnection()) {
            String getAllString = "SELECT * FROM capitals_score ORDER BY user_id ASC";
            PreparedStatement getAllStmt = conn.prepareStatement(getAllString);
            ResultSet rs = getAllStmt.executeQuery();
            List<Score> scores = new ArrayList<>();
            while (rs.next()) {
                scores.add(new Score(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("score"),
                        rs.getInt("tries"),
                        rs.getTimestamp("timestamp").toLocalDateTime()));
            }
            return scores;
        } catch (SQLException e) {
            throw e;
        }

    }

    public int create(Score oScore) throws SQLException {
        try (Connection conn = DatabaseService.getConnection()) {
            String insertSql = "INSERT INTO capitals_score (user_id, score, tries, timestamp) VALUES (?, ?, ?, NOW())";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setInt(1, oScore.getUserId());
            insertStmt.setInt(2, oScore.getScore());
            insertStmt.setInt(3, 1);
            return insertStmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    public int update(Score oScore) throws SQLException {
        try (Connection conn = DatabaseService.getConnection()) {
            String updateSql = "UPDATE capitals_score SET score = ?, tries = ?, timestamp = NOW() WHERE user_id = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setInt(1, oScore.getScore());
            updateStmt.setInt(2, oScore.getTries());
            updateStmt.setInt(3, oScore.getId());
            return updateStmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    public int delete(int id) throws SQLException {
        try (Connection conn = DatabaseService.getConnection()) {
            String deleteSql = "DELETE FROM capitals_score WHERE id = ?";
            PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
            deleteStmt.setInt(1, id);
            return deleteStmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

}
