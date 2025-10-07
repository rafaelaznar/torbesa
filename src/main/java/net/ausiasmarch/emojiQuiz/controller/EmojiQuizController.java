package net.ausiasmarch.emojiQuiz.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import net.ausiasmarch.emojiQuiz.dao.EmojiScoreDao;
import net.ausiasmarch.emojiQuiz.service.EmojiScoreService;
import net.ausiasmarch.emojiQuiz.model.EmojiQuizBean;
import net.ausiasmarch.emojiQuiz.service.EmojiQuizService;
import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.shared.model.UserBean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/emojiQuiz/play")
public class EmojiQuizController extends HttpServlet {

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

        EmojiQuizService eqService = new EmojiQuizService();
        try {
            EmojiQuizBean getRandomQuestion = eqService.getRandomQuestion();
            List<String> options = eqService.getShuffledOptions(getRandomQuestion);
            request.setAttribute("question", getRandomQuestion);
            request.setAttribute("options", options);
            request.getRequestDispatcher("/emojiQuiz/game.jsp").forward(request, response);
        } catch (SQLException e) {
            System.err.println("Error al obtener la pregunta aleatoria: " + e.getMessage());
        } catch (ServletException e) {
            System.err.println("Error al redirigir a la página de juego: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        try {
            HttpSession session = request.getSession();
            UserBean user = (UserBean) session.getAttribute("sessionUser");
            if (user == null) {
                response.sendRedirect("../shared/login.jsp");
                return;
            }
            request.setAttribute("username", user.getUsername());

            String selectedOption = request.getParameter("selectedOption");
            String qid = request.getParameter("questionId");
            if (selectedOption == null || qid == null) {
                request.setAttribute("message", "Choose an option and try again.");
                request.getRequestDispatcher("/emojiQuiz/game.jsp").forward(request, response);
                return;
            }

            long questionId = Long.parseLong(qid);
            EmojiQuizService eqService = new EmojiQuizService();
            EmojiQuizBean question = eqService.getQuestionById(questionId);
            String correctAnswer = question.getCorrectAnswer();

            boolean isCorrect = selectedOption.trim().equalsIgnoreCase(correctAnswer.trim());

            EmojiScoreService scoreService = new EmojiScoreService();
            scoreService.set(user.getId(), isCorrect);

            request.setAttribute("message", isCorrect ? "Correct! Well done." : "Incorrect. Try again!");
            request.setAttribute("question", question);
            request.setAttribute("selectedOption", selectedOption);
            request.setAttribute("correctAnswer", correctAnswer);

            try (Connection con = HikariPool.getConnection()) {
                EmojiScoreDao dao = new EmojiScoreDao(con);
                request.setAttribute("userScore", dao.get(user.getId()));
                request.setAttribute("highScores", dao.getTop10());
            }

            request.getRequestDispatcher("/emojiQuiz/scores.jsp").forward(request, response);

        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error");
            request.getRequestDispatcher("../shared/error.jsp").forward(request, response);
        }    
    }
}