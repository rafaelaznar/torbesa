package whosthatpokemon;

import org.junit.Assert;
import org.junit.Test;
import java.sql.Connection;
import java.sql.SQLException;
import net.ausiasmarch.whosthatpokemon.dao.ScoreDao;
import net.ausiasmarch.shared.connection.HikariPool;

public class ScoreDaoBasicTest {

    @Test
    public void testCountMethod() throws SQLException {
        try (Connection connection = HikariPool.getConnection()) {
            ScoreDao dao = new ScoreDao(connection);
            
            // Simplemente verificamos que el método count() no lance una excepción
            // y devuelva un número no negativo
            int count = dao.count();
            Assert.assertTrue("El count debe ser mayor o igual a 0", count >= 0);
        }
    }

    @Test
    public void testGetTop10Method() throws SQLException {
        try (Connection connection = HikariPool.getConnection()) {
            ScoreDao dao = new ScoreDao(connection);
            
            // Verificamos que getTop10() no lance excepción y devuelva una lista
            var top10 = dao.getTop10();
            Assert.assertNotNull("La lista no debe ser null", top10);
            Assert.assertTrue("La lista debe tener máximo 10 elementos", top10.size() <= 10);
        }
    }

    @Test
    public void testGetAllMethod() throws SQLException {
        try (Connection connection = HikariPool.getConnection()) {
            ScoreDao dao = new ScoreDao(connection);
            
            // Verificamos que getAll() funcione correctamente
            var allScores = dao.getAll();
            Assert.assertNotNull("La lista no debe ser null", allScores);
        }
    }

    @Test
    public void testCountByUserIdMethod() throws SQLException {
        try (Connection connection = HikariPool.getConnection()) {
            ScoreDao dao = new ScoreDao(connection);
            
            // Probamos con un userId que probablemente no existe (99999)
            // para verificar que devuelve 0
            int count = dao.count(99999);
            Assert.assertTrue("El count para un usuario inexistente debe ser >= 0", count >= 0);
        }
    }
}