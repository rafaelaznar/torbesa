package net.ausiasmarch.kimetsu.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import net.ausiasmarch.kimetsu.dao.ScoreDao;
import net.ausiasmarch.kimetsu.model.ScoreDto;
import net.ausiasmarch.kimetsu.service.ScoreService;
import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.shared.model.UserBean;
import net.ausiasmarch.kimetsu.model.KimetsuBean;
import net.ausiasmarch.kimetsu.service.KimetsuService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/kimetsu/GameServlet")
public class GameServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("sessionUser");

        // Si no hay usuario, redirigir al login
        if (user == null) {
            try {
                response.sendRedirect("../shared/login.jsp");
            } catch (IOException e) {
                System.err.println("Error al redirigir al login: " + e.getMessage());
            }
            return;
        }

        // Mostrar usuario en la vista
        request.setAttribute("sessionUser", user);

        // Crear servicio y obtener datos
        KimetsuService kimetsuService = new KimetsuService(request.getServletContext());
        KimetsuBean selectedKimetsu = kimetsuService.getOneRandomKimetsu();

        if (selectedKimetsu == null) {
            try {
                request.setAttribute("errorMessage", "No se pudieron cargar los datos de los personajes.");
                request.getRequestDispatcher("../shared/error.jsp").forward(request, response);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Generar opciones de frases para el test
        ArrayList<String> optionsList = kimetsuService.getRandomKimetsuOptionsForTest(selectedKimetsu, 3);

        // Pasar los datos al JSP
        request.setAttribute("name", selectedKimetsu.getName()); // nombre correcto del personaje
        request.setAttribute("options", optionsList);

        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("game.jsp");
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
        }

        // Obtener parámetros del formulario
        String name = request.getParameter("name"); // nombre del personaje
        String quoteGuess = request.getParameter("quoteGuess"); // frase seleccionada

        if (quoteGuess == null) {
            quoteGuess = "";
        }

        KimetsuService kimetsuService = new KimetsuService(request.getServletContext());

        String correctQuote = kimetsuService.getAllKimetsu().stream()
                .filter(k -> k.getName().equalsIgnoreCase(name))
                .map(KimetsuBean::getQuote)
                .findFirst()
                .orElse("");

        // Evaluar respuesta
        boolean isCorrect = quoteGuess.equals(correctQuote);
        request.setAttribute("name", name);
        request.setAttribute("correctQuote", correctQuote);
        request.setAttribute("quoteGuess", quoteGuess);
        request.setAttribute("message", isCorrect ? "✅ ¡Correcto!" : "❌ Incorrecto. Inténtalo de nuevo.");

        // Guardar puntuación
        try (Connection oConnection = HikariPool.getConnection()) {
            ScoreDao oScoreDao = new ScoreDao(oConnection);
            ScoreService scoreService = new ScoreService();
            scoreService.set(user.getId(), isCorrect);

            // Obtener puntuaciones
            ScoreDto userScore = oScoreDao.get(user.getId());
            List<ScoreDto> highScores = oScoreDao.getTop10();

            request.setAttribute("userScore", userScore);
            request.setAttribute("highScores", highScores);

            RequestDispatcher dispatcher = request.getRequestDispatcher("scores.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            System.err.println("Error de base de datos: " + e.getMessage());
            request.setAttribute("errorMessage", "Error de base de datos");
            request.getRequestDispatcher("../shared/error.jsp").forward(request, response);
        }
    }
}
