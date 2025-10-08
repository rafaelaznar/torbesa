package sempertegui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import net.ausiasmarch.sempertegui.service.LanguageService;

public class LanguageServiceTest {

    @Test
    public void testTranslateWord() {
        String result = LanguageService.translateWord("Rojo");
        assertNotNull(result);
        assertFalse(result.equals("error"));
        assertTrue(result.toLowerCase().contains("red"));
    }
}
