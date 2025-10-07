package starwars;

import org.junit.Test;

import net.ausiasmarch.starwars.model.CharacterBean;

import static org.junit.Assert.*;

public class CharacterBeanTest {
    @Test
    public void testCharacterBean() {
        CharacterBean character = new CharacterBean("Luke Skywalker", "http://swapi.dev/api/people/1/", 1, "Human");

        // Test getters
        assertEquals("Luke Skywalker", character.getName());
        assertEquals("http://swapi.dev/api/people/1/", character.getUrl());
        assertEquals(1, character.getId());
        assertEquals("Human", character.getSpecies());

        // Test setters
        character.setName("Darth Vader");
        character.setUrl("http://swapi.dev/api/people/4/");
        character.setId(4);
        character.setSpecies("Human");

        assertEquals("Darth Vader", character.getName());
        assertEquals("http://swapi.dev/api/people/4/", character.getUrl());
        assertEquals(4, character.getId());
        assertEquals("Human", character.getSpecies());
    }
}