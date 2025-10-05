package net.ausiasmarch.whosthatpokemon.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ausiasmarch.shared.model.UserBean;
import net.ausiasmarch.whosthatpokemon.model.PokemonBean;
import net.ausiasmarch.whosthatpokemon.service.WTPService;
import net.ausiasmarch.whosthatpokemon.service.ScoreService;
import java.sql.SQLException;

@WebServlet("/whosthatpokemon/GameServlet")
public class GameServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //Coge todos los datos de la sesión del usuario
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("sessionUser");
        //Si no hay usuario en la sesión nos redirige al login
        if (user == null) {
            response.sendRedirect("../shared/login.jsp");
            return;
        } else {
            // pone el usuario en el request para que el JSP pueda saludarle
            request.setAttribute("sessionUser", user);
        }

        // If query param new=true, clear current pokemon to fetch a new one
        boolean forceNew = "true".equalsIgnoreCase(request.getParameter("new"));
        PokemonBean current = (PokemonBean) session.getAttribute("whosthatpokemon_current");
        if (current == null || forceNew) {
            WTPService svc = new WTPService();
            PokemonBean p = svc.getRandomPokemon();
            if (p == null) {
                request.setAttribute("error", "No se pudo obtener un Pokémon. Intenta de nuevo más tarde.");
            } else {
                session.setAttribute("whosthatpokemon_current", p);
                request.setAttribute("revealed", false);
            }
        }

        RequestDispatcher rd = request.getRequestDispatcher("/whosthatpokemon/game.jsp");
        rd.forward(request, response);
    }

    //
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //recupera la sesión del usuario
        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("sessionUser");
        //si no hay usuario en la sesión nos redirige al login
        if (user == null) {
            response.sendRedirect("../shared/login.jsp");
            return;
        }
        
        PokemonBean current = (PokemonBean) session.getAttribute("whosthatpokemon_current");
        if (current == null) {
            // no hay pokemon en sesión -> redirigir a GET para crear uno
            response.sendRedirect(request.getContextPath() + "/whosthatpokemon/GameServlet");
            return;
        }

        String guess = request.getParameter("guess");
        boolean correct = false;
        if (guess != null && current.getName() != null) {
            correct = guess.trim().equalsIgnoreCase(current.getName().trim());
        }

        request.setAttribute("guess", guess);
        request.setAttribute("correct", correct);
        request.setAttribute("revealed", true);

        // keep current pokemon in the request so JSP can show the 'correct' message even after we remove it from session
        request.setAttribute("p", current);

        // update persistent score: increment tries every attempt, increment score if correct
        try {
            ScoreService scoreService = new ScoreService();
            scoreService.set(user.getId(), correct);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("scoreError", "No se pudo actualizar la puntuación.");
        }

        if (correct) {
            // clear current to force next GET to pick a new pokemon
            session.removeAttribute("whosthatpokemon_current");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/whosthatpokemon/game.jsp");
        rd.forward(request, response);
    }

}
