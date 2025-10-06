package net.ausiasmarch.harrypotter.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ausiasmarch.harrypotter.model.HarryPotterCharacterBean;
import net.ausiasmarch.harrypotter.service.HarryPotterCharacterService;
import net.ausiasmarch.shared.model.UserBean;

@WebServlet("/harrypotter/GameServlet")
public class HarryPotterGameServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("sessionUser");

        if (user == null) {
            try {
                response.sendRedirect("../shared/login.jsp");
            } catch (IOException e) {
                System.err.println("Error al redirigir a login: " + e.getMessage());
            }
            return;
        } else {
            request.setAttribute("sessionUser", user);
        }

        HarryPotterCharacterService characterService = new HarryPotterCharacterService(request.getServletContext());
        HarryPotterCharacterBean selectedCharacter = characterService.getOneRandomCharacter();
        ArrayList<String> optionsListForHouseTest = characterService.getRandomHousesForTest(selectedCharacter, 3);

        request.setAttribute("characterName", selectedCharacter.getName());
        request.setAttribute("options", optionsListForHouseTest);

        RequestDispatcher dispatcher = request.getRequestDispatcher("game.jsp");  // 👈 crea esta JSP tipo capitales/game.jsp
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            System.err.println("Error al redirigir a game.jsp: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("sessionUser");

        if (user == null) {
            response.sendRedirect("../shared/login.jsp");
            return;
        } else {
            request.setAttribute("username", user.getUsername());
        }

        String characterName = request.getParameter("characterName");
        String houseGuess = request.getParameter("houseGuess");

        HarryPotterCharacterService characterService = new HarryPotterCharacterService(request.getServletContext());
        HarryPotterCharacterBean selectedCharacter = characterService.getCharacterByName(characterName);

        String correctHouse = selectedCharacter != null ? selectedCharacter.getHouse() : "";

        request.setAttribute("characterName", characterName);
        request.setAttribute("correctHouse", correctHouse);
        request.setAttribute("houseGuess", houseGuess);

        if (houseGuess.equals(correctHouse)) {
            request.setAttribute("message", "¡Correcto! " + characterName + " pertenece a " + correctHouse + ".");
        } else {
            request.setAttribute("message", "¡Incorrecto! " + characterName + " pertenece a " + correctHouse + ".");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("result.jsp");  // 👈 haz otra JSP para mostrar resultado
        dispatcher.forward(request, response);
    }
}
