package tests.unit;

import org.junit.Assert;
import org.junit.Test;

import net.ausiasmarch.models.Score;

import java.sql.Date;
import java.sql.Time;

public class ScoreTest {
    @Test
    public void testScoreGettersSetters() {
        Score score = new Score();
        score.setId(1);
        score.setUserId(2);
        score.setScore(15);
        score.setDate(Date.valueOf("2025-09-13"));
        score.setTime(Time.valueOf("10:00:00"));
        Assert.assertEquals(1, score.getId());
        Assert.assertEquals(2, score.getUserId());
        Assert.assertEquals(15, score.getScore());
        Assert.assertEquals(Date.valueOf("2025-09-13"), score.getDate());
        Assert.assertEquals(Time.valueOf("10:00:00"), score.getTime());
    }
}
