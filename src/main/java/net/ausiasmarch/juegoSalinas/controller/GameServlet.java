package net.ausiasmarch.juegoSalinas.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import net.ausiasmarch.juegoSalinas.model.JuegoSalinasBean;
import net.ausiasmarch.juegoSalinas.model.ScoreDto;
import net.ausiasmarch.juegoSalinas.service.GameService;
import net.ausiasmarch.juegoSalinas.service.ScoreService;

@WebServlet("/juegoSalinas/GameServlet")
public class GameServlet extends HttpServlet {
    private final GameService gameService = new GameService();
    private final ScoreService scoreService = new ScoreService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userChoice = request.getParameter("choice");
        JuegoSalinasBean game = gameService.play(userChoice);

        // Guardar puntuación si gana
        if ("¡Ganaste!".equals(game.getResult())) {
            String username = (String) request.getSession().getAttribute("sessionUser");
            if (username != null) {
                scoreService.addScore(new ScoreDto(username, 1));
            }
        }

        request.setAttribute("game", game);
        request.getRequestDispatcher("/juegoSalinas/result.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/juegoSalinas/landing.jsp").forward(request, response);
    }
}