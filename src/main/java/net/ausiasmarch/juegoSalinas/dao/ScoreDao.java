package net.ausiasmarch.juegoSalinas.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import net.ausiasmarch.juegoSalinas.model.ScoreDto;

public class ScoreDao {

    private Connection oConnection;

    public ScoreDao(Connection oConnection) {
        this.oConnection = oConnection;
    }

    public ScoreDto getByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM scoreSalinas WHERE username = ?";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new ScoreDto(
                rs.getString("username"),
                rs.getInt("score")
            );
        }
        return null;
    }

    public int insert(ScoreDto oScore) throws SQLException {
        String sql = "INSERT INTO scoreSalinas (username, score, timestamp) VALUES (?, ?, NOW())";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setString(1, oScore.getUsername());
        stmt.setInt(2, oScore.getScore());
        return stmt.executeUpdate();
    }

    public int update(ScoreDto oScore) throws SQLException {
        String sql = "UPDATE scoreSalinas SET score = ?, timestamp = NOW() WHERE username = ?";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setInt(1, oScore.getScore());
        stmt.setString(2, oScore.getUsername());
        return stmt.executeUpdate();
    }

    public void addScore(ScoreDto newScore) throws SQLException {
        ScoreDto existing = getByUsername(newScore.getUsername());
        if (existing != null) {
            int newTotal = existing.getScore() + newScore.getScore();
            existing.setScore(newTotal);
            update(existing);
        } else {
            insert(newScore);
        }
    }

    public List<ScoreDto> getTopScores(int limit) throws SQLException {
        List<ScoreDto> scores = new ArrayList<>();
        String sql = "SELECT username, score FROM scoreSalinas ORDER BY score DESC LIMIT ?";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setInt(1, limit);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            scores.add(new ScoreDto(
                rs.getString("username"),
                rs.getInt("score")
            ));
        }

        return scores;
    }

    public int delete(String username) throws SQLException {
        String sql = "DELETE FROM scoreSalinas WHERE username = ?";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setString(1, username);
        return stmt.executeUpdate();
    }

    public int count() throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM scoreSalinas";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("total");
        }
        return 0;
    }
}
