package net.ausiasmarch.codequest.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ausiasmarch.codequest.model.ScoreDto;
import net.ausiasmarch.codequest.service.ScoreService;


@WebServlet("/codequest/score")
public class ScoreServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ScoreService scoreService;

    public ScoreServlet() {
        this.scoreService = new ScoreService();
    }

    // Constructor para inyección en tests
    public ScoreServlet(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)  {
        try {
            List<ScoreDto> highScoresList = scoreService.getHighScores();
            request.setAttribute("highScores", highScoresList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("highscores.jsp");
            try {
                dispatcher.forward(request, response);
            } catch (ServletException | IOException e) {
                System.err.println("Error al redirigir a la página principal: " + e.getMessage());
            }
        } catch (java.sql.SQLException e) {
            System.err.println("Error al ejecutar la operación en la base de datos: " + e.getMessage());
            request.setAttribute("errorMessage", "Database error");
            RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
            try {
                dispatcher.forward(request, response);
            } catch (ServletException | IOException e1) {
                System.err.println("Error al redirigir a la página de error: " + e1.getMessage());
            }
        } catch (RuntimeException e) {
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

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            HttpSession session = request.getSession();
            net.ausiasmarch.shared.model.UserBean user = (net.ausiasmarch.shared.model.UserBean) session.getAttribute("sessionUser");
            int userId = user != null ? user.getId() : 0;
            int score = Integer.parseInt(request.getParameter("score"));
            scoreService.insert(userId, score);
            response.sendRedirect("score");
        } catch (java.sql.SQLException e) {
            System.err.println("Error al insertar puntuación en la base de datos: " + e.getMessage());
            request.setAttribute("errorMessage", "Database error");
            RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
            try {
                dispatcher.forward(request, response);
            } catch (ServletException | IOException e1) {
                System.err.println("Error al redirigir a la página de error: " + e1.getMessage());
            }
        } catch (RuntimeException e) {
            System.err.println("Error inesperado al insertar puntuación: " + e.getMessage());
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
