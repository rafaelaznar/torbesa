package net.ausiasmarch.languages.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.ausiasmarch.languages.model.ScoreDTO;

public class ScoreDAO {

    Connection con = null;

    public ScoreDAO(Connection con) {
        this.con = con;
    }

    public ScoreDTO get(int userId) throws SQLException {
        if (count(userId) > 1) {
            sanitize();
        }
        ScoreDTO scoreDTO = null;
        String sql = "SELECT * FROM languages_score, users WHERE languages_score.user_id = ? AND users.id = ? ORDER BY timestamp DESC";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, userId);
        stmt.setInt(2, userId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            scoreDTO = new ScoreDTO(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("score"),
                    rs.getInt("tries"),
                    rs.getTimestamp("timestamp").toLocalDateTime(),
                    rs.getString("username"));
        }
        return scoreDTO;
    }

    public int count(int userId) throws SQLException {
        int count = 0;
        String sql = "SELECT COUNT(*) AS score_count FROM languages_score WHERE user_id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            count = rs.getInt("score_count");
        }
        return count;
    }

    public int count() throws SQLException {
        String countSql = "SELECT COUNT(*) AS total FROM languages_score";
        PreparedStatement countStmt = con.prepareStatement(countSql);
        ResultSet rs = countStmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("total");
        } else {
            return 0;
        }
    }

    public void sanitize() throws SQLException {
        // if a user has more then one score
        // then remove all scores in database
        // except the last one
        // Delete all scores except the most recent one for each user
        String sql = "DELETE s1 FROM languages_score s1 " +
                "INNER JOIN languages_score s2 ON s1.user_id = s2.user_id " +
                "WHERE s1.timestamp < s2.timestamp";
        PreparedStatement stmt = con.prepareStatement(sql);
        int deletedRows = stmt.executeUpdate();
        System.out.println("Sanitized " + deletedRows + " duplicate scores");
    }

    public List<ScoreDTO> getTop10() throws SQLException {
        List<ScoreDTO> scores = new ArrayList<>();
        String sql = "SELECT * FROM languages_score, users ";
        sql += "WHERE languages_score.user_id = users.id ";
        sql += "ORDER BY langauges_score.score DESC, langauges_score.timestamp DESC LIMIT 10";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            scores.add(new ScoreDTO(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("score"),
                    rs.getInt("tries"),
                    rs.getTimestamp("timestamp").toLocalDateTime(),
                    rs.getString("username")));
        }
        return scores;
    }

    public List<ScoreDTO> getAll() throws SQLException {
        String sql = "SELECT * FROM languages_score, users ";
        sql += "WHERE langauges_score.user_id = users.id";        
        PreparedStatement getAllStmt = con.prepareStatement(sql);
        ResultSet rs = getAllStmt.executeQuery();
        List<ScoreDTO> scores = new ArrayList<>();
        while (rs.next()) {
            scores.add(new ScoreDTO(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("score"),
                    rs.getInt("tries"),
                    rs.getTimestamp("timestamp").toLocalDateTime(),
                    rs.getString("username")));
        }
        return scores;
    }

    public int insert(ScoreDTO oScore) throws SQLException {
        String insertSql = "INSERT INTO langauges_score (user_id, score, tries, timestamp) VALUES (?, ?, ?, NOW())";
        PreparedStatement insertStmt = con.prepareStatement(insertSql);
        insertStmt.setInt(1, oScore.getUserId());
        insertStmt.setInt(2, oScore.getScore());
        insertStmt.setInt(3, 1);
        return insertStmt.executeUpdate();
    }

    public int update(ScoreDTO oScore) throws SQLException {
        String updateSql = "UPDATE langauges_score SET score = ?, tries = ?, timestamp = NOW() WHERE user_id = ?";
        PreparedStatement updateStmt = con.prepareStatement(updateSql);
        updateStmt.setInt(1, oScore.getScore());
        updateStmt.setInt(2, oScore.getTries());
        updateStmt.setInt(3, oScore.getUserId());
        return updateStmt.executeUpdate();
    }

    public int delete(int id) throws SQLException {
        String deleteSql = "DELETE FROM languages_score WHERE id = ?";
        PreparedStatement deleteStmt = con.prepareStatement(deleteSql);
        deleteStmt.setInt(1, id);
        return deleteStmt.executeUpdate();
    }

}
