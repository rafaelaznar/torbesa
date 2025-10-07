package net.ausiasmarch.genshinPav.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ausiasmarch.genshinPav.dao.ScoreDao;
import net.ausiasmarch.genshinPav.model.GenshinBean;
import net.ausiasmarch.genshinPav.model.ScoreDto;
import net.ausiasmarch.genshinPav.service.GenshinService;
import net.ausiasmarch.genshinPav.service.ScoreService;
import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.shared.model.UserBean;

@WebServlet("/genshinPav/GameServlet")  //Cambiarlo siempre que cambie el nombre del proyecto
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

        GenshinService oElementService = new GenshinService(request.getServletContext());
        GenshinBean selectedCharacter = oElementService.getOneRandomElement();
        ArrayList<String> visionOptions = oElementService.getRandomgenshinForTest(selectedCharacter, 4);
        request.setAttribute("characterName", selectedCharacter.getName());
        request.setAttribute("options", visionOptions);
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
            String characterName = request.getParameter("characterName");
            String visionGuess = request.getParameter("visionGuess");
            GenshinService oElementService = new GenshinService(request.getServletContext());
            String correctVision = oElementService.fetchAllCharacters().stream()
                    .filter(c -> c.getName().equals(characterName))
                    .map(GenshinBean::getVision)
                    .findFirst()
                    .orElse("");
            request.setAttribute("characterName", characterName);
            request.setAttribute("correctVision", correctVision);
            request.setAttribute("visionGuess", visionGuess);
            if (visionGuess != null && visionGuess.equals(correctVision)) {
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
