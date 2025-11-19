package net.ausiasmarch.juegoSalinas.controller;

import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import net.ausiasmarch.juegoSalinas.model.JuegoSalinasBean;
import net.ausiasmarch.juegoSalinas.service.GameService;
import net.ausiasmarch.juegoSalinas.service.ScoreService;
import net.ausiasmarch.shared.model.UserBean;

@WebServlet("/juegoSalinas/GameServlet")
public class GameServlet extends HttpServlet {

    private final GameService gameService = new GameService();
    private final ScoreService scoreService = new ScoreService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("sessionUser");
        if (user == null) {
            response.sendRedirect("../shared/login.jsp");
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/juegoSalinas/landing.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("sessionUser");
        if (user == null) {
            response.sendRedirect("../shared/login.jsp");
            return;
        }

        String userChoice = request.getParameter("choice");
        JuegoSalinasBean game = gameService.play(userChoice);

        if ("¡Ganaste!".equals(game.getResult())) {
            try {
                scoreService.addScore(user.getUsername(), 1);
            } catch (SQLException e) {
                request.setAttribute("errorMessage", "Database error");
                RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
                dispatcher.forward(request, response);
                return;
            }
        }

        request.setAttribute("game", game);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/juegoSalinas/result.jsp");
        dispatcher.forward(request, response);
    }
}



