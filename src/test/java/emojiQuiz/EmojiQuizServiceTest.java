package emojiQuiz;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;

import org.junit.Test;

import net.ausiasmarch.emojiQuiz.model.EmojiQuizBean;
import net.ausiasmarch.emojiQuiz.service.EmojiQuizService;

public class EmojiQuizServiceTest {

    @Test
    public void testGetShuffledOptionsContainsAllUniqueChoices() {
        EmojiQuizBean question = new EmojiQuizBean(1, "🔴💊🕶️🧑‍💻💥", "The Matrix", "Toy Story", "Space Jam");
        EmojiQuizService service = new EmojiQuizService();

        List<String> options = service.getShuffledOptions(question);

        assertEquals(3, options.size());
        assertTrue(options.contains("Space Jam"));
        assertTrue(options.contains("Toy Story"));
        assertTrue(options.contains("The Matrix"));
        assertEquals(3, new HashSet<>(options).size());
    }

    @Test
    public void testGetCorrectAnswerReturnsExactAnswer() {
        EmojiQuizBean question = new EmojiQuizBean();
        question.setCorrectAnswer("Interstellar");
        EmojiQuizService service = new EmojiQuizService();
        assertEquals("Interstellar", service.getCorrectAnswer(question));
    }
}