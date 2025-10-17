package net.ausiasmarch.codequest.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ausiasmarch.codequest.dao.ScoreDao;
import net.ausiasmarch.codequest.model.ScoreDto;
import net.ausiasmarch.codequest.model.TechnologyBean;
import net.ausiasmarch.codequest.service.ScoreService;
import net.ausiasmarch.codequest.service.TechnologyService;
import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.shared.model.UserBean;

@WebServlet("/codequest/GameServlet")
public class GameServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
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

        TechnologyService technologyService = new TechnologyService(request.getServletContext());

        // Obtener lista de tecnologías ya mostradas en sesión
        List<String> shownTechnologies = (List<String>) session.getAttribute("shownTechnologies");
        if (shownTechnologies == null) {
            shownTechnologies = new ArrayList<>();
        }

        // Seleccionar tecnología aleatoria excluyendo las ya mostradas
        TechnologyBean selectedTechnology = technologyService.getRandomTechnologyExcluding(shownTechnologies);

        if (selectedTechnology == null) {
            // Si no quedan tecnologías disponibles
            session.removeAttribute("shownTechnologies");
            request.setAttribute("errorMessage", "No hay más tecnologías disponibles. ¡Has respondido todas!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
            try {
                dispatcher.forward(request, response);
            } catch (ServletException | IOException e) {
                System.err.println("Error al mostrar error.jsp: " + e.getMessage());
            }
            return;
        }

        // Guardar tecnología mostrada
        shownTechnologies.add(selectedTechnology.getName());
        session.setAttribute("shownTechnologies", shownTechnologies);

        // Generar opciones de descripción para el test (seguro contra null)
        String correctDescription = selectedTechnology.getDescription() != null ? selectedTechnology.getDescription() : "";
List<String> optionsListForTechnologyTest = technologyService.getRandomDescriptionsForTest(selectedTechnology, 4);

        request.setAttribute("technology", selectedTechnology.getName());
        request.setAttribute("options", optionsListForTechnologyTest);

        RequestDispatcher dispatcher = request.getRequestDispatcher("game.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            System.err.println("Error al mostrar game.jsp: " + e.getMessage());
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession();
            UserBean user = (UserBean) session.getAttribute("sessionUser");

            if (user == null) {
                response.sendRedirect("../shared/login.jsp");
                return;
            }

            ScoreService scoreService = new ScoreService();
            TechnologyService technologyService = new TechnologyService(request.getServletContext());

            String technology = request.getParameter("technology");
            String descriptionGuess = request.getParameter("descriptionGuess");

            // Obtener descripción correcta
            String correctDescription = technologyService.fetchAllTechnologies().stream()
                    .filter(t -> t.getName().equals(technology))
                    .map(TechnologyBean::getDescription)
                    .findFirst()
                    .orElse("");

            request.setAttribute("technology", technology);
            request.setAttribute("correctDescription", correctDescription);
            request.setAttribute("descriptionGuess", descriptionGuess);

            boolean isCorrect = descriptionGuess != null && descriptionGuess.equalsIgnoreCase(correctDescription);
            request.setAttribute("isCorrect", isCorrect);

            // Mensaje personalizado
            String message;
            if (isCorrect) {
                scoreService.set(user.getId(), true);
                message = "¡Correcto! Excelente conocimiento de tecnología.";
            } else if (descriptionGuess == null || descriptionGuess.isEmpty()) {
                scoreService.set(user.getId(), false);
                message = "No has introducido ninguna descripción. ¡Inténtalo!";
            } else {
                scoreService.set(user.getId(), false);
                message = "Incorrecto. La respuesta correcta era: " + correctDescription;
            }
            request.setAttribute("message", message);

            // Obtener score y highscores
            try (Connection oConnection = HikariPool.getConnection()) {
                ScoreDao oScoreDao = new ScoreDao(oConnection);
                ScoreDto userScore = oScoreDao.get(user.getId());
                // Valores por defecto si userScore es null
                if (userScore == null) {
                    userScore = new ScoreDto();
                    userScore.setScore(0);
                    userScore.setTries(0);
                    // Si necesitas mostrar una fecha por defecto en el JSP, hazlo allí con JSTL/EL
                }
                request.setAttribute("userScore", userScore);
                List<ScoreDto> highScores = oScoreDao.getTop10();
                request.setAttribute("highScores", highScores);

                RequestDispatcher dispatcher = request.getRequestDispatcher("scores.jsp");
                dispatcher.forward(request, response);
            }

        } catch (SQLException e) {
            System.err.println("Error de base de datos: " + e.getMessage());
            request.setAttribute("errorMessage", "Error de base de datos");
            RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
            dispatcher.forward(request, response);
        }
    }
}
