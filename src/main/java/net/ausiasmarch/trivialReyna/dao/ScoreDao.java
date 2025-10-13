package net.ausiasmarch.trivialReyna.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import net.ausiasmarch.trivialReyna.model.ScoreDto;

public class ScoreDao {

    Connection oConnection = null;

    public ScoreDao(Connection oConnection) {
        this.oConnection = oConnection;
    }

    public ScoreDto get(int userId) throws SQLException {
        if (count(userId) > 1) {
            sanitize();
        }
        ScoreDto oScore = null;
        String sql = "SELECT * FROM trivialReyna_score, users WHERE trivialReyna_score.user_id = ? AND users.id = ? ORDER BY timestamp DESC";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setInt(1, userId);
        stmt.setInt(2, userId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            oScore = new ScoreDto(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("score"),
                    rs.getInt("tries"),
                    rs.getTimestamp("timestamp").toLocalDateTime(),
                    rs.getString("username"));
        }
        return oScore;
    }

    public int count(int userId) throws SQLException {
        int count = 0;
        String sql = "SELECT COUNT(*) AS score_count FROM trivialReyna_score WHERE user_id = ?";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            count = rs.getInt("score_count");
        }
        return count;
    }

    public void sanitize() throws SQLException {
        String sql = "DELETE s1 FROM trivialReyna_score s1 " +
                "INNER JOIN trivialReyna_score s2 ON s1.user_id = s2.user_id " +
                "WHERE s1.timestamp < s2.timestamp";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        int deletedRows = stmt.executeUpdate();
        System.out.println("Sanitized " + deletedRows + " duplicate scores");
    }

    public List<ScoreDto> getTop10() throws SQLException {
        List<ScoreDto> scores = new ArrayList<>();
        String sql = "SELECT DISTINCT ts.id, ts.user_id, ts.score, ts.tries, ts.timestamp, u.username " +
                    "FROM trivialReyna_score ts " +
                    "INNER JOIN users u ON ts.user_id = u.id " +
                    "WHERE ts.id IN (SELECT MAX(id) FROM trivialReyna_score GROUP BY user_id) " +
                    "ORDER BY ts.score DESC, ts.timestamp DESC " +
                    "LIMIT 10";
                    
        try (PreparedStatement stmt = oConnection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                scores.add(new ScoreDto(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("score"),
                    rs.getInt("tries"),
                    rs.getTimestamp("timestamp").toLocalDateTime(),
                    rs.getString("username")));
            }
        }
        return scores;
    }

    public int insert(ScoreDto oScore) throws SQLException {
        String insertSql = "INSERT INTO trivialReyna_score (user_id, score, tries, timestamp) VALUES (?, ?, ?, NOW())";
        PreparedStatement insertStmt = oConnection.prepareStatement(insertSql);
        insertStmt.setInt(1, oScore.getUserId());
        insertStmt.setInt(2, oScore.getScore());
        insertStmt.setInt(3, oScore.getTries());
        return insertStmt.executeUpdate();
    }

    public int update(ScoreDto oScore) throws SQLException {
        String updateSql = "UPDATE trivialReyna_score SET score = ?, tries = ?, timestamp = NOW() WHERE user_id = ?";
        PreparedStatement updateStmt = oConnection.prepareStatement(updateSql);
        updateStmt.setInt(1, oScore.getScore());
        updateStmt.setInt(2, oScore.getTries());
        updateStmt.setInt(3, oScore.getUserId());
        return updateStmt.executeUpdate();
    }

    public int delete(int id) throws SQLException {
        String deleteSql = "DELETE FROM trivialReyna_score WHERE id = ?";
        PreparedStatement deleteStmt = oConnection.prepareStatement(deleteSql);
        deleteStmt.setInt(1, id);
        return deleteStmt.executeUpdate();
    }
}
