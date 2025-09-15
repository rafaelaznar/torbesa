package net.ausiasmarch.capitals.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import net.ausiasmarch.capitals.dao.ScoreDao;
import net.ausiasmarch.capitals.model.ScoreDto;
import net.ausiasmarch.capitals.model.UserBean;
import java.io.IOException;
import java.util.List;

@WebServlet("/capitals/ScoreServlet")
public class ScoreServlet extends HttpServlet {
    private ScoreDao oScoreDao = new ScoreDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("sessionUser");
        if (user == null) {
            request.setAttribute("sessionUser", null);
        } else {
            request.setAttribute("sessionUser", user);
        }

        List<ScoreDto> highScoresList = oScoreDao.getTop10();
        request.setAttribute("highScores", highScoresList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("highscores.jsp");
        dispatcher.forward(request, response);
    }
}
