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
    RequestDispatcher dispatcher = mock(RequestDispatcher.class);
    when(request.getParameter("username")).thenReturn("alice");
    // SHA256 hash de "ausias": 7e4b4f5529e084ecafb996c891cfbd5b5284f5b00dc155c37bbb62a9f161a72e
    when(request.getParameter("password")).thenReturn("7e4b4f5529e084ecafb996c891cfbd5b5284f5b00dc155c37bbb62a9f161a72e");
    when(request.getSession()).thenReturn(session);
    when(request.getRequestDispatcher("login.jsp")).thenReturn(dispatcher);
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
