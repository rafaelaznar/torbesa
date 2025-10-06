package net.ausiasmarch.languages.controller;

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

import net.ausiasmarch.languages.dao.ScoreDAO;
import net.ausiasmarch.languages.model.ScoreDTO;
import net.ausiasmarch.languages.service.LanguageService;
import net.ausiasmarch.languages.service.ScoreService;
import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.shared.model.UserBean;

@WebServlet("/language/GameServlet")
public class LanguageGameServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

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

        LanguageService languageService = new LanguageService();
        String selectedWord = languageService.getOneRandomWord();
        String translatedSelectedWord = LanguageService.translateWord(selectedWord);
        List<String> randomWordsOptionsList = languageService.getRandomWordsOptionsList(translatedSelectedWord, 3);    


        request.setAttribute("word", translatedSelectedWord);        
        request.setAttribute("options", randomWordsOptionsList);
        request.getRequestDispatcher("languageGame.jsp").forward(request, response);

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            HttpSession session = request.getSession();
            UserBean user = (UserBean) session.getAttribute("sessionUser");
            if (user == null) {
                response.sendRedirect("../shared/login.jsp");
                return;
            } else {
                request.setAttribute("username", user.getUsername());
            }

            ScoreService scoreService = new ScoreService();
            String word = request.getParameter("word");
            String wordGuess = request.getParameter("wordGuess");
            String correctWord = LanguageService.dictionary.stream()
                    .filter(w -> w.equals(word))
                    .findFirst()
                    .orElse("");
            request.setAttribute("word", word);
            request.setAttribute("correctWord", correctWord);
            request.setAttribute("wordGuess", wordGuess);
            if (wordGuess.equals(correctWord)) {

                scoreService.set(user.getId(), true);

                request.setAttribute("message", "Correct! Well done.");
            } else {
                request.setAttribute("message", "Incorrect. Try again!");
                scoreService.set(user.getId(), false);
            }

            try (Connection oConnection = HikariPool.getConnection()) {

                ScoreDAO oScoreDao = new ScoreDAO(oConnection);
                ScoreDTO userScore = oScoreDao.get(user.getId());
                request.setAttribute("userScore", userScore);

                List<ScoreDTO> highScores = oScoreDao.getTop10();
                request.setAttribute("highScores", highScores);

                RequestDispatcher dispatcher = request.getRequestDispatcher("scores.jsp");
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
