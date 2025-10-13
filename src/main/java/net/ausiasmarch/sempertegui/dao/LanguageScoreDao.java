package net.ausiasmarch.sempertegui.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.ausiasmarch.sempertegui.model.LanguageScoreDto;

public class LanguageScoreDao {

    Connection oConnection = null;

    public LanguageScoreDao(Connection oConnection) {
        this.oConnection = oConnection;
    }

    public LanguageScoreDto get(int userId) throws SQLException {
        if (count(userId) > 1) {
            sanitize();
        }
        LanguageScoreDto oScore = null;
        String sql = "SELECT * FROM languages_score, users WHERE languages_score.user_id = ? AND users.id = ? ORDER BY timestamp DESC";
        try(PreparedStatement stmt = oConnection.prepareStatement(sql)){
            stmt.setInt(1, userId);
            stmt.setInt(2, userId);
            try(ResultSet rs = stmt.executeQuery()){
                if (rs.next()) {
                    oScore = new LanguageScoreDto(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("score"),
                        rs.getInt("tries"),
                        rs.getTimestamp("timestamp").toLocalDateTime(),
                        rs.getString("username"));
                }
            }
        }    
        return oScore;
    }

    public int count(int userId) throws SQLException {
        int count = 0;
        String sql = "SELECT COUNT(*) AS score_count FROM languages_score WHERE user_id = ?";
        try(PreparedStatement stmt = oConnection.prepareStatement(sql)){
            stmt.setInt(1, userId);
            try(ResultSet rs = stmt.executeQuery()){
                if (rs.next()) {
                    count = rs.getInt("score_count");
                }
            }
        }
        return count;
    }

    public int count() throws SQLException {
        String countSql = "SELECT COUNT(*) AS total FROM languages_score";
        try(Statement countStmt = oConnection.createStatement();
            ResultSet rs = countStmt.executeQuery(countSql)){
            if (rs.next()) {
                return rs.getInt("total");
            } else {
                return 0;
            }
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
        try(PreparedStatement stmt = oConnection.prepareStatement(sql)){
            int deletedRows = stmt.executeUpdate();
            System.out.println("Sanitized " + deletedRows + " duplicate scores");
        }
    }

    public List<LanguageScoreDto> getTop10() throws SQLException {
        List<LanguageScoreDto> scores = new ArrayList<>();
        String sql = "SELECT * FROM languages_score, users ";
        sql += "WHERE languages_score.user_id = users.id ";
        sql += "ORDER BY languages_score.score DESC, languages_score.timestamp DESC LIMIT 10";
        try(Statement stmt = oConnection.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

            while (rs.next()) {
                scores.add(new LanguageScoreDto(
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

    public List<LanguageScoreDto> getAll() throws SQLException {
        List<LanguageScoreDto> scores = new ArrayList<>();
        String sql = "SELECT * FROM languages_score, users ";
        sql += "WHERE languages_score.user_id = users.id";        
        try(Statement getAllStmt = oConnection.createStatement();
            ResultSet rs = getAllStmt.executeQuery(sql)){

            while (rs.next()) {
                scores.add(new LanguageScoreDto(
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

    public int insert(LanguageScoreDto oScore) throws SQLException {
        String insertSql = "INSERT INTO languages_score (user_id, score, tries, timestamp) VALUES (?, ?, ?, NOW())";
        try(PreparedStatement insertStmt = oConnection.prepareStatement(insertSql)){
            insertStmt.setInt(1, oScore.getUserId());
            insertStmt.setInt(2, oScore.getScore());
            insertStmt.setInt(3, oScore.getTries());
            return insertStmt.executeUpdate();
        }
    }

    public int update(LanguageScoreDto oScore) throws SQLException {
        String updateSql = "UPDATE languages_score SET score = ?, tries = ?, timestamp = NOW() WHERE user_id = ?";
        try(PreparedStatement updateStmt = oConnection.prepareStatement(updateSql)){
            updateStmt.setInt(1, oScore.getScore());
            updateStmt.setInt(2, oScore.getTries());
            updateStmt.setInt(3, oScore.getUserId());
            return updateStmt.executeUpdate();
        }
    }

    public int delete(int id) throws SQLException {
        String deleteSql = "DELETE FROM languages_score WHERE id = ?";
        try(PreparedStatement deleteStmt = oConnection.prepareStatement(deleteSql)){
            deleteStmt.setInt(1, id);
            return deleteStmt.executeUpdate();
        }
    }
}
