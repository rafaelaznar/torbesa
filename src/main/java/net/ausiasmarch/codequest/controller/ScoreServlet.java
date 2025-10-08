package net.ausiasmarch.codequest.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ausiasmarch.codequest.dao.ScoreDao;
import net.ausiasmarch.codequest.model.ScoreDto;
import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.shared.model.UserBean;

@WebServlet("/codequest/ScoreServlet")
public class ScoreServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("sessionUser");
        if (user == null) {
            try {
                response.sendRedirect("../shared/login.jsp");
            } catch (IOException e) {
                System.err.println("Error al redirigir a la página de inicio de sesión: " + e.getMessage());
            }
            return;
        } else {
            request.setAttribute("sessionUser", user);
        }

        try (Connection oConnection = HikariPool.getConnection()) {

            ScoreDao oScoreDao = new ScoreDao(oConnection);
            
            // Obtener la puntuación del usuario actual
            ScoreDto userScore = oScoreDao.get(user.getId());
            request.setAttribute("userScore", userScore);

            // Obtener las 10 mejores puntuaciones
            List<ScoreDto> highScores = oScoreDao.getTop10();
            request.setAttribute("highScores", highScores);

            // Obtener todas las puntuaciones para el leaderboard
            List<ScoreDto> allScores = oScoreDao.getAll();
            request.setAttribute("allScores", allScores);

            RequestDispatcher dispatcher = request.getRequestDispatcher("highscores.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            request.setAttribute("errorMessage", "Error de base de datos");
            try {
                RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
                dispatcher.forward(request, response);
            } catch (ServletException | IOException ex) {
                System.err.println("Error al redirigir a la página de error: " + ex.getMessage());
            }
        } catch (ServletException | IOException e) {
            System.err.println("Error al procesar la solicitud: " + e.getMessage());
        }
    }
}

