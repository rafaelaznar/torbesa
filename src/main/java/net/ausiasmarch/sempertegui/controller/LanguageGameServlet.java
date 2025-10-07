package net.ausiasmarch.sempertegui.controller;

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

import net.ausiasmarch.sempertegui.dao.LanguageScoreDao;
import net.ausiasmarch.sempertegui.model.Language;
import net.ausiasmarch.sempertegui.model.LanguageScoreDto;
import net.ausiasmarch.sempertegui.service.LanguageScoreService;
import net.ausiasmarch.sempertegui.service.LanguageService;
import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.shared.model.UserBean;

@WebServlet("/languages/languageGameServlet")
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
        Language.setWord(languageService.getOneRandomWord());
        String translatedWord = LanguageService.translateWord(Language.getWord());
        List<String> randomWordsOptionsList = languageService.getRandomWordsOptionsList(Language.getWord(), 3);    

        request.setAttribute("word", translatedWord);        
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

            LanguageScoreService scoreService = new LanguageScoreService();
          //  String word = request.getParameter("word");
            String wordGuess = request.getParameter("wordGuess");

            if (wordGuess.equalsIgnoreCase(Language.getWord())) {

                scoreService.set(user.getId(), true);

                request.setAttribute("message", "Correct! Well done.");
            } else {
                request.setAttribute("message", "Incorrect. Try again!");
                scoreService.set(user.getId(), false);
            }

            try (Connection oConnection = HikariPool.getConnection()) {

                LanguageScoreDao oScoreDao = new LanguageScoreDao(oConnection);
                LanguageScoreDto userScore = oScoreDao.get(user.getId());
                request.setAttribute("userScore", userScore);

                List<LanguageScoreDto> highScores = oScoreDao.getTop10();
                request.setAttribute("highScores", highScores);

                request.getRequestDispatcher("languageScores.jsp").forward(request, response);

            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            request.setAttribute("errorMessage", "Database error");
            RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
            dispatcher.forward(request, response);
        }

    }
}
