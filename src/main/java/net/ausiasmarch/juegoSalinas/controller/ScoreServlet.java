package net.ausiasmarch.juegoSalinas.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;
import net.ausiasmarch.juegoSalinas.model.ScoreDto;
import net.ausiasmarch.juegoSalinas.service.ScoreService;

@WebServlet("/juegoSalinas/ScoreServlet")
public class ScoreServlet extends HttpServlet {
    private final ScoreService scoreService = new ScoreService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<ScoreDto> scores = scoreService.getTopScores(10);
        request.setAttribute("scores", scores);
        request.getRequestDispatcher("/juegoSalinas/score.jsp").forward(request, response);
    }
}