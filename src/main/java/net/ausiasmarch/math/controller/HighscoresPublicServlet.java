package net.ausiasmarch.math.controller;

import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.math.model.MathScoreBean;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/math/HighscoresPublicServlet")
public class HighscoresPublicServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<MathScoreBean> highscores = new ArrayList<>();

        String sql = "SELECT u.username, m.score, m.tries, m.timestamp " +
                     "FROM math_scores m JOIN users u ON m.user_id = u.id " +
                     "ORDER BY m.score DESC, m.tries ASC LIMIT 10";

        try (Connection conn = HikariPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                MathScoreBean bean = new MathScoreBean();
                bean.setUsername(rs.getString("username"));
                bean.setScore(rs.getInt("score"));
                bean.setTries(rs.getInt("tries"));
                bean.setTimestamp(rs.getTimestamp("timestamp"));
                highscores.add(bean);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("highscores", highscores);
        request.getRequestDispatcher("/math/highscores_public.jsp").forward(request, response);
    }
}
