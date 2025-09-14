package tests.unit;

import org.junit.Assert;
import org.junit.Test;

import net.ausiasmarch.models.User;

public class UserTest {
    @Test
    public void testUserGettersSetters() {
        User user = new User();
        user.setId(1);
        user.setUsername("alice");
        user.setPassword("password123");
        Assert.assertEquals(1, user.getId());
        Assert.assertEquals("alice", user.getUsername());
        Assert.assertEquals("password123", user.getPassword());
    }
}
