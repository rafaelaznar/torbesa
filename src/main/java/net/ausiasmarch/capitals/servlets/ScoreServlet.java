package net.ausiasmarch.capitals.servlets;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import net.ausiasmarch.capitals.models.Score;
import net.ausiasmarch.capitals.models.User;
import net.ausiasmarch.capitals.services.ScoreService;

import java.io.IOException;
import java.util.List;

@WebServlet("/capitals/ScoreServlet")
public class ScoreServlet extends HttpServlet {
    private ScoreService scoreService = new ScoreService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("sessionUser");
        if (user == null) {
            request.setAttribute("sessionUser", null);
        } else {
            request.setAttribute("sessionUser", user);
        }

        List<Score> highScores = scoreService.getHighScores();
        request.setAttribute("highScores", highScores);
        RequestDispatcher dispatcher = request.getRequestDispatcher("highscores.jsp");
        dispatcher.forward(request, response);
    }
}
