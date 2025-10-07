package net.ausiasmarch.math.controller;

import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.math.model.MathScoreBean;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.sql.*;
import java.util.*;

@WebServlet("/math/HighscoresPublicServlet")
public class HighscoresPublicServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<MathScoreBean> highscores = new ArrayList<>();

        String query = "SELECT u.username, m.score, m.tries, m.timestamp " +
                       "FROM math_scores m " +
                       "JOIN users u ON m.user_id = u.id " +
                       "ORDER BY m.score DESC, m.tries ASC " +
                       "LIMIT 10";

        try (Connection conn = HikariPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                MathScoreBean scoreBean = new MathScoreBean();
                scoreBean.setUsername(rs.getString("username"));
                scoreBean.setScore(rs.getInt("score"));
                scoreBean.setTries(rs.getInt("tries"));
                scoreBean.setTimestamp(rs.getTimestamp("timestamp"));
                highscores.add(scoreBean);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "No se pudo obtener el ranking público.");
        }

        request.setAttribute("highscores", highscores);
        request.getRequestDispatcher("/math/highscores_public.jsp").forward(request, response);
    }
}
