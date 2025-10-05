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
import net.ausiasmarch.codequest.service.ExternalTechnologyService;
import net.ausiasmarch.codequest.service.ScoreService;
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
                System.err.println("Error al redirigir a la página de inicio de sesión: " + e.getMessage());
            }
            return;
        } else {
            request.setAttribute("sessionUser", user);
        }

        // Inicializar o obtener el estado del juego de la sesión
        Integer gameErrors = (Integer) session.getAttribute("gameErrors");
        Boolean gameOver = (Boolean) session.getAttribute("gameOver");
        
        if (gameErrors == null) {
            gameErrors = 0;
            session.setAttribute("gameErrors", gameErrors);
        }
        
        if (gameOver == null) {
            gameOver = false;
            session.setAttribute("gameOver", gameOver);
        }
        
        // Si el juego ha terminado (2 errores), redirigir a puntuaciones
        if (gameOver || gameErrors >= 2) {
            try {
                response.sendRedirect("ScoreServlet");
            } catch (IOException e) {
                System.err.println("Error al redirigir a puntuaciones: " + e.getMessage());
            }
            return;
        }

        ExternalTechnologyService oTechnologyService = new ExternalTechnologyService(request.getServletContext());
        System.out.println("=== DEBUG: Creando servicio de tecnologías externas ===");
        
        TechnologyBean selectedTechnology = oTechnologyService.getRandomTechnology();
        System.out.println("=== DEBUG: Tecnología seleccionada: " + (selectedTechnology != null ? selectedTechnology.getName() : "NULL") + " ===");
        
        if (selectedTechnology == null) {
            System.err.println("=== ERROR: No se pudo obtener tecnología del servicio externo ===");
            try {
                request.setAttribute("errorMessage", "No hay tecnologías disponibles en el servicio externo");
                RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
                dispatcher.forward(request, response);
            } catch (ServletException | IOException e) {
                System.err.println("Error al redirigir a la página de error: " + e.getMessage());
            }
            return;
        }
        
        ArrayList<String> optionsListForDescriptionTest = (ArrayList<String>) oTechnologyService.generateDescriptionOptions(selectedTechnology.getDescription());        
        System.out.println("=== DEBUG: Opciones generadas: " + optionsListForDescriptionTest.size() + " ===");
        
        request.setAttribute("technology", selectedTechnology.getName());
        request.setAttribute("technologyType", selectedTechnology.getType());
        request.setAttribute("technologyCategory", selectedTechnology.getCategory());
        request.setAttribute("technologyDifficulty", selectedTechnology.getDifficulty());
        request.setAttribute("options", optionsListForDescriptionTest);
        request.setAttribute("gameErrors", gameErrors);
        request.setAttribute("remainingChances", 2 - gameErrors);
        
        System.out.println("=== DEBUG: Redirigiendo a game.jsp ===");
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("game.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            System.err.println("Error al redirigir a la página del juego: " + e.getMessage());
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
            } else {
                request.setAttribute("username", user.getUsername());
            }

            // Obtener estado del juego
            Integer gameErrors = (Integer) session.getAttribute("gameErrors");
            if (gameErrors == null) {
                gameErrors = 0;
            }

            ScoreService scoreService = new ScoreService();
            String technology = request.getParameter("technology");
            String descriptionGuess = request.getParameter("descriptionGuess");
            
            ExternalTechnologyService oTechnologyService = new ExternalTechnologyService(request.getServletContext());
            String correctDescription = oTechnologyService.fetchAllTechnologies().stream()
                    .filter(t -> t.getName().equals(technology))
                    .map(TechnologyBean::getDescription)
                    .findFirst()
                    .orElse("");
                    
            request.setAttribute("technology", technology);
            request.setAttribute("correctDescription", correctDescription);
            request.setAttribute("descriptionGuess", descriptionGuess);
            
            boolean isCorrect = descriptionGuess.equals(correctDescription);
            
            if (isCorrect) {
                scoreService.set(user.getId(), true);
                request.setAttribute("message", "¡Correcto! Excelente conocimiento.");
                request.setAttribute("isCorrect", true);
                // Continuar jugando - no cambiar gameErrors
            } else {
                gameErrors++;
                session.setAttribute("gameErrors", gameErrors);
                scoreService.set(user.getId(), false);
                
                if (gameErrors >= 2) {
                    // Juego terminado
                    session.setAttribute("gameOver", true);
                    request.setAttribute("message", "¡Juego terminado! Has alcanzado el máximo de errores permitidos.");
                } else {
                    // Aún puede continuar
                    int remainingChances = 2 - gameErrors;
                    request.setAttribute("message", String.format("Incorrecto. Te quedan %d oportunidad%s.", 
                        remainingChances, remainingChances == 1 ? "" : "es"));
                }
                request.setAttribute("isCorrect", false);
            }
            
            request.setAttribute("gameErrors", gameErrors);
            request.setAttribute("remainingChances", Math.max(0, 2 - gameErrors));

            try (Connection oConnection = HikariPool.getConnection()) {

                ScoreDao oScoreDao = new ScoreDao(oConnection);
                ScoreDto userScore = oScoreDao.get(user.getId());
                request.setAttribute("userScore", userScore);

                List<ScoreDto> highScores = oScoreDao.getTop10();
                request.setAttribute("highScores", highScores);

                RequestDispatcher dispatcher = request.getRequestDispatcher("scores.jsp");
                dispatcher.forward(request, response);

            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            request.setAttribute("errorMessage", "Error de base de datos");
            RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
            dispatcher.forward(request, response);
        }

    }
}

