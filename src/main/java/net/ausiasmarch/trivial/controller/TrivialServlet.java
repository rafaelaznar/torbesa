package net.ausiasmarch.trivial.controller;

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

import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.shared.model.UserBean;
import net.ausiasmarch.trivial.dao.ScoreDao2;
import net.ausiasmarch.trivial.model.ScoreDto;
import net.ausiasmarch.trivial.model.TrivialBean;
import net.ausiasmarch.trivial.model.TrivialBean;

import net.ausiasmarch.trivial.service.TrivialService;

@WebServlet("/trivial/TrivialServlet")
public class TrivialServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("sessionUser");
        if (user == null) {
            response.sendRedirect("../shared/login.jsp");
            return;
        }

        TrivialService trivialService = new TrivialService();
        TrivialBean jsonResponse = trivialService.fetchTriviaQuestions();

        if (jsonResponse == null) {
            request.setAttribute("errorMessage", "Error fetching trivial questions");
            RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
            dispatcher.forward(request, response);
            return;
        }

        session.setAttribute("triviaQuestions", jsonResponse);
        request.setAttribute("question", jsonResponse);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/trivial/game_t.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("sessionUser");
        if (user == null) {
            response.sendRedirect("../shared/login.jsp");
            return;
        }

        String selectedAnswer = request.getParameter("selectedAnswer");
        TrivialBean currentQuestion = (TrivialBean) session.getAttribute("triviaQuestions");
        boolean correct = selectedAnswer != null && selectedAnswer.equals(currentQuestion.getCorrectAnswer());

        try (Connection oConnection = HikariPool.getConnection()) {

            ScoreDao2 scoreDao = new ScoreDao2(oConnection);

            ScoreDto scoreDto = new ScoreDto();
            scoreDto.setUserId(user.getId());
            scoreDto.setScore(1); 

            scoreDao.insertOrUpdate(scoreDto, correct);

            ScoreDto userScore = scoreDao.get(user.getId());
            List<ScoreDto> highScores = scoreDao.getTop10();

            request.setAttribute("userScore", userScore);
            request.setAttribute("highScores", highScores);
            request.setAttribute("question", currentQuestion);
            request.setAttribute("userAnswer", selectedAnswer);

            RequestDispatcher dispatcher = request.getRequestDispatcher("scores_j.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
            dispatcher.forward(request, response);
        }
    }
}
