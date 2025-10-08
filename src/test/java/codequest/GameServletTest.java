package codequest;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

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
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when; // ✅ Import añadido
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
        // ✅ Uso de lenient() para evitar UnnecessaryStubbingException
        lenient().when(request.getServletContext()).thenReturn(servletContext);
        lenient().when(servletConfig.getServletContext()).thenReturn(servletContext);
        lenient().when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        // Configurar PrintWriter para capturar salida
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
        lenient().when(response.getWriter()).thenReturn(printWriter);

        // Inicializar el servlet con el config mockeado
        gameServlet = new GameServlet();
        try {
            gameServlet.init(servletConfig);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDoGet_ShowsRandomTechnology() throws ServletException, IOException {
        // Arrange: usuario logueado
        when(session.getAttribute("sessionUser"))
            .thenReturn(new net.ausiasmarch.shared.model.UserBean(1, "testuser", "testpass"));
        when(request.getSession()).thenReturn(session);

        // Act
        gameServlet.doGet(request, response);

        // Assert: verifica que se setean los atributos esperados y se reenvía a game.jsp
        verify(request).setAttribute(eq("sessionUser"), any());
        verify(request).setAttribute(eq("technology"), any());
        verify(request).setAttribute(eq("options"), any());
        verify(request).getRequestDispatcher("game.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGet_InitializesTechnologiesIfNull() throws ServletException, IOException {
        // Arrange: usuario logueado y sin tecnologías
        when(session.getAttribute("sessionUser"))
            .thenReturn(new net.ausiasmarch.shared.model.UserBean(1, "testuser", "testpass"));
        when(request.getSession()).thenReturn(session);

        // Act
        gameServlet.doGet(request, response);

        // Assert: verifica que se setean los atributos esperados
        verify(request).setAttribute(eq("sessionUser"), any());
        verify(request).setAttribute(eq("technology"), any());
        verify(request).setAttribute(eq("options"), any());
        verify(request).getRequestDispatcher("game.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost_IncorrectAnswer() throws ServletException, IOException {
        // Arrange
        net.ausiasmarch.shared.model.UserBean user =
            new net.ausiasmarch.shared.model.UserBean(1, "testuser", "testpass");
        when(session.getAttribute("sessionUser")).thenReturn(user);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("technology")).thenReturn("Java");
        when(request.getParameter("descriptionGuess")).thenReturn("Descripción incorrecta");

        // Act
        gameServlet.doPost(request, response);

        // Assert
        verify(request).setAttribute(eq("message"), org.mockito.ArgumentMatchers.contains("Incorrecto"));
        verify(request).setAttribute(eq("isCorrect"), eq(false));
        verify(request).getRequestDispatcher("scores.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost_CaseInsensitiveComparison() throws ServletException, IOException {
        // Arrange: la comparación de descripción debería ser case-insensitive
        net.ausiasmarch.shared.model.UserBean user =
            new net.ausiasmarch.shared.model.UserBean(1, "testuser", "testpass");
        when(session.getAttribute("sessionUser")).thenReturn(user);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("technology")).thenReturn("JavaScript");
        when(request.getParameter("descriptionGuess")).thenReturn("DESCRIPCIÓN DE JAVASCRIPT");

        // Act
        gameServlet.doPost(request, response);

        // Assert
        verify(request).setAttribute(eq("isCorrect"), any());
        verify(request).getRequestDispatcher("scores.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost_EmptyAnswer() throws ServletException, IOException {
        // Arrange
        net.ausiasmarch.shared.model.UserBean user =
            new net.ausiasmarch.shared.model.UserBean(1, "testuser", "testpass");
        when(session.getAttribute("sessionUser")).thenReturn(user);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("technology")).thenReturn("Python");
        when(request.getParameter("descriptionGuess")).thenReturn("");

        // Act
        gameServlet.doPost(request, response);

        // Assert
        verify(request).setAttribute(eq("isCorrect"), eq(false));
        verify(request).getRequestDispatcher("scores.jsp");
        verify(requestDispatcher).forward(request, response);
    }

}
