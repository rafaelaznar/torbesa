package net.ausiasmarch.f1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.ausiasmarch.capitals.model.ScoreDto;

public class F1ScoreDao {

    Connection oConnection = null;

    public F1ScoreDao(Connection oConnection) {
        this.oConnection = oConnection;
    }

    public ScoreDto get(int userId) throws SQLException {
        ScoreDto oScore = null;
        String sql = "SELECT * FROM f1_score, users WHERE f1_score.user_id = ? AND users.id = ? ORDER BY timestamp DESC";
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

    public List<ScoreDto> getTop10() throws SQLException {
        List<ScoreDto> scores = new ArrayList<>();
        String sql = "SELECT * FROM f1_score, users ";
        sql += "WHERE f1_score.user_id = users.id ";
        sql += "ORDER BY f1_score.score DESC, f1_score.timestamp DESC LIMIT 10";
        Statement stmt = oConnection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            scores.add(new ScoreDto(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("score"),
                    rs.getInt("tries"),
                    rs.getTimestamp("timestamp").toLocalDateTime(),
                    rs.getString("username")));
        }
        return scores;
    }

    public int insert(ScoreDto oScore) throws SQLException {
        String insertSql = "INSERT INTO f1_score (user_id, score, tries, timestamp) VALUES (?, ?, ?, NOW())";
        PreparedStatement insertStmt = oConnection.prepareStatement(insertSql);
        insertStmt.setInt(1, oScore.getUserId());
        insertStmt.setInt(2, oScore.getScore());
        insertStmt.setInt(3, oScore.getTries());
        return insertStmt.executeUpdate();
    }

    public int update(ScoreDto oScore) throws SQLException {
        String updateSql = "UPDATE f1_score SET score = ?, tries = ?, timestamp = NOW() WHERE user_id = ?";
        PreparedStatement updateStmt = oConnection.prepareStatement(updateSql);
        updateStmt.setInt(1, oScore.getScore());
        updateStmt.setInt(2, oScore.getTries());
        updateStmt.setInt(3, oScore.getUserId());
        return updateStmt.executeUpdate();
    }

}
