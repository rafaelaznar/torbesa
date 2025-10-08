package net.ausiasmarch.trivia;

import net.ausiasmarch.trivia.model.TriviaScoreDto;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TriviaScoreDtoTest {

    @Test
    public void testAllArgsConstructorAndGetters() {
        LocalDateTime now = LocalDateTime.now();
        TriviaScoreDto dto = new TriviaScoreDto(7, 12, 3, 20, 5, now);

        assertEquals(7, dto.getUserId());
        assertEquals(12, dto.getScore());
        assertEquals(3, dto.getStreak());
        assertEquals(20, dto.getBestScore());
        assertEquals(5, dto.getBestStreak());
        assertEquals(now, dto.getUpdatedAt());
    }

    @Test
    public void testSetters() {
        TriviaScoreDto dto = new TriviaScoreDto();

        assertEquals(0, dto.getUserId());
        assertEquals(0, dto.getScore());
        assertEquals(0, dto.getStreak());
        assertEquals(0, dto.getBestScore());
        assertEquals(0, dto.getBestStreak());
        assertNull(dto.getUpdatedAt());

        LocalDateTime when = LocalDateTime.of(2024, 6, 15, 10, 30);
        dto.setUserId(9);
        dto.setScore(18);
        dto.setStreak(4);
        dto.setBestScore(25);
        dto.setBestStreak(6);
        dto.setUpdatedAt(when);

        assertEquals(9, dto.getUserId());
        assertEquals(18, dto.getScore());
        assertEquals(4, dto.getStreak());
        assertEquals(25, dto.getBestScore());
        assertEquals(6, dto.getBestStreak());
        assertEquals(when, dto.getUpdatedAt());
    }
}
