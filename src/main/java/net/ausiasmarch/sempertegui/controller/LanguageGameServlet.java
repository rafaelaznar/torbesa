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

@WebServlet("/sempertegui/languageGameServlet")
public class LanguageGameServlet extends HttpServlet {

    private static final int MAX_QUESTIONS = 5;

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
            //request.setAttribute("sessionUser", user); CUIDADO OBSERVAR
        }

        // Inicializa el contador y el score si es la primera pregunta
        if (session.getAttribute("questionCount") == null) {
            session.setAttribute("questionCount", 0);
            session.setAttribute("score", 0);
        }


        LanguageService languageService = new LanguageService();
        Language.setWord(languageService.getOneRandomWord());
        String translatedWord = LanguageService.translateWord(Language.getWord());
        List<String> randomWordsOptionsList = languageService.getRandomWordsOptionsList(Language.getWord(), 3);    

        request.setAttribute("word", translatedWord);        
        request.setAttribute("options", randomWordsOptionsList);
        request.setAttribute("score", session.getAttribute("score"));
        request.getRequestDispatcher("languageGame.jsp").forward(request, response);

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("sessionUser");
        if (user == null) {
            response.sendRedirect("../shared/login.jsp");
            return;
        } else {
            request.setAttribute("username", user.getUsername());
        }

        int questionCount = (int) session.getAttribute("questionCount");
        int score = (int) session.getAttribute("score");

        String wordGuess = request.getParameter("wordGuess");
        String correctWord = Language.getWord();

        request.setAttribute("word", request.getParameter("word"));
        request.setAttribute("wordGuess", wordGuess);
        request.setAttribute("correctWord", correctWord);

        LanguageScoreService scoreService = new LanguageScoreService();
        try {
            if (wordGuess.equalsIgnoreCase(correctWord)) {
                score++;
                scoreService.set(user.getId(), true);
                request.setAttribute("message", "Correct! Well done.");
            } else {
                scoreService.set(user.getId(), false);
                request.setAttribute("message", "Incorrect. Try again!");
            }

            questionCount++;
            session.setAttribute("questionCount", questionCount);
            session.setAttribute("score", score);
       
            if(questionCount >= MAX_QUESTIONS) {
                
                scoreService.set(user.getId()); // cambiar estoi
                try (Connection oConnection = HikariPool.getConnection()) {

                    LanguageScoreDao oScoreDao = new LanguageScoreDao(oConnection);
                    LanguageScoreDto userScore = oScoreDao.get(user.getId());
                    request.setAttribute("userScore", userScore);

                    List<LanguageScoreDto> highScores = oScoreDao.getTop10();
                    request.setAttribute("highScores", highScores);

                    request.setAttribute("numQuestions", MAX_QUESTIONS);
                    
                    //Limpia la sesión para un nuevo juego
                    session.removeAttribute("questionCount");
                    session.removeAttribute("score");

                    request.getRequestDispatcher("languageScores.jsp").forward(request, response);
                }
            } else {
                // Mostrar siguiente pregunta
                LanguageService languageService = new LanguageService();
                Language.setWord(languageService.getOneRandomWord());
                String translatedWord = LanguageService.translateWord(Language.getWord());
                List<String> randomWordsOptionsList = languageService.getRandomWordsOptionsList(Language.getWord(), 3);

                request.setAttribute("word", translatedWord);
                request.setAttribute("options", randomWordsOptionsList);
                request.setAttribute("score", score);
                request.getRequestDispatcher("languageGame.jsp").forward(request, response);
            }
        } catch(SQLException e){
            request.setAttribute("errorMessage", "Database error");
            RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
            dispatcher.forward(request, response);
        }
    }
}
