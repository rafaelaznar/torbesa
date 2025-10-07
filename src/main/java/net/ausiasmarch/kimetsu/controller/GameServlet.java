package net.ausiasmarch.kimetsu.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import net.ausiasmarch.kimetsu.dao.ScoreDao;
import net.ausiasmarch.kimetsu.model.ScoreDto;
import net.ausiasmarch.kimetsu.service.ScoreService;
import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.shared.model.UserBean;
import net.ausiasmarch.kimetsu.model.KimetsuBean;
import net.ausiasmarch.kimetsu.service.KimetsuService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet("/kimetsu/GameServlet")
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

        KimetsuService kimetsuService = new KimetsuService(request.getServletContext());
        KimetsuBean selectedKimetsu = kimetsuService.getOneRandomKimetsu();
        ArrayList<String> optionsListForKimetsuTest = kimetsuService.getRandomKimetsuOptionsForTest(selectedKimetsu, 3);
        request.setAttribute("kimetsu", selectedKimetsu.getName());
        request.setAttribute("options", optionsListForKimetsuTest);
        RequestDispatcher dispatcher = request.getRequestDispatcher("game.jsp");
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
            String kimetsu = request.getParameter("kimetsu");
            String kimetsuGuess = request.getParameter("kimetsuGuess");
            KimetsuService kimetsuService = new KimetsuService(request.getServletContext());
            String correctKimetsu = kimetsuService.fetchAllKimetsu().stream()
                    .filter(k -> k.getName().equals(kimetsu))
                    .map(KimetsuBean::getCorrect)
                    .findFirst()
                    .orElse("");
            request.setAttribute("kimetsu", kimetsu);
            request.setAttribute("correctKimetsu", correctKimetsu);
            request.setAttribute("kimetsuGuess", kimetsuGuess);
            if (kimetsuGuess.equals(correctKimetsu)) {
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
// NOTA: Debes crear las clases KimetsuService y KimetsuBean para que esto funcione correctamente.
