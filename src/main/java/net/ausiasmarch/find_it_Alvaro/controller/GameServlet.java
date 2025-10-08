package net.ausiasmarch.find_it_Alvaro.controller;

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

import net.ausiasmarch.find_it_Alvaro.dao.ScoreDao;
import net.ausiasmarch.find_it_Alvaro.model.CharacterBean;
import net.ausiasmarch.find_it_Alvaro.model.ScoreDto;
import net.ausiasmarch.find_it_Alvaro.service.CharacterService;
import net.ausiasmarch.find_it_Alvaro.service.ScoreService;
import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.shared.model.UserBean;

@WebServlet("/find_it_Alvaro/GameServlet")
public class GameServlet extends HttpServlet {

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

        CharacterService characterService = new CharacterService(request.getServletContext());
        CharacterBean selectedCharacter = characterService.getOneRandomCharacter();
        List<CharacterBean> optionsListForCharacterTest = characterService.getRandomCharactersForTest(selectedCharacter, 4);

        request.setAttribute("selectedCharacter", selectedCharacter);
        request.setAttribute("options", optionsListForCharacterTest);

    RequestDispatcher dispatcher = request.getRequestDispatcher("/find_it_alvaro/game.jsp");
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

            ScoreService scoreService = new ScoreService();
            String characterName = request.getParameter("characterName");
            String elementGuess = request.getParameter("elementGuess");

            CharacterService oCharacterService = new CharacterService(request.getServletContext());
            String correctElement = oCharacterService.fetchAllCharacters().stream()
                    .filter(c -> c.getName().equals(characterName))
                    .map(CharacterBean::getElement)
                    .findFirst()
                    .orElse("");

            request.setAttribute("characterName", characterName);
            request.setAttribute("correctElement", correctElement);
            request.setAttribute("elementGuess", elementGuess);

            if (elementGuess.equalsIgnoreCase(correctElement)) {
                scoreService.set(user.getId(), true);
                request.setAttribute("message", "¡Correcto! Bien hecho.");
            } else {
                scoreService.set(user.getId(), false);
                request.setAttribute("message", "Incorrecto. ¡Inténtalo otra vez!");
            }

            try (Connection oConnection = HikariPool.getConnection()) {
                ScoreDao oScoreDao = new ScoreDao(oConnection);
                ScoreDto userScore = oScoreDao.get(user.getId());
                request.setAttribute("userScore", userScore);

                List<ScoreDto> highScores = oScoreDao.getTop10();
                request.setAttribute("highScores", highScores);

                RequestDispatcher dispatcher = request.getRequestDispatcher("/find_it_alvaro/scores.jsp");
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
