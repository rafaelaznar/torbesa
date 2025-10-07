package starwars;

import org.junit.Test;

import net.ausiasmarch.starwars.model.CharacterBean;

import static org.junit.Assert.*;

public class CharacterBeanTest {
    @Test
    public void testGettersAndSetters() {
        // Create a CharacterBean instance
        CharacterBean character = new CharacterBean("Luke Skywalker", "https://swapi.dev/api/people/1/", 1, 
                                                    "https://starwars-visualguide.com/assets/img/characters/1.jpg");
        
        // Test getters
        assertEquals("Luke Skywalker", character.getName());
        assertEquals("https://swapi.dev/api/people/1/", character.getUrl());
        assertEquals(1, character.getId());
        assertEquals("https://starwars-visualguide.com/assets/img/characters/1.jpg", character.getClue());

        // Update values
        character.setName("Darth Vader");
        character.setUrl("https://swapi.dev/api/people/4/");
        character.setId(4);
        character.setClue("https://starwars-visualguide.com/assets/img/characters/4.jpg");

        // Test updated values
        assertEquals("Darth Vader", character.getName());
        assertEquals("https://swapi.dev/api/people/4/", character.getUrl());
        assertEquals(4, character.getId());
        assertEquals("https://starwars-visualguide.com/assets/img/characters/4.jpg", character.getClue());
    }
}