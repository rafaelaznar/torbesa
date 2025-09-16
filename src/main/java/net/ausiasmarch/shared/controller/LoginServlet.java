package net.ausiasmarch.shared.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import net.ausiasmarch.shared.dao.UserDao;
import net.ausiasmarch.shared.model.UserBean;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/shared/LoginServlet")
public class LoginServlet extends HttpServlet {
    private UserDao authService = new UserDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if (authService.authenticate(username, password)) {
                UserBean user = authService.getByUsername(username);
                request.getSession().setAttribute("sessionUser", user);
                response.sendRedirect("welcome.jsp");
            } else {
                request.setAttribute("error", "Invalid username or password");
                RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            request.setAttribute("errorMessage", "Database error");
            RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
            dispatcher.forward(request, response);
        }

    }
}
