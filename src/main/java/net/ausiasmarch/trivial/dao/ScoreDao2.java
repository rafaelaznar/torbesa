package net.ausiasmarch.trivial.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.ausiasmarch.trivial.model.ScoreDto2;

public class ScoreDao2 {
    Connection oConnection = null;

    public ScoreDao2(Connection oConnection) {
        this.oConnection = oConnection;
    }

    public ScoreDto2 get(int userId) throws SQLException {
        ScoreDto2 oScore = null;
        String sql = "SELECT * FROM trivial_score WHERE user_id = ? ORDER BY timestamp DESC LIMIT 1";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            oScore = new ScoreDto2(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("score"),
                    rs.getInt("tries"),
                    rs.getTimestamp("timestamp").toLocalDateTime(),
                    null 
            );
        }
        return oScore;
    }

    public int insertOrUpdate(ScoreDto2 oScore, boolean correct) throws SQLException {
        ScoreDto2 existingScore = get(oScore.getUserId());

        if (existingScore == null) {
           
            String insertSql = "INSERT INTO trivial_score (user_id, score, tries, timestamp) VALUES (?, ?, ?, NOW())";
            PreparedStatement insertStmt = oConnection.prepareStatement(insertSql);
            insertStmt.setInt(1, oScore.getUserId());
            insertStmt.setInt(2, correct ? oScore.getScore() : 0);
            insertStmt.setInt(3, 1); // primer intento
            return insertStmt.executeUpdate();
        } else {
            
            int newScore = existingScore.getScore() + (correct ? 1 : 0);
            int newTries = existingScore.getTries() + 1;

            String updateSql = "UPDATE trivial_score SET score = ?, tries = ?, timestamp = NOW() WHERE id = ?";
            PreparedStatement updateStmt = oConnection.prepareStatement(updateSql);
            updateStmt.setInt(1, newScore);
            updateStmt.setInt(2, newTries);
            updateStmt.setInt(3, existingScore.getId());
            return updateStmt.executeUpdate();
        }
    }

    public int count(int userId) throws SQLException {
        int count = 0;
        String sql = "SELECT COUNT(*) AS score_count FROM trivial_score WHERE user_id = ?";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            count = rs.getInt("score_count");
        }
        return count;
    }

    public void sanitize() throws SQLException {
        // Elimina scores antiguos, deja el más reciente por usuario
        String sql = "DELETE s1 FROM trivial_score s1 " +
                     "INNER JOIN trivial_score s2 ON s1.user_id = s2.user_id " +
                     "WHERE s1.timestamp < s2.timestamp";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.executeUpdate();
    }

    public List<ScoreDto2> getTop10() throws SQLException {
        List<ScoreDto2> scores = new ArrayList<>();
        String sql = "SELECT * FROM trivial_score s JOIN users u ON s.user_id = u.id " +
                     "ORDER BY s.score DESC, s.timestamp DESC LIMIT 10";
        Statement stmt = oConnection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            scores.add(new ScoreDto2(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("score"),
                    rs.getInt("tries"),
                    rs.getTimestamp("timestamp").toLocalDateTime(),
                    rs.getString("username")
            ));
        }
        return scores;
    }

    public List<ScoreDto2> getAll() throws SQLException {
        List<ScoreDto2> scores = new ArrayList<>();
        String sql = "SELECT * FROM trivial_score s JOIN users u ON s.user_id = u.id";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            scores.add(new ScoreDto2(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("score"),
                    rs.getInt("tries"),
                    rs.getTimestamp("timestamp").toLocalDateTime(),
                    rs.getString("username")
            ));
        }
        return scores;
    }

    public int delete(int id) throws SQLException {
        String deleteSql = "DELETE FROM trivial_score WHERE id = ?";
        PreparedStatement deleteStmt = oConnection.prepareStatement(deleteSql);
        deleteStmt.setInt(1, id);
        return deleteStmt.executeUpdate();
    }

    public int update(ScoreDto2 oUserScore) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    public int insert(ScoreDto2 oUserScore) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }
}

