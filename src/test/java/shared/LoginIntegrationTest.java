package shared;

import org.junit.Test;
import static org.junit.Assert.*;
import javax.servlet.http.*;
import org.mockito.Mockito;

import net.ausiasmarch.shared.controller.LoginServlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

public class LoginIntegrationTest extends Mockito {
    @Test
    public void testLoginSuccess() throws ServletException, IOException {
        LoginServlet servlet = new LoginServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getParameter("username")).thenReturn("alice");
        when(request.getParameter("password")).thenReturn("1");
        when(request.getSession()).thenReturn(session);
        servlet.doPost(request, response);
        verify(session).setAttribute(eq("sessionUser"), any());
    }

    @Test
    public void testLoginFail() throws ServletException, IOException {
        LoginServlet servlet = new LoginServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getParameter("username")).thenReturn("notfound");
        when(request.getParameter("password")).thenReturn("wrong");
        when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);
        servlet.doPost(request, response);
        // No session set for invalid login
        verify(dispatcher).forward(request, response);
    }
}
