package perro;

import java.sql.Connection;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import net.ausiasmarch.perro.dao.PuntuacionDao;
import net.ausiasmarch.perro.model.PuntuacionDto;
import net.ausiasmarch.shared.connection.HikariPool;

public class JuegoServletTest {
     @Test
    public void testInsertPuntuacion() throws Exception {
        HikariPool pool = new HikariPool();
        try (Connection conn = pool.getConnection()) {
            PuntuacionDao dao = new PuntuacionDao(conn);
            
            // Preparar datos de test
            PuntuacionDto nuevaPuntuacion = new PuntuacionDto();
            nuevaPuntuacion.setUserId(1); // Asumiendo que existe usuario con ID 1
            nuevaPuntuacion.setScore(5);
            nuevaPuntuacion.setTries(10);
            nuevaPuntuacion.setTimestamp(LocalDateTime.now());

            // Ejecutar inserción
            int result = dao.insert(nuevaPuntuacion);

            // Verificar que la inserción fue exitosa
            assertTrue("La inserción debería retornar un valor mayor a 0", result > 0);

            // Verificar que el registro se insertó correctamente
            PuntuacionDto puntuacionRecuperada = dao.get(1);
            assertNotNull("Debería poder recuperar la puntuación insertada", puntuacionRecuperada);
            assertEquals("El score debería ser 5", 5, puntuacionRecuperada.getScore());

            // Limpiar - eliminar el registro de test
            if (puntuacionRecuperada != null) {
                dao.delete(puntuacionRecuperada.getId());
            }
        }
    }

    @Test
    public void testCountPuntuaciones() throws Exception {
        HikariPool pool = new HikariPool();
        try (Connection conn = pool.getConnection()) {
            PuntuacionDao dao = new PuntuacionDao(conn);
            
            int countInicial = dao.count(1);
            
            // Insertar una puntuación
            PuntuacionDto nuevaPuntuacion = new PuntuacionDto();
            nuevaPuntuacion.setUserId(1);
            nuevaPuntuacion.setScore(1);
            nuevaPuntuacion.setTries(1);
            nuevaPuntuacion.setTimestamp(LocalDateTime.now());

            dao.insert(nuevaPuntuacion);

            int countDespues = dao.count(1);
            assertEquals("El count debería incrementar en 1", countInicial + 1, countDespues);

            // Limpiar
            PuntuacionDto puntuacionInsertada = dao.get(1);
            if (puntuacionInsertada != null) {
                dao.delete(puntuacionInsertada.getId());
            }
        }
    }
}
