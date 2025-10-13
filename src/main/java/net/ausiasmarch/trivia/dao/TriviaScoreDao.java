package net.ausiasmarch.trivia.dao;

import net.ausiasmarch.trivia.model.TriviaLeaderboardItem;
import net.ausiasmarch.trivia.model.TriviaScoreDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TriviaScoreDao {
    private final Connection con;

    public TriviaScoreDao(Connection con) { this.con = con; }

    public TriviaScoreDto getByUserId(int userId) throws SQLException {
        String sql = "SELECT user_id, score, streak, best_score, best_streak, updated_at " +
                     "FROM trivia_score WHERE user_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                Timestamp ts = rs.getTimestamp("updated_at");
                return new TriviaScoreDto(
                    rs.getInt("user_id"),
                    rs.getInt("score"),
                    rs.getInt("streak"),
                    rs.getInt("best_score"),
                    rs.getInt("best_streak"),
                    ts != null ? ts.toLocalDateTime() : null
                );
            }
        }
    }

    /** Inserta si no existe; si existe, actualiza y recalcula best_* */
    public int upsert(int userId, int score, int streak) throws SQLException {
        String sql = "INSERT INTO trivia_score (user_id, score, streak, best_score, best_streak, updated_at) " +
                     "VALUES (?, ?, ?, ?, ?, NOW()) " +
                     "ON DUPLICATE KEY UPDATE " +
                     "  score = VALUES(score), " +
                     "  streak = VALUES(streak), " +
                     "  best_score  = GREATEST(best_score, VALUES(score)), " +
                     "  best_streak = GREATEST(best_streak, VALUES(streak)), " +
                     "  updated_at = NOW()";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, score);
            ps.setInt(3, streak);
            ps.setInt(4, score);
            ps.setInt(5, streak);
            return ps.executeUpdate();
        }
    }

    /** Pone score/streak a 0 (mantiene best_* si ya existen) */
    public int reset(int userId) throws SQLException {
        String sql = "INSERT INTO trivia_score (user_id, score, streak, best_score, best_streak, updated_at) " +
                     "VALUES (?, 0, 0, 0, 0, NOW()) " +
                     "ON DUPLICATE KEY UPDATE score=0, streak=0, updated_at=NOW()";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            return ps.executeUpdate();
        }
    }

    /** Top N global (ordenado por best_score, luego best_streak) */
    public List<TriviaLeaderboardItem> topN(int limit) throws SQLException {
        String sql =
            "SELECT u.username, t.best_score, t.best_streak, t.updated_at " +
            "FROM trivia_score t JOIN users u ON u.id = t.user_id " +
            "ORDER BY t.best_score DESC, t.best_streak DESC, t.updated_at ASC " +
            "LIMIT ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Math.max(1, limit));
            try (ResultSet rs = ps.executeQuery()) {
                List<TriviaLeaderboardItem> out = new ArrayList<>();
                while (rs.next()) {
                    TriviaLeaderboardItem item = new TriviaLeaderboardItem();
                    item.setUsername(rs.getString("username"));          // <-- cambia si tu columna no es "username"
                    item.setBestScore(rs.getInt("best_score"));
                    item.setBestStreak(rs.getInt("best_streak"));
                    Timestamp ts = rs.getTimestamp("updated_at");
                    item.setUpdatedAt(ts != null ? ts.toLocalDateTime() : null);
                    out.add(item);
                }
                return out;
            }
        }
    }
}