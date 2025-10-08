package net.ausiasmarch.emojiQuiz.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import net.ausiasmarch.emojiQuiz.dao.EmojiScoreDao;
import net.ausiasmarch.emojiQuiz.model.EmojiScoreBean;
import net.ausiasmarch.shared.connection.HikariPool;

public class EmojiScoreService {

    public boolean set(int userId, boolean correct) throws SQLException {
        try (Connection cn = HikariPool.getConnection()) {
            cn.setAutoCommit(false);
            try {
                EmojiScoreDao dao = new EmojiScoreDao(cn);

                EmojiScoreBean last = dao.get(userId);

                int newScore = (last != null ? last.getScore() : 0) + (correct ? 1 : 0);
                int newTries = (last != null ? last.getTries() : 0) + 1;

                EmojiScoreBean toInsert = new EmojiScoreBean(userId, newScore, newTries, LocalDateTime.now());
                boolean ok = dao.insert(toInsert) > 0;

                dao.sanitizeByUser(userId);

                cn.commit();
                return ok;
            } catch (SQLException ex) {
                cn.rollback();
                throw ex;
            } finally {
                cn.setAutoCommit(true);
            }
        }
    }

    public List<EmojiScoreBean> getHighScores() throws SQLException {
        try (Connection cn = HikariPool.getConnection()) {
            EmojiScoreDao dao = new EmojiScoreDao(cn);
            return dao.getTop10();
        }
    }

    public EmojiScoreBean getCurrentUserScore(int userId) throws SQLException {
        try (Connection cn = HikariPool.getConnection()) {
            EmojiScoreDao dao = new EmojiScoreDao(cn);
            return dao.get(userId);
        }
    }
}
