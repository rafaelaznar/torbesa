package net.ausiasmarch.capitals.servlets;


import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import net.ausiasmarch.capitals.models.User;
import net.ausiasmarch.capitals.services.AuthService;

import java.io.IOException;

@WebServlet("/capitals/LoginServlet")
public class LoginServlet extends HttpServlet {
    private AuthService authService = new AuthService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (authService.authenticate(username, password)) {
            User user = authService.getUserByUsername(username);
            request.getSession().setAttribute("sessionUser", user);
            response.sendRedirect("GameServlet");
        } else {
            request.setAttribute("error", "Invalid username or password");
            RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
            dispatcher.forward(request, response);
        }
    }
}
