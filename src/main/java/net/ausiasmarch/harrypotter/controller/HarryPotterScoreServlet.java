package net.ausiasmarch.harrypotter.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ausiasmarch.harrypotter.model.HarryPotterScoreBean;
import net.ausiasmarch.harrypotter.service.ScoreService;

@WebServlet("/harrypotter/ScoreServlet")
public class HarryPotterScoreServlet extends HttpServlet {

    private final ScoreService scoreService;

    public HarryPotterScoreServlet() {
        this.scoreService = new ScoreService();
    }

    public HarryPotterScoreServlet(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            System.out.println("HarryPotterScoreServlet: Starting doGet()");
            List<HarryPotterScoreBean> highScoresList = scoreService.getHighScores();
            System.out.println("HarryPotterScoreServlet: Retrieved " + 
                (highScoresList != null ? highScoresList.size() : "null") + " scores");
            
            request.setAttribute("highScores", highScoresList);

            RequestDispatcher dispatcher = request.getRequestDispatcher("highscores.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            System.err.println("HarryPotterScoreServlet SQL Error: " + e.getMessage());
            e.printStackTrace();
            handleError(request, response, "Database error: " + e.getMessage());
        } catch (ServletException | IOException e) {
            System.err.println("HarryPotterScoreServlet Servlet/IO Error: " + e.getMessage());
            e.printStackTrace();
            handleError(request, response, "Internal error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("HarryPotterScoreServlet Unexpected Error: " + e.getMessage());
            e.printStackTrace();
            handleError(request, response, "Unexpected error: " + e.getMessage());
        }
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, String message) {
        request.setAttribute("errorMessage", message);
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            System.err.println("Error al redirigir a la página de error: " + e.getMessage());
        }
    }
}
