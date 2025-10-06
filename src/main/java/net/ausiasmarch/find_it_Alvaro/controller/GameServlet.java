package net.ausiasmarch.find_it_Alvaro.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import net.ausiasmarch.find_it_Alvaro.dao.ScoreDao;
import net.ausiasmarch.find_it_Alvaro.model.CharacterBean;
import net.ausiasmarch.find_it_Alvaro.model.ScoreDto;
import net.ausiasmarch.find_it_Alvaro.service.CharacterService;
import net.ausiasmarch.find_it_Alvaro.service.ScoreService;
import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.shared.model.UserBean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

        CharacterService oCharacterService = new CharacterService(request.getServletContext()); //Cambiar por CharacterService
        CountryBean selectedCountry = oCharacterService.getOneRandomCountry(); //Cambiar por CharacterBean y getOneRandomCharacter
        ArrayList<String> optionsListForCapitalTest = oCharacterService.getRandomCapitalsForTest(selectedCountry, 3);        
        request.setAttribute("country", selectedCountry.getName());         //Cambiar por character y getName por getSomething
        request.setAttribute("options", optionsListForCapitalTest);       //Cambiar por optionsListForSomethingTest
        RequestDispatcher dispatcher = request.getRequestDispatcher("game.jsp"); //Esto tengo que tocarlo? creo que no
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
            String country = request.getParameter("country");
            String capitalGuess = request.getParameter("capitalGuess");
            CharacterService oCharacterService = new CharacterService(request.getServletContext()); //Cambiar por CharacterService
             //CharacterService oCharacterService = new CharacterService(request.getServletContext());
            String correctCapital = oCharacterService.fetchAllCharacters()().stream()
                    .filter(c -> c.getName().equals(country))
                    .map(CharacterBean::getCapital)
                    .findFirst()
                    .orElse("");
            request.setAttribute("country", country);
            request.setAttribute("correctCapital", correctCapital);
            request.setAttribute("capitalGuess", capitalGuess);
            if (capitalGuess.equals(correctCapital)) {

                scoreService.set(user.getId(), true);

                request.setAttribute("message", "Correct! Well done.");
            } else {
                request.setAttribute("message", "Incorrect. Try again!");
                scoreService.set(user.getId(), false);
            }

            try (Connection oConnection = HikariPool.getConnection()) {

                ScoreDao oScoreDao = new ScoreDao(oConnection);
                ScoreDto userScore = oScoreDao.get(user.getId());
                request.setAttribute("userScore", userScore);

                List<ScoreDto> highScores = oScoreDao.getTop10();
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
