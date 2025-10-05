package net.ausiasmarch.whosthatpokemon.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ausiasmarch.whosthatpokemon.model.ScoreDao;
import net.ausiasmarch.whosthatpokemon.service.ScoreService;


@WebServlet("/whosthatpokemon/ScoreServlet")
public class ScoreServlet extends HttpServlet {
    //
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ScoreService scoreService = new ScoreService();
            List<ScoreDao> highScores = scoreService.getHighScores();
            request.setAttribute("highScores", highScores);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("scoreError", "No se pudieron cargar las puntuaciones.");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/whosthatpokemon/highscores.jsp");
        rd.forward(request, response);
    }

}
