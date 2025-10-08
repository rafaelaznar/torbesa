package genshin;

import org.junit.Test;

import net.ausiasmarch.genshinPav.model.GenshinBean;

import static org.junit.Assert.*;

public class GenshinBeanTest {
    @Test
    public void testGettersAndSetters() {
        GenshinBean character = new GenshinBean("Diluc", "Pyro");
        assertEquals("Diluc", character.getName());
        assertEquals("Pyro", character.getVision());
        character.setName("Venti");
        character.setVision("Anemo");
        assertEquals("Venti", character.getName());
        assertEquals("Anemo", character.getVision());
    }
}
