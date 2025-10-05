package net.ausiasmarch.trivial.controller;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import net.ausiasmarch.capitals.dao.ScoreDao;
import net.ausiasmarch.capitals.model.CountryBean;
import net.ausiasmarch.capitals.model.ScoreDto;
import net.ausiasmarch.capitals.service.CountryService;
import net.ausiasmarch.capitals.service.ScoreService;
import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.shared.model.UserBean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet("/trivial/ScoreServlet")
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
