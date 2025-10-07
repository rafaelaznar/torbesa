package net.ausiasmarch.trivial.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.ausiasmarch.trivial.model.ScoreDto;

public class ScoreDao2 {
    Connection oConnection = null;

    public ScoreDao2(Connection oConnection) {
        this.oConnection = oConnection;
    }

    public ScoreDto get(int userId) throws SQLException {
        ScoreDto oScore = null;
        String sql = "SELECT * FROM trivial_score WHERE user_id = ? ORDER BY timestamp DESC LIMIT 1";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            oScore = new ScoreDto(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("score"),
                    rs.getInt("tries"),
                    rs.getTimestamp("timestamp").toLocalDateTime(),
                    null // no necesitamos username aquí, se puede obtener aparte si hace falta
            );
        }
        return oScore;
    }

    public int insertOrUpdate(ScoreDto oScore, boolean correct) throws SQLException {
        ScoreDto existingScore = get(oScore.getUserId());

        if (existingScore == null) {
            // No existe → insert
            String insertSql = "INSERT INTO trivial_score (user_id, score, tries, timestamp) VALUES (?, ?, ?, NOW())";
            PreparedStatement insertStmt = oConnection.prepareStatement(insertSql);
            insertStmt.setInt(1, oScore.getUserId());
            insertStmt.setInt(2, correct ? oScore.getScore() : 0);
            insertStmt.setInt(3, 1); // primer intento
            return insertStmt.executeUpdate();
        } else {
            // Existe → update
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

    public List<ScoreDto> getTop10() throws SQLException {
        List<ScoreDto> scores = new ArrayList<>();
        String sql = "SELECT * FROM trivial_score s JOIN users u ON s.user_id = u.id " +
                     "ORDER BY s.score DESC, s.timestamp DESC LIMIT 10";
        Statement stmt = oConnection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            scores.add(new ScoreDto(
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

    public List<ScoreDto> getAll() throws SQLException {
        List<ScoreDto> scores = new ArrayList<>();
        String sql = "SELECT * FROM trivial_score s JOIN users u ON s.user_id = u.id";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            scores.add(new ScoreDto(
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
}

