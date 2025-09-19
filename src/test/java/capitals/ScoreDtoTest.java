package capitals;

import org.junit.Test;

import net.ausiasmarch.capitals.model.ScoreDto;

import static org.junit.Assert.*;
import java.time.LocalDateTime;

public class ScoreDtoTest {
    @Test
    public void testDefaultConstructor() {
        ScoreDto score = new ScoreDto();
        assertEquals(0, score.getId());
        assertEquals(0, score.getUserId());
        assertEquals(0, score.getScore());
        assertEquals(0, score.getTries());
        assertNull(score.getTimestamp());
    }

    @Test
    public void testParameterizedConstructor() {
        LocalDateTime now = LocalDateTime.now();
        ScoreDto score = new ScoreDto(1, 2, 10, 5, now);
        assertEquals(1, score.getId());
        assertEquals(2, score.getUserId());
        assertEquals(10, score.getScore());
        assertEquals(5, score.getTries());
        assertEquals(now, score.getTimestamp());
    }

    @Test
    public void testUsernameConstructor() {
        LocalDateTime now = LocalDateTime.now();
        ScoreDto score = new ScoreDto(1, 2, 10, 5, now, "alice");
        assertEquals("alice", score.getUsername());
    }
}
