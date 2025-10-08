package net.ausiasmarch.harrypotter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.ausiasmarch.harrypotter.model.HarryPotterScoreBean;

public class ScoreDAO {

    private Connection oConnection;

    public ScoreDAO(Connection oConnection) {
        this.oConnection = oConnection;
    }

    /**
     * Obtiene la última puntuación de un usuario.
     */
    public HarryPotterScoreBean get(int userId) throws SQLException {
        if (count(userId) > 1) {
            sanitize();
        }

        HarryPotterScoreBean score = null;
        String sql = "SELECT s.id, s.user_id, s.score, s.tries, s.timestamp, u.username " +
                     "FROM harrypotter_score s " +
                     "JOIN users u ON s.user_id = u.id " +
                     "WHERE s.user_id = ? " +
                     "ORDER BY s.timestamp DESC LIMIT 1";

        try (PreparedStatement stmt = oConnection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                score = new HarryPotterScoreBean(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("score"),
                        rs.getInt("tries"),
                        rs.getTimestamp("timestamp") != null ? 
                            rs.getTimestamp("timestamp").toLocalDateTime() : null,
                        rs.getString("username")
                );
            }
        }
        return score;
    }

    /**
     * Devuelve el número de puntuaciones que tiene un usuario.
     */
    public int count(int userId) throws SQLException {
        String sql = "SELECT COUNT(*) AS score_count FROM harrypotter_score WHERE user_id = ?";
        try (PreparedStatement stmt = oConnection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("score_count");
            }
        }
        return 0;
    }

    /**
     * Devuelve el número total de puntuaciones registradas.
     */
    public int count() throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM harrypotter_score";
        try (PreparedStatement stmt = oConnection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

    /**
     * Limpia puntuaciones duplicadas, dejando solo la más reciente por usuario.
     */
    public void sanitize() throws SQLException {
        String sql = "DELETE s1 FROM harrypotter_score s1 " +
                     "INNER JOIN harrypotter_score s2 ON s1.user_id = s2.user_id " +
                     "WHERE s1.timestamp < s2.timestamp";
        try (PreparedStatement stmt = oConnection.prepareStatement(sql)) {
            int deleted = stmt.executeUpdate();
            System.out.println("Sanitized " + deleted + " duplicate Harry Potter scores");
        }
    }

    /**
     * Devuelve el top 10 de puntuaciones ordenado por puntuación y fecha.
     */
    public List<HarryPotterScoreBean> getTop10() throws SQLException {
        List<HarryPotterScoreBean> scores = new ArrayList<>();
        String sql = "SELECT s.id, s.user_id, s.score, s.tries, s.timestamp, u.username " +
                     "FROM harrypotter_score s " +
                     "JOIN users u ON s.user_id = u.id " +
                     "ORDER BY s.score DESC, s.timestamp DESC LIMIT 10";

        try (Statement stmt = oConnection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                HarryPotterScoreBean score = new HarryPotterScoreBean(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("score"),
                        rs.getInt("tries"),
                        rs.getTimestamp("timestamp") != null ? 
                            rs.getTimestamp("timestamp").toLocalDateTime() : null,
                        rs.getString("username")
                );
                scores.add(score);
            }
        }
        return scores;
    }

    /**
     * Devuelve todas las puntuaciones (para administración o debug).
     */
    public List<HarryPotterScoreBean> getAll() throws SQLException {
        List<HarryPotterScoreBean> scores = new ArrayList<>();
        String sql = "SELECT * FROM harrypotter_score s " +
                     "JOIN users u ON s.user_id = u.id";

        try (PreparedStatement stmt = oConnection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                scores.add(new HarryPotterScoreBean(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("score"),
                        rs.getInt("tries"),
                        rs.getTimestamp("timestamp").toLocalDateTime(),
                        rs.getString("username")
                ));
            }
        }
        return scores;
    }

    /**
     * Inserta una nueva puntuación para un usuario.
     */
    public int insert(HarryPotterScoreBean score) throws SQLException {
        String sql = "INSERT INTO harrypotter_score (user_id, score, tries, timestamp) VALUES (?, ?, ?, NOW())";
        try (PreparedStatement stmt = oConnection.prepareStatement(sql)) {
            stmt.setInt(1, score.getUserId());
            stmt.setInt(2, score.getScore());
            stmt.setInt(3, score.getTries());
            return stmt.executeUpdate();
        }
    }

    /**
     * Actualiza la puntuación de un usuario existente.
     */
    public int update(HarryPotterScoreBean score) throws SQLException {
        String sql = "UPDATE harrypotter_score " +
                     "SET score = ?, tries = ?, timestamp = NOW() " +
                     "WHERE user_id = ?";
        try (PreparedStatement stmt = oConnection.prepareStatement(sql)) {
            stmt.setInt(1, score.getScore());
            stmt.setInt(2, score.getTries());
            stmt.setInt(3, score.getUserId());
            return stmt.executeUpdate();
        }
    }

    /**
     * Elimina una puntuación por su ID.
     */
    public int delete(int id) throws SQLException {
        String sql = "DELETE FROM harrypotter_score WHERE id = ?";
        try (PreparedStatement stmt = oConnection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate();
        }
    }
}

