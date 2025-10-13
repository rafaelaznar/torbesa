package net.ausiasmarch.starwars.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import net.ausiasmarch.starwars.dao.ScoreDao;
import net.ausiasmarch.starwars.model.CharacterBean;
import net.ausiasmarch.starwars.model.ScoreDto;
import net.ausiasmarch.starwars.service.CharacterService;
import net.ausiasmarch.starwars.service.ScoreService;
import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.shared.model.UserBean;

import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@WebServlet("/starwars/GameServlet")
public class GameServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            // --- 1. Verificar Sesión de Usuario ---
            HttpSession session = request.getSession();
            UserBean user = (UserBean) session.getAttribute("sessionUser");
            if (user == null) {
                // Manejo de redirección si no hay usuario (esto ya lo tenías)
                response.sendRedirect("../shared/login.jsp");
                return;
            } else {
                request.setAttribute("sessionUser", user);
            }

            // --- 2. Lógica del Juego (Punto de Fallo Crítico) ---
            CharacterService oCharacterService = new CharacterService(request.getServletContext());
            
            // ¡CUIDADO AQUÍ! Si la BD falla o no hay datos, selectedCharacter podría ser null.
            CharacterBean selectedCharacter = oCharacterService.getOneRandomCharacter(); 
            
            // Manejo de carácter nulo para evitar NullPointerException
            if (selectedCharacter == null) {
                throw new IllegalStateException("No se pudo obtener un personaje aleatorio. La base de datos puede estar vacía o el servicio falló.");
            }
            
            String correctSpecies = selectedCharacter.getSpecies();
            List<String> allSpecies = oCharacterService.fetchAllSpeciesNames();
            
            // Manejo de especies vacías
            if (allSpecies == null || allSpecies.isEmpty()) {
                throw new IllegalStateException("No se pudieron obtener nombres de especies. La tabla de especies podría estar vacía.");
            }
            
            // Lógica para generar las opciones (esta lógica parece correcta)
            ArrayList<String> optionsList = new ArrayList<>();
            optionsList.add(correctSpecies);

            int requiredOptions = 3;
            Random random = new Random();

            // Asegúrate de que haya suficientes especies para llenar las opciones
            while (optionsList.size() < requiredOptions && optionsList.size() < allSpecies.size()) {
                int randomIndex = random.nextInt(allSpecies.size());
                String randomSpecies = allSpecies.get(randomIndex);

                if (!optionsList.contains(randomSpecies)) {
                    optionsList.add(randomSpecies);
                }
            }

            Collections.shuffle(optionsList);

            // --- 3. Establecer Atributos y Reenviar ---
            // Asumiendo que también quieres el Score en la sesión
            Integer score = (Integer) session.getAttribute("score");
            if (score == null) {
                score = 0; // Inicializar score si es el inicio del juego
                session.setAttribute("score", score);
            }
            
            request.setAttribute("character", selectedCharacter);       
            request.setAttribute("options", optionsList);
            request.setAttribute("score", score); // Pasar el score a la vista
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("gameSW.jsp");
            dispatcher.forward(request, response);
            
        } catch (Exception e) {
            // --- MANEJO DE ERROR CRÍTICO ---
            // Imprime el Stack Trace en la consola de Tomcat para saber la causa
            e.printStackTrace();
            System.err.println("FATAL ERROR en GameServlet.doGet: " + e.getMessage());

            // Redirige al usuario a una página de error para evitar que se quede cargando
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al cargar el juego. Consulte los logs del servidor para más detalles.");
            } catch (IOException ioException) {
                System.err.println("Error al enviar el 500: " + ioException.getMessage());
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
        // El try-catch envuelve toda la lógica de validación y base de datos
        try {
            HttpSession session = request.getSession();
            UserBean user = (UserBean) session.getAttribute("sessionUser");
            
            // 1. Verificación de sesión de usuario
            if (user == null) {
                response.sendRedirect("../shared/login.jsp");
                return;
            } 

            // 2. Recuperación de los parámetros de respuesta
            String characterGuess = request.getParameter("characterGuess");
            // CORRECCIÓN CLAVE: Usamos el nombre del campo oculto del JSP.
            String correctSpeciesName = request.getParameter("correctSpeciesName"); 
            
            // **¡CLAVE! Obtener el nombre del personaje enviado desde el formulario original.**
            // Asumiendo que has añadido: <input type="hidden" name="lastCharacterName" value="${character.name}"> en gameSW.jsp
            String lastCharacterName = request.getParameter("lastCharacterName");

            // Inicializar el ScoreService
            ScoreService scoreService = new ScoreService();

            // 3. Lógica de validación
            boolean isCorrect = false;
            String message;

            // Validación robusta: se utiliza trim() para eliminar espacios
            // y equalsIgnoreCase() para ignorar mayúsculas y minúsculas (ej: "Droid" vs "droide").
            if (correctSpeciesName != null && characterGuess != null && 
                correctSpeciesName.trim().equalsIgnoreCase(characterGuess.trim())) {
                
                isCorrect = true;
                message = "Correct! Well done.";
            } else {
                // Se puede incluir la respuesta correcta en el mensaje para el usuario si es útil.
                message = "Incorrect. The correct species was: " + (correctSpeciesName != null ? correctSpeciesName : "Unknown");
            }

            // 4. Actualizar puntuación y establecer atributos para scoresSW.jsp
            scoreService.set(user.getId(), isCorrect);
            request.setAttribute("message", message);
            
            // Establecer el nombre de usuario (aunque ya está en la sesión, es buena práctica si la vista lo requiere)
            request.setAttribute("username", user.getUsername());

            // ESTABLECER ATRIBUTOS FALTANTES PARA LA TARJETA DE FEEDBACK:
            request.setAttribute("characterGuess", characterGuess); 
            request.setAttribute("correctSpeciesName", correctSpeciesName); 
            request.setAttribute("lastCharacterName", lastCharacterName);

            // 5. Conexión a BD para High Scores y Reenvío
            try (Connection oConnection = HikariPool.getConnection()) {
                ScoreDao oScoreDao = new ScoreDao(oConnection);
                ScoreDto userScore = oScoreDao.get(user.getId());
                request.setAttribute("userScore", userScore);

                List<ScoreDto> highScores = oScoreDao.getTop10();
                request.setAttribute("highScores", highScores);

                RequestDispatcher dispatcher = request.getRequestDispatcher("scoresSW.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException e) {
            // Manejo de errores de base de datos
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace(); // Imprimir stack trace completo para debugging
            request.setAttribute("errorMessage", "Database error: Unable to process score.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
            dispatcher.forward(request, response);
        }
    }
}