package trivial;

import static org.junit.Assert.*;
import org.junit.Test;

import net.ausiasmarch.trivial.model.TrivialBean;

import java.util.Arrays;

public class TrivialBeanTest {

    @Test
    public void testGettersAndSetters() {
        TrivialBean question = new TrivialBean(
            "Baron Silas Greenback is the arch nemesis of which cartoon hero?",
            Arrays.asList("Bananaman", "SuperTed", "Captain Star", "Danger Mouse"),
            "Danger Mouse"
        );

        
        assertEquals("Baron Silas Greenback is the arch nemesis of which cartoon hero?", question.getQuestion());
        assertTrue(question.getOptions().contains("Bananaman"));
        assertTrue(question.getOptions().contains("SuperTed"));
        assertTrue(question.getOptions().contains("Captain Star"));
        assertTrue(question.getOptions().contains("Danger Mouse"));
        assertEquals("Danger Mouse", question.getCorrectAnswer());

    }
}


 
