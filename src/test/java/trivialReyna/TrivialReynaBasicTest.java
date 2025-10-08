package trivialReyna;

import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.ausiasmarch.trivialReyna.service.QuestionService;
import net.ausiasmarch.trivialReyna.model.QuestionBean;

public class TrivialReynaBasicTest {

    @Test
    public void testGetOneRandomQuestionFromContext() {
        ServletContext mockContext = Mockito.mock(ServletContext.class);
        List<QuestionBean> questions = new ArrayList<>();
        questions.add(new QuestionBean("¿Cuál es la respuesta?", "Correcta", Arrays.asList("Correcta","Wrong1","Wrong2","Wrong3")));
        Mockito.when(mockContext.getAttribute("questions")).thenReturn(questions);

        QuestionService service = new QuestionService(mockContext);
        QuestionBean q = service.getOneRandomQuestion();
        assertNotNull(q);
        assertEquals("¿Cuál es la respuesta?", q.getQuestion());
    }
    
    @Test
    public void testGetRandomOptionsForTestIncludesCorrectAnswerAndCount() {
        ServletContext mockContext = Mockito.mock(ServletContext.class);
        QuestionBean question = new QuestionBean("Q", "Right", Arrays.asList("Right","A","B","C"));
        QuestionService service = new QuestionService(mockContext);
        ArrayList<String> options = service.getRandomOptionsForTest(question, 3);
        assertNotNull(options);
        assertEquals(3, options.size());
        assertTrue(options.contains("Right"));
    }
}
