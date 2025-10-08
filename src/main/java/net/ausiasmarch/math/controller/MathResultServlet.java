package net.ausiasmarch.math.controller;

import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.shared.model.UserBean;
import net.ausiasmarch.math.model.MathScoreBean;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.sql.*;
import java.util.*;

@WebServlet("/math/ResultServlet")
public class MathResultServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("sessionUser") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        UserBean user = (UserBean) session.getAttribute("sessionUser");
        int userId = user.getId(); // ID único del usuario

        MathScoreBean mathScore = (MathScoreBean) session.getAttribute("mathScore");
        int score = (mathScore != null) ? mathScore.getScore() : 0;
        int attempts = (mathScore != null) ? mathScore.getTries() : 0;

        // ✅ Insertar o actualizar la fila del usuario en la tabla (versión correcta y funcional)
        String upsertSql = "INSERT INTO math_scores (user_id, score, tries, timestamp) " +
                "VALUES (?, ?, ?, NOW()) " +
                "ON DUPLICATE KEY UPDATE " +
                "score = score + VALUES(score), " +
                "tries = tries + VALUES(tries), " +
                "timestamp = NOW()";

        try (Connection conn = HikariPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(upsertSql)) {

            ps.setInt(1, userId);
            ps.setInt(2, score);
            ps.setInt(3, attempts);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "No se pudo guardar la puntuación en la base de datos.");
        }

        // ✅ Obtener top 10 puntuaciones
        List<MathScoreBean> highscores = new ArrayList<>();
        String query = "SELECT u.username, m.score, m.tries, m.timestamp " +
                       "FROM math_scores m JOIN users u ON m.user_id = u.id " +
                       "ORDER BY m.score DESC, m.tries ASC LIMIT 10";

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
        }

        // ✅ Limpiar sesión del juego
        session.removeAttribute("mathScore");

        request.setAttribute("score", score);
        request.setAttribute("highscores", highscores);
        request.getRequestDispatcher("/math/highscores.jsp").forward(request, response);
    }
}
