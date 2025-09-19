package shared;

import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.*;
import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.shared.dao.UserDao;
import net.ausiasmarch.shared.model.UserBean;

public class UserDaoTest {
    @Test
    public void testCreateAndDeleteUser() throws Exception {
        HikariPool pool = new HikariPool();
        try (Connection conn = pool.getConnection()) {
            UserDao dao = new UserDao(conn);
            UserBean user = new UserBean(0, "testuser", "1234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef");
            int id = dao.create(user);
            assertTrue(id > 0);
            UserBean fetched = dao.getUserById(id);
            assertEquals("testuser", fetched.getUsername());
            dao.deleteUserById(id);
            try {
                dao.getUserById(id);
                fail("Expected ResourceNotFoundException");
            } catch (Exception e) {
                // ok
            }
        }
    }

    @Test
    public void testChangePasswordById() throws Exception {
        HikariPool pool = new HikariPool();
        try (Connection conn = pool.getConnection()) {
            UserDao dao = new UserDao(conn);
            UserBean user = new UserBean(0, "changepass", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            int id = dao.create(user);
            dao.changePasswordById(id, "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
            UserBean updated = dao.getUserById(id);
            assertEquals("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb", updated.getPassword());
            dao.deleteUserById(id);
        }
    }

    @Test
    public void testIsPresent() throws Exception {
        HikariPool pool = new HikariPool();
        try (Connection conn = pool.getConnection()) {
            UserDao dao = new UserDao(conn);
            UserBean user = new UserBean(0, "ispresent", "cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc");
            int id = dao.create(user);
            assertTrue(dao.isPresent("ispresent"));
            assertTrue(dao.isPresent("ispresent", "cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc"));
            dao.deleteUserById(id);
        }
    }

    @Test
    public void testGetByUsernameNotFound() throws Exception {
        HikariPool pool = new HikariPool();
        try (Connection conn = pool.getConnection()) {
            UserDao dao = new UserDao(conn);
            try {
                dao.getByUsername("notfounduser");
                fail("Expected ResourceNotFoundException");
            } catch (Exception e) {
                // ok
            }
        }
    }

    @Test
    public void testGetUserByIdNotFound() throws Exception {
        HikariPool pool = new HikariPool();
        try (Connection conn = pool.getConnection()) {
            UserDao dao = new UserDao(conn);
            try {
                dao.getUserById(-9999);
                fail("Expected ResourceNotFoundException");
            } catch (Exception e) {
                // ok
            }
        }
    }
}
