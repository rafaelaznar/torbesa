package shared;

import org.junit.Test;

import net.ausiasmarch.shared.model.UserBean;

import static org.junit.Assert.*;

public class UserBeanTest {
    @Test
    public void testGettersAndSetters() {
        UserBean user = new UserBean(1, "alice", "pass");
        assertEquals(1, user.getId());
        assertEquals("alice", user.getUsername());
        assertEquals("pass", user.getPassword());
        user.setId(2);
        user.setUsername("bob");
        user.setPassword("secret");
        assertEquals(2, user.getId());
        assertEquals("bob", user.getUsername());
        assertEquals("secret", user.getPassword());
    }
}
