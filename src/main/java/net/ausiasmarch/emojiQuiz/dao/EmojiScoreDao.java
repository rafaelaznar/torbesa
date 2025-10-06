package net.ausiasmarch.emojiQuiz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import net.ausiasmarch.emojiQuiz.model.EmojiScoreBean;

public class EmojiScoreDao {

    private final Connection conn;

    public EmojiScoreDao(Connection conn) {
        this.conn = conn;
    }

    private EmojiScoreBean mapRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int userId = rs.getInt("user_id");
        String username;
        try {
            username = rs.getString("username");
        } catch (SQLException ex) {
            username = null;
        }        int score = rs.getInt("score");
        int tries = rs.getInt("tries");
        Timestamp ts = rs.getTimestamp("timestamp");
        LocalDateTime ldt = ts != null ? ts.toLocalDateTime() : null;

        return new EmojiScoreBean(id, userId, username, score, tries, ldt);
    }

    public int countByUser(int userId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM emoji_quiz_score WHERE user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    public int sanitizeByUser(int userId) throws SQLException {
        String sql =
            "DELETE FROM emoji_quiz_score " +
            "WHERE user_id = ? " +
            "  AND id NOT IN ( " +
            "    SELECT id FROM ( " +
            "      SELECT id FROM emoji_quiz_score " +
            "      WHERE user_id = ? " +
            "      ORDER BY `timestamp` DESC " +
            "      LIMIT 1 " +
            "    ) t " +
            "  )";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            return ps.executeUpdate();
        }
    }

    public EmojiScoreBean get(int userId) throws SQLException {
        if (countByUser(userId) > 1) {
            sanitizeByUser(userId);
        }

        String sql =
            "SELECT e.id, e.user_id, u.username, e.score, e.tries, e.`timestamp` " +
            "FROM emoji_quiz_score e " +
            "JOIN users u ON e.user_id = u.id " +
            "WHERE e.user_id = ? " +
            "ORDER BY e.`timestamp` DESC " +
            "LIMIT 1";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    public List<EmojiScoreBean> getTop10() throws SQLException {
        String sql =
            "SELECT e.id, e.user_id, u.username, e.score, e.tries, e.`timestamp` " +
            "FROM emoji_quiz_score e " +
            "JOIN users u ON e.user_id = u.id " +
            "ORDER BY e.score DESC, e.tries ASC, e.`timestamp` DESC " +
            "LIMIT 10";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<EmojiScoreBean> list = new ArrayList<EmojiScoreBean>();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;
        }
    }

    public int insert(EmojiScoreBean b) throws SQLException {
        String sql = "INSERT INTO emoji_quiz_score (user_id, score, tries, `timestamp`) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, b.getUserId());
            ps.setInt(2, b.getScore());
            ps.setInt(3, b.getTries());

            LocalDateTime ts = b.getTimestamp();
            ps.setTimestamp(4, ts != null ? Timestamp.valueOf(ts) : new Timestamp(System.currentTimeMillis()));

            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        int newId = keys.getInt(1);
                        try {
                            b.setId(newId);
                        } catch (Exception ignore) {}
                        return newId;
                    }
                }
            }
        }
        return -1;
    }

    public int delete(int id) throws SQLException {
        String sql = "DELETE FROM emoji_quiz_score WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        }
    }
}
