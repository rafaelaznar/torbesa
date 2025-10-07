package net.ausiasmarch.whosthatpokemon.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import net.ausiasmarch.shared.model.UserBean;
import net.ausiasmarch.whosthatpokemon.dao.ScoreDao;
import net.ausiasmarch.whosthatpokemon.model.ScoreBean;
import net.ausiasmarch.shared.connection.HikariPool;

@WebServlet("/whosthatpokemon/DeleteScoreServlet")
public class DeleteScoreServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("sessionUser") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        UserBean user = (UserBean) session.getAttribute("sessionUser");
    try (Connection oConnection = HikariPool.getConnection()) {
            ScoreDao scoreDao = new ScoreDao(oConnection);
            ScoreBean score = scoreDao.get(user.getId());
            if (score != null) {
                scoreDao.delete(score.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Puedes añadir manejo de error aquí
        }
        response.sendRedirect(request.getContextPath() + "/whosthatpokemon/landingpokemon.jsp");
    }
}
