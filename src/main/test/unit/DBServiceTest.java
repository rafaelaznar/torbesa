package tests.unit;

import org.junit.Assert;
import org.junit.Test;
import services.DBService;
import java.sql.Connection;

public class DBServiceTest {
    @Test
    public void testGetConnection() throws Exception {
        Connection conn = DBService.getConnection();
        Assert.assertNotNull(conn);
        conn.close();
    }
}
