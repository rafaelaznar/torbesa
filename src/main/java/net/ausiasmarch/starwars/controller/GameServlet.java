package net.ausiasmarch.starwars.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import net.ausiasmarch.starwars.dao.ScoreDao;
import net.ausiasmarch.starwars.model.CharacterBean;
import net.ausiasmarch.starwars.model.ScoreDto;
import net.ausiasmarch.starwars.service.CharacterService;
import net.ausiasmarch.starwars.service.ScoreService;
import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.shared.model.UserBean;

import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@WebServlet("/starwars/GameServlet")
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

        CharacterService oCharacterService = new CharacterService(request.getServletContext());
        CharacterBean selectedCharacter = oCharacterService.getOneRandomCharacter();
        
        String correctSpecies = selectedCharacter.getSpecies();
        List<String> allSpecies = oCharacterService.fetchAllSpeciesNames();
        ArrayList<String> optionsList = new ArrayList<>();
        optionsList.add(correctSpecies);

        int requiredOptions = 3;
        Random random = new Random();

        while (optionsList.size() < requiredOptions) {
            int randomIndex = random.nextInt(allSpecies.size());
            String randomSpecies = allSpecies.get(randomIndex);

            if (!optionsList.contains(randomSpecies)) {
                optionsList.add(randomSpecies);
            }
        }

        Collections.shuffle(optionsList);

        request.setAttribute("character", selectedCharacter);      
        request.setAttribute("options", optionsList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("gameSW.jsp");
        
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
            String characterGuess = request.getParameter("characterGuess");
            String correctCharacterName = request.getParameter("correctCharacterName");
            request.setAttribute("character", correctCharacterName);
            request.setAttribute("correctCharacter", correctCharacterName);
            request.setAttribute("characterGuess", characterGuess);
            if (correctCharacterName != null && correctCharacterName.equals(characterGuess)) {
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

                RequestDispatcher dispatcher = request.getRequestDispatcher("scoresSW.jsp");
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