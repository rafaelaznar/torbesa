package net.ausiasmarch.codequest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.ausiasmarch.codequest.model.ScoreDto;

public class ScoreDao {

    Connection oConnection = null;

    public ScoreDao(Connection oConnection) {
        this.oConnection = oConnection;
    }

    public ScoreDto get(int userId) throws SQLException {
        ScoreDto oScore = null;
        // CONSULTA CORREGIDA - JOIN explícito y LIMIT 1
        String sql = "SELECT cs.*, u.username FROM codequest_score cs " +
                     "JOIN users u ON cs.user_id = u.id " +
                     "WHERE cs.user_id = ? " +
                     "ORDER BY cs.timestamp DESC LIMIT 1";
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
                    rs.getString("username"));
        }
        return oScore;
    }

    public int count(int userId) throws SQLException {
        int count = 0;
        String sql = "SELECT COUNT(*) AS score_count FROM codequest_score WHERE user_id = ?";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            count = rs.getInt("score_count");
        }
        return count;
    }

    public int count() throws SQLException {
        String countSql = "SELECT COUNT(*) AS total FROM codequest_score";
        PreparedStatement countStmt = oConnection.prepareStatement(countSql);
        ResultSet rs = countStmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("total");
        } else {
            return 0;
        }
    }

    // ELIMINA el método sanitize() o déjalo solo para uso manual
    public void sanitize() throws SQLException {
        // Solo usar manualmente si es necesario
        System.out.println("Sanitize llamado - pero no ejecutando DELETE automático");
        // String sql = "DELETE s1 FROM codequest_score s1 " +
        //         "INNER JOIN codequest_score s2 ON s1.user_id = s2.user_id " +
        //         "WHERE s1.timestamp < s2.timestamp";
        // PreparedStatement stmt = oConnection.prepareStatement(sql);
        // int deletedRows = stmt.executeUpdate();
        // System.out.println("Sanitized " + deletedRows + " duplicate scores");
    }

    public List<ScoreDto> getTop10() throws SQLException {
        List<ScoreDto> scores = new ArrayList<>();
        // CONSULTA CORREGIDA - JOIN explícito
        String sql = "SELECT cs.*, u.username FROM codequest_score cs " +
                     "JOIN users u ON cs.user_id = u.id " +
                     "ORDER BY cs.score DESC, cs.timestamp DESC LIMIT 10";
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

    public List<ScoreDto> getAll() throws SQLException {
        // CONSULTA CORREGIDA - JOIN explícito
        String sql = "SELECT cs.*, u.username FROM codequest_score cs " +
                     "JOIN users u ON cs.user_id = u.id";
        PreparedStatement getAllStmt = oConnection.prepareStatement(sql);
        ResultSet rs = getAllStmt.executeQuery();
        List<ScoreDto> scores = new ArrayList<>();
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
        String insertSql = "INSERT INTO codequest_score (user_id, score, tries, timestamp) VALUES (?, ?, ?, NOW())";
        PreparedStatement insertStmt = oConnection.prepareStatement(insertSql);
        insertStmt.setInt(1, oScore.getUserId());
        insertStmt.setInt(2, oScore.getScore());
        insertStmt.setInt(3, oScore.getTries());  // CORREGIDO: usa el valor del objeto
        return insertStmt.executeUpdate();
    }

    public int update(ScoreDto oScore) throws SQLException {
        String updateSql = "UPDATE codequest_score SET score = ?, tries = ?, timestamp = NOW() WHERE user_id = ?";
        PreparedStatement updateStmt = oConnection.prepareStatement(updateSql);
        updateStmt.setInt(1, oScore.getScore());
        updateStmt.setInt(2, oScore.getTries());
        updateStmt.setInt(3, oScore.getUserId());
        return updateStmt.executeUpdate();
    }

    public int delete(int id) throws SQLException {
        String deleteSql = "DELETE FROM codequest_score WHERE id = ?";
        PreparedStatement deleteStmt = oConnection.prepareStatement(deleteSql);
        deleteStmt.setInt(1, id);
        return deleteStmt.executeUpdate();
    }
}