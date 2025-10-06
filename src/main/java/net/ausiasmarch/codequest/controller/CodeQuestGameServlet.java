package net.ausiasmarch.codequest.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
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
import net.ausiasmarch.codequest.service.GitHubTechnologyService;
import net.ausiasmarch.codequest.service.ScoreService;
import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.shared.model.UserBean;

/**
 * Servlet principal del juego CodeQuest.
 * Muestra preguntas sobre tecnologías y procesa respuestas del usuario.
 */
@WebServlet("/codequest/GameServlet")
public class CodeQuestGameServlet extends HttpServlet {

    /**
     * Atiende peticiones GET mostrando una nueva pregunta de tecnología.
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
    // Configura la respuesta para HTML y UTF-8
    response.setContentType("text/html;charset=UTF-8");
        
        try {
            // Comprueba si el usuario está logueado
            HttpSession session = request.getSession();
            UserBean user = (UserBean) session.getAttribute("sessionUser");
            if (user == null) {
                response.sendRedirect("../shared/login.jsp");
                return;
            }
            // Pasa el usuario a la vista
            request.setAttribute("sessionUser", user);

            // Obtiene una tecnología aleatoria usando el servicio de GitHub
            System.out.println("Creando GitHubTechnologyService...");
            GitHubTechnologyService technologyService = new GitHubTechnologyService(request.getServletContext());
            
            System.out.println("DEBUG: Obteniendo tecnología aleatoria...");
            TechnologyBean selectedTechnology = technologyService.getRandomTechnology();
            
            System.out.println("DEBUG: Tecnología obtenida: " + (selectedTechnology != null ? selectedTechnology.getName() : "null"));
            
            // 3. Verificar que obtuvimos una tecnología válida
            if (selectedTechnology == null) {
                System.err.println("ERROR: No se pudo obtener ninguna tecnología");
                // Si no hay tecnologías, mostrar error de forma elegante
                request.setAttribute("errorMessage", "No hay tecnologías disponibles en este momento. Reintenta en unos segundos.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
                dispatcher.forward(request, response);
                return;
            }
            
            // 4. Generar opciones de respuesta (3 incorrectas + 1 correcta)
            System.out.println("DEBUG: Generando opciones de respuesta...");
            List<String> options = technologyService.generateDescriptionOptions(selectedTechnology.getDescription());
            
            System.out.println("DEBUG: Opciones generadas: " + options.size() + " opciones");
            
            // 5. Pasar datos a la vista JSP (igual que Capitals hace con países)
            request.setAttribute("technology", selectedTechnology.getName());
            request.setAttribute("technologyType", selectedTechnology.getType());
            request.setAttribute("technologyCategory", selectedTechnology.getCategory());
            request.setAttribute("technologyDifficulty", selectedTechnology.getDifficulty());
            request.setAttribute("options", options);
            
            System.out.println("DEBUG: Datos enviados al JSP - Technology: " + selectedTechnology.getName());
            
            // 6. Mostrar la página del juego
            RequestDispatcher dispatcher = request.getRequestDispatcher("game.jsp");
            dispatcher.forward(request, response);
            
        } catch (Exception e) {
            // Manejo robusto de cualquier error inesperado
            System.err.println("Error inesperado en CodeQuestGameServlet.doGet(): " + e.getMessage());
            
            request.setAttribute("errorMessage", "Error interno del juego. Por favor, inténtalo de nuevo.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * MÉTODO POST: Procesa la respuesta del usuario
     * Mucho más simple que antes, sigue el patrón de Capitals
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Fuerza la codificación UTF-8 para los parámetros recibidos
        request.setCharacterEncoding("UTF-8");
        try {
            // 1. Verificar usuario logueado (seguridad)
            HttpSession session = request.getSession();
            UserBean user = (UserBean) session.getAttribute("sessionUser");
            
            if (user == null) {
                response.sendRedirect("../shared/login.jsp");
                return;
            }
            
            request.setAttribute("username", user.getUsername());

            // 2. Obtener los datos del formulario
            String technology = request.getParameter("technology");
            String descriptionGuess = request.getParameter("descriptionGuess");
            
            // 3. Buscar la descripción correcta de la tecnología
            GitHubTechnologyService technologyService = new GitHubTechnologyService(request.getServletContext());
            String correctDescription = technologyService.fetchAllTechnologies().stream()
                    .filter(t -> t.getName().equals(technology))
                    .map(TechnologyBean::getDescription)
                    .findFirst()
                    .orElse("");
            
            // 4. Pasar información a la vista para mostrar resultado
            request.setAttribute("technology", technology);
            request.setAttribute("correctDescription", correctDescription);
            request.setAttribute("descriptionGuess", descriptionGuess);
            
            // 5. Verificar si la respuesta es correcta y guardar puntuación
            ScoreService scoreService = new ScoreService();
            boolean isCorrect = correctDescription != null && correctDescription.equals(descriptionGuess);
            
            if (isCorrect) {
                // Respuesta correcta: sumar punto
                scoreService.set(user.getId(), true);
                request.setAttribute("message", "¡Correcto! Excelente conocimiento de tecnología.");
                request.setAttribute("isCorrect", true);
            } else {
                // Respuesta incorrecta: restar punto
                scoreService.set(user.getId(), false);
                request.setAttribute("message", "Incorrecto. La próxima vez será mejor.");
                request.setAttribute("isCorrect", false);
            }

            // 6. Obtener puntuaciones para mostrar en la página de resultados
            try (Connection connection = HikariPool.getConnection()) {
                ScoreDao scoreDao = new ScoreDao(connection);
                
                // Puntuación del usuario actual
                ScoreDto userScore = scoreDao.get(user.getId());
                request.setAttribute("userScore", userScore);

                // Top 10 mejores puntuaciones
                List<ScoreDto> highScores = scoreDao.getTop10();
                request.setAttribute("highScores", highScores);

                // 7. Mostrar página de resultados
                RequestDispatcher dispatcher = request.getRequestDispatcher("scores.jsp");
                dispatcher.forward(request, response);
            }

        } catch (SQLException e) {
            // Manejo elegante de errores de base de datos
            System.err.println("Error de base de datos: " + e.getMessage());
            request.setAttribute("errorMessage", "Error de base de datos. Inténtalo de nuevo.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            // Manejo de errores de servlet
            System.err.println("Error de servlet: " + e.getMessage());
            request.setAttribute("errorMessage", "Error interno del servidor");
            RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
            dispatcher.forward(request, response);
        }
    }
}