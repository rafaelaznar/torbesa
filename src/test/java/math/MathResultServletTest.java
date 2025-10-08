package math;

import net.ausiasmarch.math.controller.MathResultServlet;
import net.ausiasmarch.math.model.MathScoreBean;
import net.ausiasmarch.shared.model.UserBean;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;

public class MathResultServletTest {

    private MathResultServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @Before
    public void setUp() {
        servlet = new MathResultServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
    }

    @Test
    public void testDoGetWithScores() throws ServletException, IOException {
        // Simular usuario en sesión
        UserBean user = new UserBean(1, "Hector", null);
        
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("sessionUser")).thenReturn(user);
        when(request.getRequestDispatcher("/math/highscores.jsp")).thenReturn(dispatcher);

        // Simular MathScoreBean en sesión
        MathScoreBean scoreBean = new MathScoreBean();
        scoreBean.setScore(5);
        scoreBean.setTries(3);
        scoreBean.setUsername("Hector");
        when(session.getAttribute("mathScore")).thenReturn(scoreBean);

        // Ejecutar servlet
        servlet.doGet(request, response);

        // Verificar comportamiento
        verify(request).setAttribute(eq("score"), eq(5));
        verify(request).setAttribute(eq("highscores"), any(List.class));
        verify(dispatcher).forward(request, response);
        verify(session).removeAttribute("mathScore");
        verify(response, never()).sendRedirect(anyString());
    }

    @Test
    public void testDoGetNoSessionRedirect() throws ServletException, IOException {
        when(request.getSession(false)).thenReturn(null);
        when(request.getContextPath()).thenReturn("/app");

        servlet.doGet(request, response);

        // Verifica redirección al login
        verify(response).sendRedirect("/app/index.jsp");
        verify(request, never()).getRequestDispatcher(anyString());
    }

    @Test
    public void testDoGetSessionWithoutUserRedirect() throws ServletException, IOException {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("sessionUser")).thenReturn(null);
        when(request.getContextPath()).thenReturn("/app");

        servlet.doGet(request, response);

        verify(response).sendRedirect("/app/index.jsp");
        verify(request, never()).getRequestDispatcher(anyString());
    }

    @Test
    public void testDoGetSessionWithoutMathScoreStillWorks() throws ServletException, IOException {
        // Usuario logueado pero sin MathScore en sesión
        UserBean user = new UserBean(2, "Ana", null);

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("sessionUser")).thenReturn(user);
        when(session.getAttribute("mathScore")).thenReturn(null);
        when(request.getRequestDispatcher("/math/highscores.jsp")).thenReturn(dispatcher);

        servlet.doGet(request, response);

        // Se debe mostrar score 0 y lista de highscores
        verify(request).setAttribute(eq("score"), eq(0));
        verify(request).setAttribute(eq("highscores"), any(List.class));
        verify(dispatcher).forward(request, response);
        verify(response, never()).sendRedirect(anyString());
    }
}
