package net.ausiasmarch.capitals.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import net.ausiasmarch.capitals.dao.ScoreDao;
import net.ausiasmarch.capitals.model.CountryBean;
import net.ausiasmarch.capitals.model.ScoreDto;
import net.ausiasmarch.capitals.service.CountryService;
import net.ausiasmarch.capitals.service.ScoreService;
import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.shared.model.UserBean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet("/capitals/GameServlet")
public class GameServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("sessionUser");
        if (user == null) {
            response.sendRedirect("../shared/login.jsp");
            return;
        } else {
            request.setAttribute("sessionUser", user);
        }

        CountryService oCountryService = new CountryService(request.getServletContext());
        List<CountryBean> countries = oCountryService.fetchAllCountries();

        ArrayList<String> options = new ArrayList<>();

        // fist select one country from the list of all countries
        int randomIndex0 = (int) (Math.random() * countries.size());
        CountryBean selectedCountry = countries.get(randomIndex0);
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
            CountryService oCountryService = new CountryService(request.getServletContext());
            String correctCapital = oCountryService.fetchAllCountries().stream()
                    .filter(c -> c.getName().equals(country))
                    .map(CountryBean::getCapital)
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

            HikariPool oPool = new HikariPool();
            try (Connection oConnection = oPool.getConnection()) {

                ScoreDao oScoreDao = new ScoreDao(oConnection);
                ScoreDto userScore = oScoreDao.get(user.getId());
                request.setAttribute("userScore", userScore);

                List<ScoreDto> highScores = oScoreDao.getTop10();
                request.setAttribute("highScores", highScores);

                RequestDispatcher dispatcher = request.getRequestDispatcher("scores.jsp");
                dispatcher.forward(request, response);

            } finally {
                oPool.disposeConnection();
            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            request.setAttribute("errorMessage", "Database error");
            RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
            dispatcher.forward(request, response);
        }

    }
}
