package net.ausiasmarch.math.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import net.ausiasmarch.math.model.MathScoreBean;

public class MathScoreDao {
    private final Connection connection;

    public MathScoreDao(Connection connection) {
        this.connection = connection;
    }

    // Inserta una nueva puntuación
    public int create(MathScoreBean score) throws SQLException {
        String sql = "INSERT INTO math_scores (user_id, score, tries, timestamp) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, score.getUserId());
            ps.setInt(2, score.getScore());
            ps.setInt(3, score.getTries());
            ps.setTimestamp(4, new java.sql.Timestamp(score.getTimestamp().getTime()));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    // Crea o actualiza (sumando valores)
    public void createOrUpdate(MathScoreBean score) throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM math_scores WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(checkSql)) {
            ps.setInt(1, score.getUserId());
            ResultSet rs = ps.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                // 🔄 Actualiza sumando score y tries
                String updateSql = "UPDATE math_scores " +
                                   "SET score = score + ?, tries = tries + ?, timestamp = ? " +
                                   "WHERE user_id = ?";
                try (PreparedStatement ups = connection.prepareStatement(updateSql)) {
                    ups.setInt(1, score.getScore());
                    ups.setInt(2, score.getTries());
                    ups.setTimestamp(3, new java.sql.Timestamp(score.getTimestamp().getTime()));
                    ups.setInt(4, score.getUserId());
                    ups.executeUpdate();
                }
            } else {
                // ➕ Si no existe, lo crea
                create(score);
            }
        }
    }

    // Devuelve el top 10 global con usernames
    public List<MathScoreBean> getTopScores() throws SQLException {
        List<MathScoreBean> list = new ArrayList<>();
        String sql = "SELECT m.user_id, m.score, m.tries, m.timestamp, u.username " +
                     "FROM math_scores m " +
                     "JOIN users u ON m.user_id = u.id " +
                     "ORDER BY m.score DESC, m.tries ASC LIMIT 10";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MathScoreBean score = new MathScoreBean();
                score.setUserId(rs.getInt("user_id"));
                score.setScore(rs.getInt("score"));
                score.setTries(rs.getInt("tries"));
                score.setTimestamp(rs.getTimestamp("timestamp"));
                score.setUsername(rs.getString("username"));
                list.add(score);
            }
        }
        return list;
    }
}
