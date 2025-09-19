package net.ausiasmarch.capitals.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import net.ausiasmarch.capitals.model.ScoreDto;
import net.ausiasmarch.capitals.service.ScoreService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/capitals/ScoreServlet")
public class ScoreServlet extends HttpServlet {
    private ScoreService scoreService;

    public ScoreServlet() {
        this.scoreService = new ScoreService();
    }

    // Constructor para inyección en tests
    public ScoreServlet(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<ScoreDto> highScoresList = scoreService.getHighScores();
            request.setAttribute("highScores", highScoresList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("highscores.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la operación en la base de datos: " + e.getMessage());
            request.setAttribute("errorMessage", "Database error");
            RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            System.err.println("Error al ejecutar la operación en la base de datos: " + e.getMessage());
            request.setAttribute("errorMessage", "Internal error");
            RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            request.setAttribute("errorMessage", e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
            dispatcher.forward(request, response);
        }
    }
}
