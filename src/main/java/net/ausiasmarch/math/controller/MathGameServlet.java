package net.ausiasmarch.math.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ausiasmarch.math.service.MathGameService;

@WebServlet("/math/GameServlet")
public class MathGameServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MathGameService service = new MathGameService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setAttribute("question", service.generateQuestion(session).getExpression());
        request.setAttribute("score", session.getAttribute("mathScore"));
        request.getRequestDispatcher("/math/game.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String answer = request.getParameter("answer");
        boolean correct = service.checkAnswer(session, answer);
        request.setAttribute("correct", correct);
        doGet(request, response);
    }
}
