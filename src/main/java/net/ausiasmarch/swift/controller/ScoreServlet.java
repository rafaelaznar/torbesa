package net.ausiasmarch.swift.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import net.ausiasmarch.swift.service.ScoreService;
import net.ausiasmarch.swift.model.ScoreDto;

@WebServlet("/swift/ScoreServlet")
public class ScoreServlet extends HttpServlet {
    private ScoreService oScoreService;

    public ScoreServlet() {
        this.oScoreService = new ScoreService();
    }

    // Constructor para inyección en tests
    public ScoreServlet(ScoreService scoreService) {
        this.oScoreService = scoreService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
        try {
            List<ScoreDto> highScoresList = oScoreService.getHighScores();
            request.setAttribute("highScores", highScoresList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("highscores.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            request.setAttribute("errorMessage", "Database error");
            RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
            try {
                dispatcher.forward(request, response);
            } catch (ServletException | IOException e1) {
                System.err.println("Error forwarding to error page: " + e1.getMessage());
            }
        } catch (ServletException | IOException e) {
            System.err.println("Internal error: " + e.getMessage());
            request.setAttribute("errorMessage", "Internal error");
            RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
            try {
                dispatcher.forward(request, response);
            } catch (ServletException | IOException e1) {
                System.err.println("Error forwarding to error page: " + e1.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            request.setAttribute("errorMessage", e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
            try {
                dispatcher.forward(request, response);
            } catch (ServletException | IOException e1) {
                System.err.println("Error forwarding to error page: " + e1.getMessage());
            }
        }
    }
}
