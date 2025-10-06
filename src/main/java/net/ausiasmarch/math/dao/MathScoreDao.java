package net.ausiasmarch.math.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import net.ausiasmarch.math.model.MathScoreBean;

public class MathScoreDao {
    private Connection connection;

    public MathScoreDao(Connection connection) { this.connection = connection; }

    public int create(MathScoreBean score, String username) throws Exception {
        String sql = "INSERT INTO math_scores(username, score, tries, timestamp) VALUES(?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, username);
            ps.setInt(2, score.getScore());
            ps.setInt(3, score.getTries());
            ps.setTimestamp(4, new java.sql.Timestamp(score.getTimestamp().getTime()));
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    public List<MathScoreBean> getTopScores() throws Exception {
        List<MathScoreBean> list = new ArrayList<>();
        String sql = "SELECT * FROM math_scores ORDER BY score DESC, tries ASC LIMIT 10";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                MathScoreBean score = new MathScoreBean();
                score.setScore(rs.getInt("score"));
                score.setTries(rs.getInt("tries"));
                score.setTimestamp(rs.getTimestamp("timestamp"));
                list.add(score);
            }
        }
        return list;
    }
}
