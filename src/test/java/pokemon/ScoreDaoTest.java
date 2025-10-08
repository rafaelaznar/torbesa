package pokemon;

import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import net.ausiasmarch.pokemon.dao.ScoreDao;
import net.ausiasmarch.pokemon.model.ScoreDto;
import net.ausiasmarch.shared.connection.HikariPool;

public class ScoreDaoTest {
    @Test
    public void testCountAndInsert() throws Exception {
        try (Connection conn = HikariPool.getConnection()) {
            ScoreDao dao = new ScoreDao(conn);
            int before = dao.count();
            ScoreDto dto = new ScoreDto(0, 1, 150, 3, java.time.LocalDateTime.now());
            int result = dao.insert(dto);
            assertTrue(result > 0);
            int after = dao.count();
            assertEquals(before + 1, after);
            
           
            ScoreDto insertedScore = dao.get(1);
            if (insertedScore != null) {
                dao.delete(insertedScore.getId());
            }
        }
    }

    @Test
    public void testGetTop10() throws Exception {
        try (Connection conn = HikariPool.getConnection()) {
            ScoreDao dao = new ScoreDao(conn);
            List<ScoreDto> top = dao.getTop10();
            assertNotNull(top);
            assertTrue(top.size() <= 10);
        }
    }

    @Test
    public void testGetByUserId() throws Exception {
        try (Connection conn = HikariPool.getConnection()) {
            ScoreDao dao = new ScoreDao(conn);
            ScoreDto score = dao.get(1);
            if (score != null) {
                assertTrue(score.getUserId() > 0);
                assertTrue(score.getScore() >= 0);
                assertTrue(score.getTries() >= 0);
                assertNotNull(score.getTimestamp());
            }
        }
    }

    @Test
    public void testCountByUserId() throws Exception {
        try (Connection conn = HikariPool.getConnection()) {
            ScoreDao dao = new ScoreDao(conn);
            int count = dao.count(1);
            assertTrue(count >= 0);
        }
    }

    @Test
    public void testGetAll() throws Exception {
        try (Connection conn = HikariPool.getConnection()) {
            ScoreDao dao = new ScoreDao(conn);
            List<ScoreDto> allScores = dao.getAll();
            assertNotNull(allScores);
            for (ScoreDto score : allScores) {
                assertTrue(score.getId() > 0);
                assertTrue(score.getUserId() > 0);
                assertTrue(score.getScore() >= 0);
                assertTrue(score.getTries() >= 0);
                assertNotNull(score.getTimestamp());
                assertNotNull(score.getUsername());
            }
        }
    }

    @Test
    public void testUpdate() throws Exception {
        try (Connection conn = HikariPool.getConnection()) {
            ScoreDao dao = new ScoreDao(conn);
            ScoreDto dto = new ScoreDto(0, 1, 100, 2, java.time.LocalDateTime.now());
            dao.insert(dto);
            
            ScoreDto updateDto = new ScoreDto(0, 1, 200, 4, java.time.LocalDateTime.now());
            int result = dao.update(updateDto);
            assertTrue(result > 0);
            
            ScoreDto updatedScore = dao.get(1);
            if (updatedScore != null) {
                assertEquals(200, updatedScore.getScore());
                assertEquals(4, updatedScore.getTries());
            }
        }
    }
}
