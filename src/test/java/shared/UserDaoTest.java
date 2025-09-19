package shared;

import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.*;
import net.ausiasmarch.capitals.dao.UserDao;
import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.shared.model.UserBean;

public class UserDaoTest {
    @Test
    public void testAuthenticateAndGetByUsername() throws Exception {
        HikariPool pool = new HikariPool();
        try (Connection conn = pool.getConnection()) {
            UserDao dao = new UserDao(conn);
            boolean exists = dao.authenticate("alice", "1");
            assertTrue(exists);
            UserBean user = dao.getByUsername("alice");
            assertNotNull(user);
            assertEquals("alice", user.getUsername());
        }
    }
}
