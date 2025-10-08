package net.ausiasmarch.trivialReyna.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import net.ausiasmarch.trivialReyna.model.QuestionBean;
import net.ausiasmarch.trivialReyna.service.QuestionService;
import net.ausiasmarch.trivialReyna.service.ScoreService;
import net.ausiasmarch.trivialReyna.dao.ScoreDao;
import net.ausiasmarch.trivialReyna.model.ScoreDto;
import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.shared.model.UserBean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/trivialReyna/GameServlet")
public class GameServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("sessionUser");

        if (user == null) {
            try {
                response.sendRedirect("../shared/login.jsp");
            } catch (IOException e) {
                System.err.println("Error al redirigir a login: " + e.getMessage());
            }
            return;
        } else {
            request.setAttribute("sessionUser", user);
        }
        // Obtener pregunta aleatoria desde la API
        QuestionService oQuestionService = new QuestionService(request.getServletContext());
        QuestionBean selectedQuestion = oQuestionService.getOneRandomQuestion();
        
        // Obtener 3 opciones incluyendo la respuesta correcta
        ArrayList<String> optionsListForTest = oQuestionService.getRandomOptionsForTest(selectedQuestion, 3);
        
        // Establecer los atributos necesarios para la vista
        request.setAttribute("questionText", selectedQuestion.getQuestion());
        request.setAttribute("correctAnswer", selectedQuestion.getCorrectAnswer());
        request.setAttribute("options", optionsListForTest); 
        request.setAttribute("correctAnswer", selectedQuestion.getCorrectAnswer()); // Pasar la respuesta correcta al JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("game.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            System.err.println("Error al mostrar juego: " + e.getMessage());
        }
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

        String questionText = request.getParameter("questionText");
        String selectedAnswer = request.getParameter("selectedAnswer");
        String correctAnswer = request.getParameter("correctAnswer");

        request.setAttribute("questionText", questionText);
        request.setAttribute("selectedAnswer", selectedAnswer);
        request.setAttribute("correctAnswer", correctAnswer);

        ScoreService scoreService = new ScoreService();

        if (selectedAnswer != null && selectedAnswer.equals(correctAnswer)) {
            try {
                scoreService.set(user.getId(), true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            request.setAttribute("message", "✅ Correcto!");
        } else {
            try {
                scoreService.set(user.getId(), false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            request.setAttribute("message", "❌ Incorrecto. La respuesta correcta era: " + correctAnswer);
        }

        try (Connection oConnection = HikariPool.getConnection()) {

            ScoreDao scoreDao = new ScoreDao(oConnection);
            ScoreDto userScore = scoreDao.get(user.getId());
            request.setAttribute("userScore", userScore);

            List<ScoreDto> highScores = scoreDao.getTop10();
            request.setAttribute("highScores", highScores);

            RequestDispatcher dispatcher = request.getRequestDispatcher("scores.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            request.setAttribute("errorMessage", "Database error");
            RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
            dispatcher.forward(request, response);
        }
    }
}
