package net.ausiasmarch.capitals.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import net.ausiasmarch.capitals.dao.ScoreDao;
import net.ausiasmarch.capitals.model.ScoreDto;
import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.shared.model.UserBean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/capitals/ScoreServlet")
public class ScoreServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HikariPool oPool = new HikariPool();
        try (Connection oConnection = oPool.getConnection()) {

            ScoreDao oScoreDao = new ScoreDao(oConnection);

            HttpSession session = request.getSession();
            UserBean user = (UserBean) session.getAttribute("sessionUser");
            if (user == null) {
                request.setAttribute("sessionUser", null);
            } else {
                request.setAttribute("sessionUser", user);
            }

            List<ScoreDto> highScoresList = oScoreDao.getTop10();
            request.setAttribute("highScores", highScoresList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("highscores.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            request.setAttribute("errorMessage", "Database error");
            RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
            dispatcher.forward(request, response);
        } finally {
            try {
                oPool.disposeConnection();
            } catch (SQLException e) {
                System.err.println("Database error: " + e.getMessage());
                request.setAttribute("errorMessage", "Database error");
                RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
                dispatcher.forward(request, response);
            }
        }
    }
}
