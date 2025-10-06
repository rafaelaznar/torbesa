package net.ausiasmarch.genshin.controller;

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

import net.ausiasmarch.genshin.dao.ScoreDao;
import net.ausiasmarch.genshin.model.GenshinBean;
import net.ausiasmarch.genshin.model.ScoreDto;
import net.ausiasmarch.genshin.service.GenshinService;
import net.ausiasmarch.genshin.service.ScoreService;
import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.shared.model.UserBean;

@WebServlet("/genshin/GameServlet")
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
        GenshinBean selectedElement = oElementService.getOneRandomElement();
        ArrayList<String> optionsListForElementTest = oElementService.getRandomgenshinForTest(selectedElement, 3);        
        request.setAttribute("Element", selectedElement.getName());        
        request.setAttribute("options", optionsListForElementTest);
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
            String element = request.getParameter("element");
            String elementGuess = request.getParameter("elementGuess");
            GenshinService oElementService = new GenshinService(request.getServletContext());
            String correctElement = oElementService.fetchAllCharacters().stream()
                    .filter(c -> c.getName().equals(element))
                    .map(GenshinBean::getElement)
                    .findFirst()
                    .orElse("");
            request.setAttribute("element", element);
            request.setAttribute("correctElement", correctElement);
            request.setAttribute("elementGuess", elementGuess);
            if (elementGuess.equals(correctElement)) {

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
