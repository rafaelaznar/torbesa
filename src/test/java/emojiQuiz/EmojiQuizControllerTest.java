package emojiQuiz;

import static org.mockito.Mockito.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ausiasmarch.emojiQuiz.controller.EmojiQuizController;
import net.ausiasmarch.shared.model.UserBean;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

public class EmojiQuizControllerTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher dispatcher;

    private EmojiQuizController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new EmojiQuizController();
    }

    @Test
    public void doGetWithoutUserRedirectsToLogin() throws IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("sessionUser")).thenReturn(null);

        controller.doGet(request, response);

        verify(response).sendRedirect("../shared/login.jsp");
        verify(request, never()).setAttribute(eq("sessionUser"), any());
    }

    @Test
    public void doPostMissingSelectionForwardsBackToGame() throws ServletException, IOException {
        UserBean user = new UserBean(1, "player", "secret");

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("sessionUser")).thenReturn(user);
        when(request.getParameter("selectedOption")).thenReturn(null);
        when(request.getParameter("questionId")).thenReturn("42");
        when(request.getRequestDispatcher("/emojiQuiz/game.jsp")).thenReturn(dispatcher);

        controller.doPost(request, response);

        verify(request).setAttribute("username", user.getUsername());
        verify(request).setAttribute("message", "Choose an option and try again.");
        verify(dispatcher).forward(request, response);
        verify(response, never()).sendRedirect(anyString());
    }
}