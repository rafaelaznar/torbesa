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
        // Mockeamos usuario
        UserBean user = mock(UserBean.class);
        when(user.getUsername()).thenReturn("Hector");
        when(user.getId()).thenReturn(1);

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("sessionUser")).thenReturn(user);
        when(request.getRequestDispatcher("/math/highscores.jsp")).thenReturn(dispatcher);

        // Simulamos MathScoreBean en sesión
        MathScoreBean scoreBean = new MathScoreBean();
        scoreBean.setScore(5);
        scoreBean.setTries(3);
        scoreBean.setUsername("Hector");
        when(session.getAttribute("mathScore")).thenReturn(scoreBean);

        servlet.doGet(request, response);

        // Verificamos que se haya agregado la puntuación y la lista de highscores
        verify(request).setAttribute(eq("score"), eq(5));
        verify(request).setAttribute(eq("highscores"), any(List.class));
        verify(dispatcher).forward(request, response);
        verify(session).removeAttribute("mathScore");
    }

    @Test
    public void testDoGetNoSessionRedirect() throws ServletException, IOException {
        when(request.getSession(false)).thenReturn(null);
        when(request.getContextPath()).thenReturn("/app");

        servlet.doGet(request, response);

        // Verificamos redirección al login
        verify(response).sendRedirect("/app/index.jsp");
    }

    @Test
    public void testDoGetSessionWithoutUserRedirect() throws ServletException, IOException {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("sessionUser")).thenReturn(null);
        when(request.getContextPath()).thenReturn("/app");

        servlet.doGet(request, response);

        verify(response).sendRedirect("/app/index.jsp");
    }
}
