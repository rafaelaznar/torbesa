package net.ausiasmarch.f1.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ausiasmarch.f1.model.DriverBean;
import net.ausiasmarch.f1.service.F1Service;
import net.ausiasmarch.f1.service.F1ScoreService;
import net.ausiasmarch.shared.model.UserBean;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/f1/GameServlet")
public class F1GameServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("sessionUser");
        if (user == null) {
            response.sendRedirect("../shared/login.jsp");
            return;
        } else {
            request.setAttribute("sessionUser", user);
        }

    F1Service f1Service = new F1Service(request.getServletContext());
    // pick a random driver for this round by querying driver_number randomly
    DriverBean selectedDriver = f1Service.fetchRandomDriver();
        String driverName = selectedDriver.getName();
        String correctTeam = selectedDriver.getTeam();
        request.setAttribute("driverName", driverName);

        // Build options: correct team + 2 unique random distractor teams fetched by querying random driver numbers
        java.util.List<String> options = new java.util.ArrayList<>();
        options.add(correctTeam);

        java.util.Set<String> distractors = new java.util.LinkedHashSet<>();
        int attempts = 0;
        while (distractors.size() < 2 && attempts < 20) {
            int rnd = 1 + (int) (Math.random() * 99);
            // avoid picking the same driver_number that might correspond to selectedDriver (best-effort)
            net.ausiasmarch.f1.model.DriverBean d = f1Service.fetchDriverByNumber(rnd);
            if (d != null) {
                String team = d.getTeam();
                if (team != null && !team.isEmpty() && !team.equals(correctTeam)) {
                    distractors.add(team);
                }
            }
            attempts++;
        }

        options.addAll(distractors);

        // if we still have fewer than 3 options, try filling from cached teams
        if (options.size() < 3) {
            for (String t : f1Service.getDistinctTeamsFromCache()) {
                if (options.size() >= 3) break;
                if (t != null && !t.isEmpty() && !t.equals(correctTeam) && !options.contains(t)) {
                    options.add(t);
                }
            }
        }

        // ensure exactly 3 options
        while (options.size() < 3) options.add("");

        // shuffle so correct answer appears in random position
        java.util.Collections.shuffle(options);

        request.setAttribute("options", options.toArray(new String[0]));
        // also fetch user's score and top scores to display leaderboard like capitals game
        try {
            if (user != null) {
                try (java.sql.Connection oConnection = net.ausiasmarch.shared.connection.HikariPool.getConnection()) {
                    net.ausiasmarch.f1.dao.F1ScoreDao oScoreDao = new net.ausiasmarch.f1.dao.F1ScoreDao(oConnection);
                    net.ausiasmarch.capitals.model.ScoreDto userScore = oScoreDao.get(user.getId());
                    request.setAttribute("userScore", userScore);

                    java.util.List<net.ausiasmarch.capitals.model.ScoreDto> highScores = oScoreDao.getTop10();
                    request.setAttribute("highScores", highScores);
                }
            }
        } catch (java.sql.SQLException e) {
            request.setAttribute("leaderboardError", "Could not load leaderboard");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/f1/game.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("sessionUser");
        if (user == null) {
            response.sendRedirect("../shared/login.jsp");
            return;
        }

        String driverName = request.getParameter("driverName");
        String teamGuess = request.getParameter("teamGuess");

        F1Service f1Service = new F1Service(request.getServletContext());
        // Try to fetch canonical driver info by full name from API (with cache fallback)
        String correctTeam = "";
        net.ausiasmarch.f1.model.DriverBean canonical = f1Service.fetchDriverByFullName(driverName);
        if (canonical != null && canonical.getTeam() != null) {
            correctTeam = canonical.getTeam();
        } else {
            correctTeam = f1Service.fetchDrivers().stream().filter(d -> d.getName().equals(driverName)).map(d -> d.getTeam()).findFirst().orElse("");
        }

        request.setAttribute("driverName", driverName);
        request.setAttribute("teamGuess", teamGuess);
        request.setAttribute("correctTeam", correctTeam);

        try {
            F1ScoreService scoreService = new F1ScoreService();
            if (teamGuess.equals(correctTeam)) {
                scoreService.set(user.getId(), true);
                request.setAttribute("message", "Correct! Well done.");
            } else {
                scoreService.set(user.getId(), false);
                request.setAttribute("message", "Incorrect. Try again!");
            }
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error");
            request.getRequestDispatcher("../shared/error.jsp").forward(request, response);
            return;
        }

        // After updating score, fetch userScore and highScores to show on results page
        try {
            try (java.sql.Connection oConnection = net.ausiasmarch.shared.connection.HikariPool.getConnection()) {
                net.ausiasmarch.f1.dao.F1ScoreDao oScoreDao = new net.ausiasmarch.f1.dao.F1ScoreDao(oConnection);
                net.ausiasmarch.capitals.model.ScoreDto userScore = oScoreDao.get(user.getId());
                request.setAttribute("userScore", userScore);

                java.util.List<net.ausiasmarch.capitals.model.ScoreDto> highScores = oScoreDao.getTop10();
                request.setAttribute("highScores", highScores);
            }
        } catch (java.sql.SQLException e) {
            request.setAttribute("leaderboardError", "Could not load leaderboard");
        }

        request.getRequestDispatcher("/f1/scores.jsp").forward(request, response);
    }
}
