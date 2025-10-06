package net.ausiasmarch.starwars.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import net.ausiasmarch.starwars.model.ScoreDto;

import java.io.IOException;
import java.util.List;

@WebServlet("/starwars/ScoreServlet")
public class ScoreServlet {
    private ScoreServlet oScoreService;

    public ScoreServlet() {
        this.oScoreService = new ScoreServlet();
    }

    // Constructor para inyección en tests
    public ScoreServlet(ScoreServlet scoreService) {
        this.oScoreService = scoreService;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)  {
        try {
            List<ScoreDto> highScoresList = oScoreService.getHighScores();
            request.setAttribute("highScores", highScoresList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("highscoresSW.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            System.err.println("Error al ejecutar la operación en la base de datos: " + e.getMessage());
            request.setAttribute("errorMessage", "Internal error");
            RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
            try {
                dispatcher.forward(request, response);
            } catch (ServletException | IOException e1) {
                System.err.println("Error al redirigir a la página de error: " + e1.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            request.setAttribute("errorMessage", e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
            try {
                dispatcher.forward(request, response);
            } catch (ServletException | IOException e1) {
                System.err.println("Error al redirigir a la página de error: " + e1.getMessage());
            }
        }
    }

    private List<ScoreDto> getHighScores() {
        throw new UnsupportedOperationException("Unimplemented method 'getHighScores'");
    }
}