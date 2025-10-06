package codequest;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;

import net.ausiasmarch.codequest.controller.GameServlet;

/**
 * Test class para GameServlet de CodeQuest
 * 
 * Este test verifica que el servlet funcione correctamente tanto para
 * mostrar preguntas (GET) como para procesar respuestas (POST)
 */
@RunWith(MockitoJUnitRunner.class)
public class GameServletTest {

    @Mock
    private HttpServletRequest request;
    
    @Mock
    private HttpServletResponse response;
    
    @Mock
    private HttpSession session;
    
    @Mock
    private ServletContext servletContext;
    
    @Mock
    private ServletConfig servletConfig;
    
    @Mock
    private RequestDispatcher requestDispatcher;

    private GameServlet gameServlet;
    private StringWriter stringWriter;
    private PrintWriter printWriter;

    @Before
    public void setUp() throws IOException {
        gameServlet = new GameServlet();
        
        // Configurar mocks básicos
        when(request.getSession()).thenReturn(session);
        when(session.getServletContext()).thenReturn(servletContext);
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        
        // Configurar PrintWriter para capturar salida
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
        
        // Configurar RequestDispatcher
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet_ShowsRandomTechnology() throws ServletException, IOException {
        // Arrange - Preparar datos de prueba
        List<String> technologies = List.of("Java", "Python", "JavaScript");
        when(servletContext.getAttribute("technologies")).thenReturn(technologies);
        
        // Act - Ejecutar el método
        gameServlet.doGet(request, response);
        
        // Assert - Verificar resultados
        verify(session).setAttribute(eq("currentTechnology"), anyString());
        verify(request).getRequestDispatcher("/codequest/game.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGet_InitializesTechnologiesIfNull() throws ServletException, IOException {
        // Arrange - ServletContext sin tecnologías
        when(servletContext.getAttribute("technologies")).thenReturn(null);
        
        // Act
        gameServlet.doGet(request, response);
        
        // Assert - Debe inicializar las tecnologías
        verify(servletContext).setAttribute(eq("technologies"), any(List.class));
        verify(session).setAttribute(eq("currentTechnology"), anyString());
    }

    @Test
    public void testDoPost_CorrectAnswer() throws ServletException, IOException {
        // Arrange
        when(session.getAttribute("currentTechnology")).thenReturn("Java");
        when(request.getParameter("answer")).thenReturn("java"); // respuesta correcta
        
        // Act
        gameServlet.doPost(request, response);
        
        // Assert
        verify(request).setAttribute("result", "¡Correcto! La tecnología era Java");
        verify(request).setAttribute("isCorrect", true);
        verify(request).getRequestDispatcher("/codequest/game.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost_IncorrectAnswer() throws ServletException, IOException {
        // Arrange
        when(session.getAttribute("currentTechnology")).thenReturn("Java");
        when(request.getParameter("answer")).thenReturn("python"); // respuesta incorrecta
        
        // Act
        gameServlet.doPost(request, response);
        
        // Assert
        verify(request).setAttribute("result", "Incorrecto. La tecnología era Java");
        verify(request).setAttribute("isCorrect", false);
        verify(request).getRequestDispatcher("/codequest/game.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost_CaseInsensitiveComparison() throws ServletException, IOException {
        // Arrange - Probar que la comparación ignora mayúsculas/minúsculas
        when(session.getAttribute("currentTechnology")).thenReturn("JavaScript");
        when(request.getParameter("answer")).thenReturn("JAVASCRIPT");
        
        // Act
        gameServlet.doPost(request, response);
        
        // Assert
        verify(request).setAttribute("result", "¡Correcto! La tecnología era JavaScript");
        verify(request).setAttribute("isCorrect", true);
    }

    @Test
    public void testDoPost_EmptyAnswer() throws ServletException, IOException {
        // Arrange
        when(session.getAttribute("currentTechnology")).thenReturn("Python");
        when(request.getParameter("answer")).thenReturn(""); // respuesta vacía
        
        // Act
        gameServlet.doPost(request, response);
        
        // Assert
        verify(request).setAttribute("result", "Incorrecto. La tecnología era Python");
        verify(request).setAttribute("isCorrect", false);
    }

    @Test
    public void testDoPost_NullAnswer() throws ServletException, IOException {
        // Arrange
        when(session.getAttribute("currentTechnology")).thenReturn("React");
        when(request.getParameter("answer")).thenReturn(null); // respuesta null
        
        // Act
        gameServlet.doPost(request, response);
        
        // Assert
        verify(request).setAttribute("result", "Incorrecto. La tecnología era React");
        verify(request).setAttribute("isCorrect", false);
    }
}
