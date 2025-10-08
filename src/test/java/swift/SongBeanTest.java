package swift;

import org.junit.Test;

import net.ausiasmarch.swift.model.SongBean;

import static org.junit.Assert.*;

public class SongBeanTest {
     @Test
    public void testGettersAndSetters() {
        SongBean song = new SongBean("hoax", "8");
        assertEquals("hoax", song.getCancion());
        assertEquals("8", song.getAlbum());
        song.setCancion("22");
        song.setAlbum("5");
        assertEquals("22", song.getCancion());
        assertEquals("5", song.getAlbum());
    }
}
