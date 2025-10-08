package net.ausiasmarch.perro.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ausiasmarch.perro.dao.PuntuacionDao;
import net.ausiasmarch.perro.model.PuntuacionDto;
import net.ausiasmarch.perro.service.PerroService;
import net.ausiasmarch.perro.service.PuntuacionService;
import net.ausiasmarch.shared.model.UserBean;

@WebServlet("/perro/JuegoServlet")
public class JuegoServlet extends HttpServlet{

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
        // Obtener pregunta de raza
        PerroService.PreguntaRaza pregunta = new PerroService(getServletContext()).obtenerPreguntaRaza();
        request.setAttribute("imagenUrl", pregunta.imagenUrl);
        request.setAttribute("opciones", pregunta.opciones);
        request.setAttribute("razaCorrecta", pregunta.razaCorrecta); // solo para debug
        Integer puntuacion = (Integer) session.getAttribute("puntuacion");
        if (puntuacion == null) puntuacion = 0;
        request.setAttribute("puntuacion", puntuacion);
        RequestDispatcher rd = request.getRequestDispatcher("/perro/juego.jsp");
        try {
            rd.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String respuesta = request.getParameter("respuesta");
        String razaCorrecta = request.getParameter("razaCorrecta");
        
        boolean esCorrecta = respuesta != null && respuesta.equals(razaCorrecta);
        request.setAttribute("resultado", esCorrecta ? "¡Correcto!" : "Incorrecto");

        // Usar el servicio de puntuación para actualizar/insertar correctamente
        UserBean user = (UserBean) session.getAttribute("sessionUser");
        PuntuacionDto ultimaPuntuacion = null;
        if (user != null) {
            try {
                // Usar el servicio que maneja correctamente updates/inserts
                PuntuacionService puntuacionService = new PuntuacionService();
                puntuacionService.set(user.getId(), esCorrecta);
                
                // Obtener la puntuación actualizada del usuario
                PuntuacionDao puntuacionDao = new PuntuacionDao(
                    net.ausiasmarch.shared.connection.HikariPool.getConnection()
                );
                ultimaPuntuacion = puntuacionDao.get(user.getId());
                
                // Actualizar la sesión con la puntuación actual
                if (ultimaPuntuacion != null) {
                    session.setAttribute("puntuacion", ultimaPuntuacion.getScore());
                }
                
            } catch (Exception e) {
                System.err.println("Error actualizando puntuación en dog_score: " + e.getMessage());
            }
        }
        
        // Establecer atributos para la página de puntuación
        request.setAttribute("country", "Raza de Perro"); // Para el contexto del juego de perros
        request.setAttribute("capitalGuess", respuesta);
        request.setAttribute("correctCapital", razaCorrecta);
        request.setAttribute("userScore", ultimaPuntuacion);
        
        // Obtener y establecer las mejores puntuaciones
        try {
            PuntuacionService puntuacionService = new PuntuacionService();
            java.util.List<PuntuacionDto> highScoresList = puntuacionService.getHighScores();
            request.setAttribute("highScores", highScoresList);
        } catch (SQLException e) {
            System.err.println("Error obteniendo mejores puntuaciones: " + e.getMessage());
        }
        
        // Usar forward en lugar de redirect para mantener los atributos
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("puntuacion.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            System.err.println("Error redirigiendo a puntuación: " + e.getMessage());
        }
    }
}
