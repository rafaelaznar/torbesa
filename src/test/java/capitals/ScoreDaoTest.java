package capitals;

import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.*;

import net.ausiasmarch.capitals.dao.ScoreDao;
import net.ausiasmarch.capitals.model.ScoreDto;
import net.ausiasmarch.shared.connection.HikariPool;
import java.util.List;

public class ScoreDaoTest {
    @Test
    public void testCountAndInsert() throws Exception {
        HikariPool pool = new HikariPool();
        try (Connection conn = pool.getConnection()) {
            ScoreDao dao = new ScoreDao(conn);
            int before = dao.count();
            ScoreDto dto = new ScoreDto(0, 1, 99, 1, java.time.LocalDateTime.now());
            int id = dao.insert(dto);
            assertTrue(id > 0);
            int after = dao.count();
            assertEquals(before + 1, after);
            dao.delete(id);
        }
    }

    @Test
    public void testGetTop10() throws Exception {
        HikariPool pool = new HikariPool();
        try (Connection conn = pool.getConnection()) {
            ScoreDao dao = new ScoreDao(conn);
            List<ScoreDto> top = dao.getTop10();
            assertNotNull(top);
            assertTrue(top.size() <= 10);
        }
    }
}
