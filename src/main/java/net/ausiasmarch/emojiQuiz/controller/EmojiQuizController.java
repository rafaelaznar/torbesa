package net.ausiasmarch.emojiQuiz.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import net.ausiasmarch.emojiQuiz.model.EmojiQuizBean;
import net.ausiasmarch.emojiQuiz.service.EmojiQuizService;
import net.ausiasmarch.shared.model.UserBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/emojiQuiz/play")
public class EmojiQuizController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        // obtener el usuario de la sesión
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("sessionUser");
        
        // si no hay usuario, redirigir a la página de inicio de sesión

        if (user == null) {
            try {
                response.sendRedirect("../shared/login.jsp");
            } catch (IOException e) {
                System.err.println("Error al redirigir a la página de inicio de sesión: " + e.getMessage());
            }
            return;
        } else {
            request.setAttribute("sessionUser", user);
        }

        EmojiQuizService emojiQuizService = new EmojiQuizService();
        try {
            EmojiQuizBean getRandomQuestion = emojiQuizService.getRandomQuestion();
            List<String> options = emojiQuizService.getShuffledOptions(getRandomQuestion);
            request.setAttribute("question", getRandomQuestion);
            request.setAttribute("options", options);
            request.getRequestDispatcher("/emojiQuiz/game.jsp").forward(request, response);
        } catch (SQLException e) {
            System.err.println("Error al obtener la pregunta aleatoria: " + e.getMessage());
        } catch (ServletException e) {
            System.err.println("Error al redirigir a la página de juego: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Verificar si el usuario ha iniciado sesión
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("sessionUser");

        if (user == null) {
            response.sendRedirect("../shared/login.jsp");
            return;
        } else {
            request.setAttribute("username", user.getUsername());
        }
    }
}