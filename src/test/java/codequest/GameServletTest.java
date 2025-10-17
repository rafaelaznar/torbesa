package codequest;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import net.ausiasmarch.codequest.controller.GameServlet;
import net.ausiasmarch.shared.model.UserBean;

public class GameServletTest {
    private GameServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;

    @Before
    public void setUp() {
        servlet = new GameServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
    when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testDoGetRedirectsIfNoSessionUser() throws Exception {
        when(session.getAttribute("sessionUser")).thenReturn(null);
        servlet.doGet(request, response);
        verify(response).sendRedirect("../shared/login.jsp");
    }

    @Test
    public void testDoGetForwardsIfSessionUser() throws Exception {
        UserBean user = mock(UserBean.class);
        when(session.getAttribute("sessionUser")).thenReturn(user);
        when(request.getServletContext()).thenReturn(mock(ServletContext.class));
        when(request.getRequestDispatcher("game.jsp")).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(request).setAttribute(eq("sessionUser"), eq(user));
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testDoGetNoMoreTechnologies() throws Exception {
        UserBean user = mock(UserBean.class);
        when(session.getAttribute("sessionUser")).thenReturn(user);
        when(request.getServletContext()).thenReturn(mock(ServletContext.class));

        java.util.List<String> allTechs = java.util.Arrays.asList(
            "JavaScript","Python","Java","HTML","CSS","TypeScript","PHP","C#","Go","Rust","Ruby","Node.js","React","Vue.js","Angular","Svelte","Bootstrap","Tailwind CSS","Next.js","Nuxt.js","Spring Boot","Django","Flask","Express.js","Laravel","Ruby on Rails","ASP.NET Core","FastAPI","Gin","jQuery","Lodash","Axios","Redux","Vuex","RxJS","D3.js","Chart.js","Three.js","Moment.js","Spring Security","Hibernate","Jackson","Requests","SQLAlchemy","Pandas","NumPy","Eloquent","Gorm","Entity Framework","Meteor","T3 Stack","SvelteKit","Remix","Gatsby","Jest","JUnit","PyTest","PHPUnit","Cypress","Selenium"
        );
        when(session.getAttribute("shownTechnologies")).thenReturn(new java.util.ArrayList<>(allTechs));
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(request).setAttribute(eq("errorMessage"), anyString());
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testDoPostRedirectsIfNoSessionUser() throws Exception {
        when(session.getAttribute("sessionUser")).thenReturn(null);
        servlet.doPost(request, response);
        verify(response).sendRedirect("../shared/login.jsp");
    }

    @Test
    public void testDoPostSetsAttributesForCorrectAnswer() throws Exception {
        UserBean user = mock(UserBean.class);
        when(session.getAttribute("sessionUser")).thenReturn(user);
        when(user.getId()).thenReturn(1);
        when(request.getParameter("technology")).thenReturn("Java");
        when(request.getParameter("descriptionGuess"))
                .thenReturn("Lenguaje orientado a objetos para aplicaciones empresariales");
        when(request.getServletContext()).thenReturn(mock(ServletContext.class));
        when(request.getRequestDispatcher("scores.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("technology"), eq("Java"));
        verify(request).setAttribute(eq("descriptionGuess"), eq("Lenguaje orientado a objetos para aplicaciones empresariales"));
        verify(request).setAttribute(eq("correctDescription"), anyString());
        verify(request).setAttribute(eq("isCorrect"), eq(true));
        verify(request).setAttribute(eq("message"), anyString());
        verify(request).setAttribute(eq("userScore"), any());
        verify(request).setAttribute(eq("highScores"), any());
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testDoPostSetsAttributesForEmptyAnswer() throws Exception {
        UserBean user = mock(UserBean.class);
        when(session.getAttribute("sessionUser")).thenReturn(user);
        when(user.getId()).thenReturn(1);
        when(request.getParameter("technology")).thenReturn("Java");
        when(request.getParameter("descriptionGuess")).thenReturn("");
        when(request.getServletContext()).thenReturn(mock(ServletContext.class));
        when(request.getRequestDispatcher("scores.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("isCorrect"), eq(false));
        verify(request).setAttribute(eq("message"), anyString());
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testDoPostSetsAttributesForIncorrectAnswer() throws Exception {
        UserBean user = mock(UserBean.class);
        when(session.getAttribute("sessionUser")).thenReturn(user);
        when(user.getId()).thenReturn(1);
        when(request.getParameter("technology")).thenReturn("Java");
        when(request.getParameter("descriptionGuess")).thenReturn("Respuesta incorrecta");
        when(request.getServletContext()).thenReturn(mock(ServletContext.class));
        when(request.getRequestDispatcher("scores.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("isCorrect"), eq(false));
        verify(request).setAttribute(eq("message"), anyString());
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testDoPostUserScoreNullDefaults() throws Exception {
        UserBean user = mock(UserBean.class);
        when(session.getAttribute("sessionUser")).thenReturn(user);
        when(user.getId()).thenReturn(1);
        when(request.getParameter("technology")).thenReturn("Java");
        when(request.getParameter("descriptionGuess")).thenReturn("Cualquier cosa");
        when(request.getServletContext()).thenReturn(mock(ServletContext.class));
        when(request.getRequestDispatcher("scores.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("userScore"), any());
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testDoPostSQLException() throws Exception {
        UserBean user = mock(UserBean.class);
        when(session.getAttribute("sessionUser")).thenReturn(user);
        when(user.getId()).thenReturn(1);
        when(request.getParameter("technology")).thenReturn("Java");
        when(request.getParameter("descriptionGuess")).thenReturn("Cualquier cosa");
        when(request.getServletContext()).thenReturn(mock(ServletContext.class));
        when(request.getRequestDispatcher("../shared/error.jsp")).thenReturn(dispatcher);

        // Aquí podrías simular una excepción SQL desde el DAO
    }
}
