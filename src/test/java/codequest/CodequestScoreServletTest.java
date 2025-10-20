package codequest;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import net.ausiasmarch.codequest.controller.ScoreServlet;
import net.ausiasmarch.codequest.model.ScoreDto;
import net.ausiasmarch.codequest.service.ScoreService;
import net.ausiasmarch.shared.model.UserBean;

public class CodequestScoreServletTest {
    private ScoreServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;
    private ScoreService scoreService;
    private HttpSession session;

    @Before
    public void setUp() {
        scoreService = mock(ScoreService.class);
        servlet = new ScoreServlet(scoreService);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testDoGetSuccess() throws Exception {
        List<ScoreDto> fakeScores = Arrays.asList(mock(ScoreDto.class), mock(ScoreDto.class));
        when(scoreService.getHighScores()).thenReturn(fakeScores);
        when(request.getRequestDispatcher("highscores.jsp")).thenReturn(dispatcher);
        servlet.doGet(request, response);
        verify(request).setAttribute(eq("highScores"), eq(fakeScores));
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testDoGetDatabaseError() throws Exception {
        when(scoreService.getHighScores()).thenThrow(new SQLException("DB error"));
        when(request.getRequestDispatcher("../shared/error.jsp")).thenReturn(dispatcher);
        servlet.doGet(request, response);
        verify(request).setAttribute(eq("errorMessage"), eq("Database error"));
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testDoGetRuntimeException() throws Exception {
        when(scoreService.getHighScores()).thenThrow(new RuntimeException("Internal error"));
        when(request.getRequestDispatcher("../shared/error.jsp")).thenReturn(dispatcher);
        servlet.doGet(request, response);
        verify(request).setAttribute(eq("errorMessage"), eq("Internal error"));
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testDoPostSuccess() throws Exception {
        UserBean user = new UserBean(1, "testuser", "pass");
        when(session.getAttribute("sessionUser")).thenReturn(user);
        when(request.getParameter("score")).thenReturn("150");
        when(scoreService.insert(1, 150)).thenReturn(true);
        doNothing().when(response).sendRedirect(anyString());
        servlet.doPost(request, response);
        verify(scoreService).insert(1, 150);
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testDoPostDatabaseError() throws Exception {
        UserBean user = new UserBean(1, "testuser", "pass");
        when(session.getAttribute("sessionUser")).thenReturn(user);
        when(request.getParameter("score")).thenReturn("150");
        when(scoreService.insert(1, 150)).thenThrow(new SQLException("DB error"));
        when(request.getRequestDispatcher("../shared/error.jsp")).thenReturn(dispatcher);
        servlet.doPost(request, response);
        verify(request).setAttribute(eq("errorMessage"), eq("Database error"));
        verify(dispatcher).forward(request, response);
    }
}
