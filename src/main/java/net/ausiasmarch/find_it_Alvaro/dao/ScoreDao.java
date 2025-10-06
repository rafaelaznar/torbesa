package net.ausiasmarch.find_it_Alvaro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import net.ausiasmarch.find_it_Alvaro.model.ScoreDto;

public class ScoreDao {

    Connection oConnection = null;

    public ScoreDao(Connection oConnection) {
        this.oConnection = oConnection;
    }

    public ScoreDto get(int userId) throws SQLException {
        ScoreDto oScore = null;
        String sql = "SELECT * FROM puntuajes_score WHERE user_id = ? ORDER BY timestamp DESC LIMIT 1";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            oScore = mapResultSetToScoreDto(rs);
        }
        return oScore;
    }

    public int count(int userId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM puntuajes_score WHERE user_id = ?";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public int count() throws SQLException {
        String sql = "SELECT COUNT(*) FROM puntuajes_score";
        Statement stmt = oConnection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public void sanitize() throws SQLException {
        String sql = "DELETE FROM puntuajes_score WHERE score < 0";
        Statement stmt = oConnection.createStatement();
        stmt.executeUpdate(sql);
    }

    public List<ScoreDto> getTop10() throws SQLException {
        List<ScoreDto> scores = new ArrayList<>();
        String sql = "SELECT * FROM puntuajes_score ORDER BY score DESC, tries ASC LIMIT 10";
        Statement stmt = oConnection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            scores.add(mapResultSetToScoreDto(rs));
        }
        return scores;
    }

    public List<ScoreDto> getAll() throws SQLException {
        List<ScoreDto> scores = new ArrayList<>();
        String sql = "SELECT * FROM puntuajes_score ORDER BY timestamp DESC";
        Statement stmt = oConnection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            scores.add(mapResultSetToScoreDto(rs));
        }
        return scores;
    }

    public int insert(ScoreDto oScore) throws SQLException {
        String sql = "INSERT INTO puntuajes_score (user_id, score, tries, timestamp, username) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = oConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, oScore.getUserId());
        stmt.setInt(2, oScore.getScore());
        stmt.setInt(3, oScore.getTries());
        stmt.setTimestamp(4, Timestamp.valueOf(oScore.getTimestamp()));
        stmt.setString(5, oScore.getUsername());
        int affectedRows = stmt.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Insert failed, no rows affected.");
        }
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Insert failed, no ID obtained.");
            }
        }
    }

    public int update(ScoreDto oScore) throws SQLException {
        String sql = "UPDATE puntuajes_score SET score = ?, tries = ?, timestamp = ?, username = ? WHERE id = ?";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setInt(1, oScore.getScore());
        stmt.setInt(2, oScore.getTries());
        stmt.setTimestamp(3, Timestamp.valueOf(oScore.getTimestamp()));
        stmt.setString(4, oScore.getUsername());
        stmt.setInt(5, oScore.getId());
        return stmt.executeUpdate();
    }

    public int delete(int id) throws SQLException {
        String sql = "DELETE FROM puntuajes_score WHERE id = ?";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setInt(1, id);
        return stmt.executeUpdate();
    }

    private ScoreDto mapResultSetToScoreDto(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int userId = rs.getInt("user_id");
        int score = rs.getInt("score");
        int tries = rs.getInt("tries");
        Timestamp timestamp = rs.getTimestamp("timestamp");
        String username = rs.getString("username");
        LocalDateTime localDateTime = timestamp != null ? timestamp.toLocalDateTime() : null;
        return new ScoreDto(id, userId, score, tries, localDateTime, username);
    }
}