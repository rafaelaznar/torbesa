package net.ausiasmarch.juegoSalinas.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import net.ausiasmarch.juegoSalinas.model.ScoreDto;
import net.ausiasmarch.juegoSalinas.service.ScoreService;

@WebServlet("/juegoSalinas/ScoreServlet")
public class ScoreServlet extends HttpServlet {

    private ScoreService oScoreService;

    public ScoreServlet() {
        this.oScoreService = new ScoreService();
    }

    // Constructor alternativo para inyección (tests)
    public ScoreServlet(ScoreService scoreService) {
        this.oScoreService = scoreService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Obtener los mejores puntajes
            List<ScoreDto> highScores = oScoreService.getTopScores(10);

            // Pasar datos a la vista
            request.setAttribute("scores", highScores);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/juegoSalinas/score.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            System.err.println("Error en base de datos: " + e.getMessage());
            handleError(request, response, "Database error");

        } catch (ServletException | IOException e) {
            System.err.println("Error interno del servlet: " + e.getMessage());
            handleError(request, response, "Internal server error");

        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            handleError(request, response, e.getMessage());
        }
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, String message) {
        try {
            request.setAttribute("errorMessage", message);
            RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
            dispatcher.forward(request, response);
        } catch (Exception ex) {
            System.err.println("Error al redirigir a la página de error: " + ex.getMessage());
        }
    }
}
