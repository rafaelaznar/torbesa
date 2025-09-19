package capitals;

import net.ausiasmarch.capitals.controller.ScoreServlet;
import net.ausiasmarch.capitals.model.ScoreDto;
import net.ausiasmarch.capitals.service.ScoreService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class ScoreServletTest {
    private ScoreServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;
    private ScoreService scoreService;

    @Before
    public void setUp() {
        scoreService = mock(ScoreService.class);
        servlet = new ScoreServlet(scoreService);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
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
    public void testDoGetServletException() throws Exception {
        when(scoreService.getHighScores()).thenThrow(new RuntimeException("Internal error"));
        when(request.getRequestDispatcher("../shared/error.jsp")).thenReturn(dispatcher);
        servlet.doGet(request, response);
        verify(request).setAttribute(eq("errorMessage"), eq("Internal error"));
        verify(dispatcher).forward(request, response);
    }
}
