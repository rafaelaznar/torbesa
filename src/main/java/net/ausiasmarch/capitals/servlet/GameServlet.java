package net.ausiasmarch.capitals.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import net.ausiasmarch.capitals.model.Country;
import net.ausiasmarch.capitals.model.Score;
import net.ausiasmarch.capitals.model.User;
import net.ausiasmarch.capitals.service.CountryService;
import net.ausiasmarch.capitals.service.ScoreService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet("/capitals/GameServlet")
public class GameServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("sessionUser");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        } else {
            request.setAttribute("sessionUser", user);
        }

        List<Country> countries = CountryService.getInstance().fetchAllCountries();

        ArrayList<String> options = new ArrayList<>();

        // fist select one country from the list of all countries
        int randomIndex0 = (int) (Math.random() * countries.size());
        Country selectedCountry = countries.get(randomIndex0);
        // while selectedCountry has no capital select another
        while (selectedCountry.getCapital().trim().isEmpty()) {
            randomIndex0 = (int) (Math.random() * countries.size());
            selectedCountry = countries.get(randomIndex0);
        }
        options.add(selectedCountry.getCapital());
        // Choose three random countries from the list to be the options
        // ensure the country is not the right country
        for (int i = 0; i < 3; i++) {
            int randomIndex = 0;
            while (randomIndex == 0) {
                randomIndex = (int) (Math.random() * countries.size());
                if (countries.get(randomIndex).getCapital().trim().isEmpty()) {
                    randomIndex = 0;
                } else {
                    if (options.contains(countries.get(randomIndex).getCapital())) {
                        randomIndex = 0;
                    }
                }
            }
            options.add(countries.get(randomIndex).getCapital());
        }
        request.setAttribute("country", selectedCountry.getName());
        Collections.shuffle(options);
        request.setAttribute("options", options);
        RequestDispatcher dispatcher = request.getRequestDispatcher("game.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("sessionUser");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        } else {
            request.setAttribute("username", user.getUsername());
        }

        ScoreService scoreService = new ScoreService();
        String country = request.getParameter("country");
        String capitalGuess = request.getParameter("capitalGuess");

        // get all countries from CountryService
        String correctCapital = CountryService.getInstance().fetchAllCountries().stream()
                .filter(c -> c.getName().equals(country))
                .map(Country::getCapital)
                .findFirst()
                .orElse("");
        request.setAttribute("country", country);
        request.setAttribute("correctCapital", correctCapital);
        request.setAttribute("capitalGuess", capitalGuess);
        if (capitalGuess.equals(correctCapital)) {
            scoreService.updateScore(user.getId(), true);
            request.setAttribute("message", "Correct! Well done.");
        } else {
            request.setAttribute("message", "Incorrect. Try again!");
            scoreService.updateScore(user.getId(), false);
        }

        // get user scores from ScoreService

        Score userScore = scoreService.getScoreByUser(user.getId());
        request.setAttribute("userScore", userScore);

        // get high scores from ScoreService
        List<Score> highScores = scoreService.getHighScores();
        request.setAttribute("highScores", highScores);

        RequestDispatcher dispatcher = request.getRequestDispatcher("scores.jsp");
        dispatcher.forward(request, response);
    }
}
