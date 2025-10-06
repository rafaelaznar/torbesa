package net.ausiasmarch.perro.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ausiasmarch.capitals.model.ScoreDto;
import net.ausiasmarch.capitals.service.ScoreService;

@WebServlet("/perro/PuntuacionServlet")
public class PuntuacionServlet extends javax.servlet.http.HttpServlet {
     private ScoreService oScoreService;

    public PuntuacionServlet() {
        this.oScoreService = new ScoreService();
    }

    // Constructor para inyección en tests
    public PuntuacionServlet(ScoreService scoreService) {
        this.oScoreService = scoreService;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)  {
        try {
            List<ScoreDto> highScoresList = oScoreService.getHighScores();
            request.setAttribute("highScores", highScoresList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("highscores.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la operación en la base de datos: " + e.getMessage());
            request.setAttribute("errorMessage", "Database error");
            RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
            try {
                dispatcher.forward(request, response);
            } catch (ServletException | IOException e1) {
                System.err.println("Error al redirigir a la página de error: " + e1.getMessage());
            }
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
}
