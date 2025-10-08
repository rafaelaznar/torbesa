package emojiQuiz;

import org.junit.Test;

import net.ausiasmarch.emojiQuiz.model.EmojiQuizBean;

import static org.junit.Assert.*;

public class EmojiQuizBeanTest {
    @Test
    public void testGettersAndSetters() {
        EmojiQuizBean question = new EmojiQuizBean(2, "👍🧩📦", "box", "puzzle", "like");
        assertEquals(2, question.getId());
        assertEquals("👍🧩📦", question.getQuestion());
        assertEquals("box", question.getCorrectAnswer());
        assertEquals("puzzle", question.getOption1());
        assertEquals("like", question.getOption2());

        question.setId(5);
        question.setQuestion("👉⚙️🧾");
        question.setCorrectAnswer("ajustes");
        question.setOption1("mano");
        question.setOption2("libreta");

        assertEquals(5, question.getId());
        assertEquals("👉⚙️🧾", question.getQuestion());
        assertEquals("ajustes", question.getCorrectAnswer());
        assertEquals("mano", question.getOption1());
        assertEquals("libreta", question.getOption2());
    }
}
