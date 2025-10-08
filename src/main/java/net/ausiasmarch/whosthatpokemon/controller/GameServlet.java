package net.ausiasmarch.whosthatpokemon.controller;

import java.io.IOException;

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

        // si la query new es true sacamos otro pokemon
        boolean forceNew = "true".equalsIgnoreCase(request.getParameter("new"));
        // obtenemos la información del pokemon actual (en caso de tenerlo)
        PokemonBean current = (PokemonBean) session.getAttribute("whosthatpokemon_current");
        // si no existe un pokemon actual o new es true, llamaremos al WTPService para obtener otro pokemon
        if (current == null || forceNew) {
            WTPService svc = new WTPService();
            //vovemos a guardar el pokemon que ha tocado en p
            PokemonBean p = svc.getRandomPokemon();
            // si sigue siendo null ya saltará un error
            if (p == null) {
                request.setAttribute("error", "No se pudo obtener un Pokémon. Intenta de nuevo más tarde.");
            } 
            //si no es null lo guardará en la sesión y revealed a false
            else {
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
        
        //recupera el pokemon que tenemos actualmente en sesión
        PokemonBean current = (PokemonBean) session.getAttribute("whosthatpokemon_current");
        //si no hay pokemon en la sesión nos redirige a este mismo servlet 
        //para volver a hacer los pasaos de obtener un pokemon
        if (current == null) {
            response.sendRedirect(request.getContextPath() + "/whosthatpokemon/GameServlet");
            return;
        }
        //obtenemos el texto que había en el input del formulario de game.jsp
        String guess = request.getParameter("guess");
        boolean correct = false;
        //si ha recivido un texto del formulario y tenemos el nombre del pokemon entramos al if
        if (guess != null && current.getName() != null) {
            //compara el nombre que hemos escrito con el del pokemon y guarda true o false en la variable correct
            correct = guess.trim().equalsIgnoreCase(current.getName().trim());
        }
        //hacemos un request de los datos que usaremos y cambia el revelated a true para así ver el sprite del pokemon
        request.setAttribute("guess", guess);
        request.setAttribute("correct", correct);
        request.setAttribute("revealed", true);

        // envíamos los datos del pokemon actual al JSP para que pueda
        //seguir mostrandolo aunque hayamos quitado ya la sesión
        request.setAttribute("p", current);

        // CORREGIR PUNTUACIÓN, NO SE MUESTRA EN EL JSP
        try {
            ScoreService scoreService = new ScoreService();
            scoreService.set(user.getId(), correct);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("scoreError", "No se pudo actualizar la puntuación.");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/whosthatpokemon/game.jsp");
        rd.forward(request, response);
    }

}
