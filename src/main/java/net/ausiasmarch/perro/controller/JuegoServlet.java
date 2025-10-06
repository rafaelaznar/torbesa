package net.ausiasmarch.perro.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ausiasmarch.perro.model.PerroBean;
import net.ausiasmarch.perro.service.PerroService;

@WebServlet("/perro/JuegoServlet")
public class JuegoServlet extends HttpServlet{

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        PerroBean user = (PerroBean) session.getAttribute("sessionUser");
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
        Integer puntuacion = (Integer) session.getAttribute("puntuacion");
        if (puntuacion == null) puntuacion = 0;
        boolean esCorrecta = respuesta != null && respuesta.equals(razaCorrecta);
        if (esCorrecta) {
            puntuacion++;
        } else {
            puntuacion--;
        }
        session.setAttribute("puntuacion", puntuacion);
        request.setAttribute("resultado", esCorrecta ? "¡Correcto!" : "Incorrecto");
        // Nueva pregunta
        PerroService.PreguntaRaza pregunta = new PerroService(getServletContext()).obtenerPreguntaRaza();
        request.setAttribute("imagenUrl", pregunta.imagenUrl);
        request.setAttribute("opciones", pregunta.opciones);
        request.setAttribute("razaCorrecta", pregunta.razaCorrecta);
        request.setAttribute("puntuacion", puntuacion);
        RequestDispatcher rd = request.getRequestDispatcher("/perro/juego.jsp");
        try {
            rd.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
