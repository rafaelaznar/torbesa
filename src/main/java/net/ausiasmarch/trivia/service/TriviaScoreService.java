package net.ausiasmarch.trivia.service;

import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.trivia.dao.TriviaScoreDao;
import net.ausiasmarch.trivia.model.TriviaLeaderboardItem;
import net.ausiasmarch.trivia.model.TriviaScoreDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TriviaScoreService {

    public void upsert(int userId, int score, int streak) throws SQLException {
        try (Connection c = HikariPool.getConnection()) {
            new TriviaScoreDao(c).upsert(userId, score, streak);
        }
    }

    public void reset(int userId) throws SQLException {
        try (Connection c = HikariPool.getConnection()) {
            new TriviaScoreDao(c).reset(userId);
        }
    }

    public TriviaScoreDto getByUserId(int userId) throws Exception {
        try (Connection c = HikariPool.getConnection()) {
            return new TriviaScoreDao(c).getByUserId(userId);
        }
    }

    public List<TriviaLeaderboardItem> topN(int limit) throws Exception {
        try (Connection c = HikariPool.getConnection()) {
            return new TriviaScoreDao(c).topN(limit);
        }
    }
}