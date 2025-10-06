package net.ausiasmarch.trivial.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import net.ausiasmarch.capitals.dao.ScoreDao;
import net.ausiasmarch.capitals.model.CountryBean;
import net.ausiasmarch.capitals.model.ScoreDto;
import net.ausiasmarch.capitals.service.CountryService;
import net.ausiasmarch.capitals.service.ScoreService;
import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.shared.model.UserBean;
import net.ausiasmarch.trivial.model.TrivialBean;
import net.ausiasmarch.trivial.service.TrivialService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet("/trivial/TrivialServlet")
public class TrivialServlet extends HttpServlet {

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

        TrivialService trivialService = new TrivialService();
        TrivialBean jsonResponse = trivialService.fetchTriviaQuestions();

        session.setAttribute("triviaQuestions", jsonResponse.getCorrectAnswer());
        request.setAttribute("question", jsonResponse);

        RequestDispatcher dispatcher = request.getRequestDispatcher("game_t.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            System.err.println("Error al redirigir a la página del juego: " + e.getMessage());
        }
       
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            HttpSession session = request.getSession();
            UserBean user = (UserBean) session.getAttribute("sessionUser");
            if (user == null) {
                response.sendRedirect("../shared/login.jsp");
                return;
            } else {
                request.setAttribute("username", user.getUsername());
            }

            

            try (Connection oConnection = HikariPool.getConnection()) {

                ScoreDao oScoreDao = new ScoreDao(oConnection);
                ScoreDto userScore = oScoreDao.get(user.getId());
                request.setAttribute("userScore", userScore);

                List<ScoreDto> highScores = oScoreDao.getTop10();
                request.setAttribute("highScores", highScores);

                RequestDispatcher dispatcher = request.getRequestDispatcher("scores_j.jsp");
                dispatcher.forward(request, response);

            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            request.setAttribute("errorMessage", "Database error");
            RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
            dispatcher.forward(request, response);
        }

    }
}
