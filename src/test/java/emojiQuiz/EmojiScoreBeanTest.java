package emojiQuiz;

import org.junit.Test;

import net.ausiasmarch.emojiQuiz.model.EmojiScoreBean;

import static org.junit.Assert.*;
import java.time.LocalDateTime;

public class EmojiScoreBeanTest {
    @Test
    public void testDefaultConstructor() {
        EmojiScoreBean score = new EmojiScoreBean();
        assertEquals(0, score.getId());
        assertEquals(0, score.getUserId());
        assertEquals(0, score.getScore());
        assertEquals(0, score.getTries());
        assertNull(score.getTimestamp());
    }

    @Test
    public void testParameterizedConstructor() {
        LocalDateTime now = LocalDateTime.now();
        EmojiScoreBean score = new EmojiScoreBean(1, 2, "alice", 10, 5, now);
        assertEquals(1, score.getId());
        assertEquals(2, score.getUserId());
        assertEquals(10, score.getScore());
        assertEquals(5, score.getTries());
        assertEquals(now, score.getTimestamp());
    }

    @Test
    public void testUsernameConstructor() {
        LocalDateTime now = LocalDateTime.now();
        EmojiScoreBean score = new EmojiScoreBean(1, 2, "alice", 10, 5, now);
        assertEquals("alice", score.getUsername());
    }
}
