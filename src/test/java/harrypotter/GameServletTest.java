package harrypotter;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import net.ausiasmarch.harrypotter.controller.HarryPotterGameServlet;
import net.ausiasmarch.shared.model.UserBean;

public class GameServletTest {
    
    // Test-friendly subclass to expose protected methods
    private static class TestableHarryPotterGameServlet extends HarryPotterGameServlet {
        @Override
        public void doGet(HttpServletRequest request, HttpServletResponse response) {
            super.doGet(request, response);
        }
        
        @Override
        public void doPost(HttpServletRequest request, HttpServletResponse response) 
                throws ServletException, IOException {
            super.doPost(request, response);
        }
    }
    
    private TestableHarryPotterGameServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private ServletContext context;

    @Before
    public void setUp() {
        servlet = new TestableHarryPotterGameServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
        context = mock(ServletContext.class);
        when(request.getSession()).thenReturn(session);
        when(request.getServletContext()).thenReturn(context);
    }

    @Test
    public void testDoGetRedirectsIfNoSessionUser() throws Exception {
        when(session.getAttribute("sessionUser")).thenReturn(null);
        servlet.doGet(request, response);
        verify(response).sendRedirect("../shared/login.jsp");
    }

    @Test
    public void testDoGetForwardsIfSessionUser() throws Exception {
        UserBean user = new UserBean(1, "testuser", "hash");
        when(session.getAttribute("sessionUser")).thenReturn(user);
        when(request.getRequestDispatcher("game.jsp")).thenReturn(dispatcher);
        servlet.doGet(request, response);
        verify(request).setAttribute(eq("sessionUser"), eq(user));
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testDoPostRedirectsIfNoSessionUser() throws Exception {
        when(session.getAttribute("sessionUser")).thenReturn(null);
        servlet.doPost(request, response);
        verify(response).sendRedirect("../shared/login.jsp");
    }

    @Test
    public void testDoPostSetsAttributesAndForwards() throws Exception {
        UserBean user = new UserBean(1, "testuser", "hash");
        when(session.getAttribute("sessionUser")).thenReturn(user);
        when(request.getParameter("characterName")).thenReturn("Harry Potter");
        when(request.getParameter("houseGuess")).thenReturn("");
        when(request.getRequestDispatcher("result.jsp")).thenReturn(dispatcher);
        // El resto de dependencias (servicios, DAOs) deberían ser mockeadas para un test más robusto
        servlet.doPost(request, response);
        verify(request).setAttribute(eq("username"), eq("testuser"));
        verify(dispatcher).forward(request, response);
    }
}