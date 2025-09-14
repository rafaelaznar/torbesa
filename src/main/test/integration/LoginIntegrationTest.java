package tests.integration;

import org.junit.Assert;
import org.junit.Test;
import services.DBService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginIntegrationTest {
    @Test
    public void testValidLogin() throws Exception {
        Connection conn = DBService.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT id FROM users WHERE username = ? AND password = ?");
        stmt.setString(1, "alice");
        stmt.setString(2, "password123");
        ResultSet rs = stmt.executeQuery();
        Assert.assertTrue(rs.next());
        conn.close();
    }

    @Test
    public void testInvalidLogin() throws Exception {
        Connection conn = DBService.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT id FROM users WHERE username = ? AND password = ?");
        stmt.setString(1, "alice");
        stmt.setString(2, "wrongpass");
        ResultSet rs = stmt.executeQuery();
        Assert.assertFalse(rs.next());
        conn.close();
    }
}
