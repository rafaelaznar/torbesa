package net.ausiasmarch.whosthatpokemon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.ausiasmarch.whosthatpokemon.model.ScoreBean;

public class ScoreDao {

    Connection oConnection = null;

    public ScoreDao(Connection oConnection) {
        this.oConnection = oConnection;
    }

    public ScoreBean get(int userId) throws SQLException {
        if (count(userId) > 1) {
            sanitize();
        }
        ScoreBean oScore = null;
        String sql = "SELECT * FROM whosthatpokemon_score, users WHERE whosthatpokemon_score.user_id = ? AND users.id = ? ORDER BY timestamp DESC";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setInt(1, userId);
        stmt.setInt(2, userId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            oScore = new ScoreBean(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("score"),
                    rs.getInt("tries"),
                    rs.getString("username"));
        }
        return oScore;
    }

    public int count(int userId) throws SQLException {
        int count = 0;
        String sql = "SELECT COUNT(*) AS score_count FROM whosthatpokemon_score WHERE user_id = ?";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            count = rs.getInt("score_count");
        }
        return count;
    }

    public int count() throws SQLException {
        String countSql = "SELECT COUNT(*) AS total FROM whosthatpokemon_score";
        PreparedStatement countStmt = oConnection.prepareStatement(countSql);
        ResultSet rs = countStmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("total");
        } else {
            return 0;
        }
    }

    public void sanitize() throws SQLException {
        String sql = "DELETE s1 FROM whosthatpokemon_score s1 " +
                "INNER JOIN whosthatpokemon_score s2 ON s1.user_id = s2.user_id " +
                "WHERE s1.timestamp < s2.timestamp";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        int deletedRows = stmt.executeUpdate();
        System.out.println("Sanitized " + deletedRows + " duplicate scores");
    }

    public List<ScoreBean> getTop10() throws SQLException {
        List<ScoreBean> scores = new ArrayList<>();
        String sql = "SELECT * FROM whosthatpokemon_score, users ";
        sql += "WHERE whosthatpokemon_score.user_id = users.id ";
        sql += "ORDER BY whosthatpokemon_score.score DESC, whosthatpokemon_score.timestamp DESC LIMIT 10";
        Statement stmt = oConnection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            scores.add(new ScoreBean(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("score"),
                    rs.getInt("tries"),
                    rs.getString("username")));
        }
        return scores;
    }

    public List<ScoreBean> getAll() throws SQLException {
        String sql = "SELECT * FROM whosthatpokemon_score, users ";
        sql += "WHERE whosthatpokemon_score.user_id = users.id";
        PreparedStatement getAllStmt = oConnection.prepareStatement(sql);
        ResultSet rs = getAllStmt.executeQuery();
        List<ScoreBean> scores = new ArrayList<>();
        while (rs.next()) {
            scores.add(new ScoreBean(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("score"),
                    rs.getInt("tries"),
                    rs.getString("username")));
        }
        return scores;
    }

    public int insert(ScoreBean oScore) throws SQLException {
        String insertSql = "INSERT INTO whosthatpokemon_score (user_id, score, tries, timestamp) VALUES (?, ?, ?, NOW())";
        PreparedStatement insertStmt = oConnection.prepareStatement(insertSql);
        insertStmt.setInt(1, oScore.getUserId());
        insertStmt.setInt(2, oScore.getScore());
        insertStmt.setInt(3, oScore.getTries());
        return insertStmt.executeUpdate();
    }

    public int update(ScoreBean oScore) throws SQLException {
        String updateSql = "UPDATE whosthatpokemon_score SET score = ?, tries = ?, timestamp = NOW() WHERE user_id = ?";
        PreparedStatement updateStmt = oConnection.prepareStatement(updateSql);
        updateStmt.setInt(1, oScore.getScore());
        updateStmt.setInt(2, oScore.getTries());
        updateStmt.setInt(3, oScore.getUserId());
        return updateStmt.executeUpdate();
    }

    public int delete(int id) throws SQLException {
        String deleteSql = "DELETE FROM whosthatpokemon_score WHERE id = ?";
        PreparedStatement deleteStmt = oConnection.prepareStatement(deleteSql);
        deleteStmt.setInt(1, id);
        return deleteStmt.executeUpdate();
    }

}
