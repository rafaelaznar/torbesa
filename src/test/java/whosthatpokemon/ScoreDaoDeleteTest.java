package whosthatpokemon;

import net.ausiasmarch.whosthatpokemon.dao.ScoreDao;
import net.ausiasmarch.whosthatpokemon.model.ScoreBean;
import net.ausiasmarch.shared.connection.HikariPool;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class ScoreDaoDeleteTest {
    @Test
    public void testDeleteScore() throws SQLException {
        try (Connection conn = HikariPool.getConnection()) {
            ScoreDao dao = new ScoreDao(conn);
            // Insertar un score de prueba
            ScoreBean score = new ScoreBean(0, 99, 10, 5, "testuser"); // id=0 para autoincrement, user_id=99, username dummy
            int insertedId = dao.insert(score);
            Assert.assertTrue(insertedId > 0);
            // Eliminar el score
            int deletedRows = dao.delete(insertedId);
            Assert.assertEquals(1, deletedRows);
            // Comprobar que ya no existe
            ScoreBean deletedScore = dao.get(99);
            Assert.assertNull(deletedScore);
        }
    }
}
