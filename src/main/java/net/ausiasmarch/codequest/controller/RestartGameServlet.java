package net.ausiasmarch.codequest.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ausiasmarch.shared.model.UserBean;

@WebServlet("/codequest/RestartGameServlet")
public class RestartGameServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("sessionUser");
        if (user == null) {
            try {
                response.sendRedirect("../shared/login.jsp");
            } catch (IOException e) {
                System.err.println("Error al redirigir a la página de inicio de sesión: " + e.getMessage());
            }
            return;
        }

        // Reiniciar el estado del juego
        session.setAttribute("gameErrors", 0);
        session.setAttribute("gameOver", false);
        
        // Redirigir al GameServlet para empezar un nuevo juego
        try {
            response.sendRedirect("GameServlet");
        } catch (IOException e) {
            System.err.println("Error al redirigir al juego: " + e.getMessage());
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}