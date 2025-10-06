package net.ausiasmarch.pokemon.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import net.ausiasmarch.pokemon.dao.ScoreDao;
import net.ausiasmarch.pokemon.model.Pokemon;
import net.ausiasmarch.pokemon.model.ScoreDto;
import net.ausiasmarch.pokemon.service.PokemonService;
import net.ausiasmarch.pokemon.service.ScoreService;
import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.shared.model.UserBean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.List;

@WebServlet("/pokemon/ServletJuego")
public class ServletJuego extends HttpServlet {

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

        PokemonService oPokemonService = new PokemonService(request.getServletContext());
        Pokemon selectedPokemon = oPokemonService.getOneRandomPokemonByName();
        ArrayList<String> optionsListForTypeTest = oPokemonService.getRandomNamesForTest(selectedPokemon, 3);
        if (selectedPokemon != null) {
            request.setAttribute("pokemon", selectedPokemon.getName());
        } else {
            request.setAttribute("pokemon", "");
        }
        request.setAttribute("options", optionsListForTypeTest);
        RequestDispatcher dispatcher = request.getRequestDispatcher("juego.jsp");
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
            String pokemon = request.getParameter("pokemon");
            String nameGuess = request.getParameter("typeGuess");
            PokemonService oPokemonService = new PokemonService(request.getServletContext());
            String correctName = oPokemonService.fetchAllPokemonList().stream()
                    .filter(c -> c.getName().equalsIgnoreCase(pokemon))
                    .map(Pokemon::getName)
                    .findFirst()
                    .orElse("");
            request.setAttribute("pokemon", pokemon);
            request.setAttribute("correctType", correctName);
            request.setAttribute("typeGuess", nameGuess);
            if (Objects.equals(nameGuess, correctName)) {
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

                RequestDispatcher dispatcher = request.getRequestDispatcher("puntajes.jsp");
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
