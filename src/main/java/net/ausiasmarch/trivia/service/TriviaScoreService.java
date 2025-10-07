package net.ausiasmarch.trivia.service;

import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.trivia.dao.TriviaScoreDao;

import java.sql.Connection;
import java.sql.SQLException;

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
}