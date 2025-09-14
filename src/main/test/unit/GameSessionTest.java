package tests.unit;

import org.junit.Assert;
import org.junit.Test;

import net.ausiasmarch.models.GameSession;

import java.sql.Timestamp;

public class GameSessionTest {
    @Test
    public void testGameSessionGettersSetters() {
        GameSession gs = new GameSession();
        gs.setId(1);
        gs.setUserId(2);
        gs.setStartTime(Timestamp.valueOf("2025-09-13 10:00:00"));
        gs.setEndTime(Timestamp.valueOf("2025-09-13 10:10:00"));
        gs.setScore(20);
        Assert.assertEquals(1, gs.getId());
        Assert.assertEquals(2, gs.getUserId());
        Assert.assertEquals(Timestamp.valueOf("2025-09-13 10:00:00"), gs.getStartTime());
        Assert.assertEquals(Timestamp.valueOf("2025-09-13 10:10:00"), gs.getEndTime());
        Assert.assertEquals(20, gs.getScore());
    }
}
