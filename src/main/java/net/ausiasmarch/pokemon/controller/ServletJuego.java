package net.ausiasmarch.pokemon.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ausiasmarch.pokemon.dao.ScoreDao;
import net.ausiasmarch.pokemon.model.Pokemon;
import net.ausiasmarch.pokemon.model.ScoreDto;
import net.ausiasmarch.pokemon.service.PokemonService;
import net.ausiasmarch.pokemon.service.ScoreService;
import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.shared.model.UserBean;

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
        
        if (selectedPokemon != null) {
            // Fetch detailed Pokemon info including abilities
            Pokemon detailedPokemon = oPokemonService.fetchPokemonDetails(selectedPokemon.getId());
            if (detailedPokemon != null && detailedPokemon.getAbilities() != null && !detailedPokemon.getAbilities().isEmpty()) {
                request.setAttribute("pokemonName", detailedPokemon.getName());
                request.setAttribute("pokemonId", detailedPokemon.getId());
                
                // Generate options with one correct ability and random incorrect ones
                ArrayList<String> optionsListForAbilityTest = getRandomAbilitiesForTest(oPokemonService, detailedPokemon, 4);
                request.setAttribute("options", optionsListForAbilityTest);
                
                // Store correct ability for verification
                session.setAttribute("correctAbility", detailedPokemon.getAbilities().get(0));
            } else {
                request.setAttribute("pokemonName", "");
                request.setAttribute("options", new ArrayList<String>());
            }
        } else {
            request.setAttribute("pokemonName", "");
            request.setAttribute("options", new ArrayList<String>());
        }

        // Get current user score
        try (Connection oConnection = HikariPool.getConnection()) {
            ScoreDao oScoreDao = new ScoreDao(oConnection);
            ScoreDto userScore = oScoreDao.get(user.getId());
            request.setAttribute("score", userScore != null ? userScore.getScore() : 0);
        } catch (SQLException e) {
            System.err.println("Database error getting score: " + e.getMessage());
            request.setAttribute("score", 0);
        }

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
            String pokemonName = request.getParameter("pokemonName");
            String abilityGuess = request.getParameter("abilityGuess");
            
            // Get the correct ability from session
            String correctAbility = (String) session.getAttribute("correctAbility");
            
            request.setAttribute("pokemonName", pokemonName);
            request.setAttribute("correctAbility", correctAbility);
            request.setAttribute("abilityGuess", abilityGuess);
            
            if (abilityGuess != null && correctAbility != null && abilityGuess.equalsIgnoreCase(correctAbility)) {
                scoreService.set(user.getId(), true);
                request.setAttribute("message", "Correct! Well done.");
            } else {
                request.setAttribute("message", "Incorrect. The correct ability was: " + correctAbility);
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

    /**
     * Generate a list of ability options including one correct ability and random incorrect ones
     */
    private ArrayList<String> getRandomAbilitiesForTest(PokemonService pokemonService, Pokemon correctPokemon, int numOptions) {
        ArrayList<String> abilities = new ArrayList<>();
        
        // Add the correct ability (first one from the pokemon's abilities)
        if (correctPokemon.getAbilities() != null && !correctPokemon.getAbilities().isEmpty()) {
            abilities.add(correctPokemon.getAbilities().get(0));
        }
        
        // Get all pokemon list to extract other abilities
        List<Pokemon> allPokemon = pokemonService.fetchAllPokemonList();
        int attempts = 0;
        
        while (abilities.size() < numOptions && attempts < numOptions * 10) {
            attempts++;
            // Get a random pokemon
            int randomIndex = (int) (Math.random() * allPokemon.size());
            Pokemon randomPokemon = allPokemon.get(randomIndex);
            
            // Fetch its details to get abilities
            Pokemon detailedRandomPokemon = pokemonService.fetchPokemonDetails(randomPokemon.getId());
            if (detailedRandomPokemon != null && detailedRandomPokemon.getAbilities() != null 
                && !detailedRandomPokemon.getAbilities().isEmpty()) {
                
                String randomAbility = detailedRandomPokemon.getAbilities().get(0);
                if (!abilities.contains(randomAbility)) {
                    abilities.add(randomAbility);
                }
            }
        }
        
        // Shuffle the list so the correct answer isn't always first
        Collections.shuffle(abilities);
        return abilities;
    }
}
