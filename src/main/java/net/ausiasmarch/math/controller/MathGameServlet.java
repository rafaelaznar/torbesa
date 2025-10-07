package net.ausiasmarch.math.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import net.ausiasmarch.math.model.MathScoreBean;
import net.ausiasmarch.math.service.MathGameService;
import net.ausiasmarch.shared.model.UserBean;

@WebServlet("/math/GameServlet")
public class MathGameServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MathGameService service = new MathGameService();
    private static final int MAX_ATTEMPTS = 10;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("sessionUser");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        // Obtener o crear MathScoreBean
        MathScoreBean mathScore = (MathScoreBean) session.getAttribute("mathScore");
        if (mathScore == null) {
            mathScore = new MathScoreBean();
            mathScore.setUsername(user.getUsername());
            session.setAttribute("mathScore", mathScore);
        }

        // Comprobar si terminó
        if (mathScore.getTries() >= MAX_ATTEMPTS) {
            response.sendRedirect(request.getContextPath() + "/math/ResultServlet");
            return;
        }

        request.setAttribute("question", service.generateQuestion(session).getExpression());
        request.setAttribute("score", mathScore);
        request.setAttribute("correct", null);
        request.getRequestDispatcher("/math/game.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("sessionUser");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        MathScoreBean mathScore = (MathScoreBean) session.getAttribute("mathScore");
        if (mathScore == null) {
            mathScore = new MathScoreBean();
            mathScore.setUsername(user.getUsername());
            session.setAttribute("mathScore", mathScore);
        }

        String answer = request.getParameter("answer");
        boolean correct = service.checkAnswer(session, answer);

        if (correct) mathScore.setScore(mathScore.getScore() + 1);
        mathScore.setTries(mathScore.getTries() + 1);

        session.setAttribute("mathScore", mathScore);

        if (mathScore.getTries() >= MAX_ATTEMPTS) {
            response.sendRedirect(request.getContextPath() + "/math/ResultServlet");
            return;
        }

        request.setAttribute("correct", correct);
        doGet(request, response);
    }
}
