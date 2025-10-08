
package find_it_Alvaro;

import org.junit.Test;
import static org.junit.Assert.*;
import net.ausiasmarch.find_it_Alvaro.model.CharacterBean;

public class Honkai_test {
	@Test
	public void testGettersAndSetters() {
		CharacterBean character = new CharacterBean("1", "Kafka", 5, "Lightning");
		assertEquals("1", character.getId());
		assertEquals("Kafka", character.getName());
		assertEquals(5, character.getRarity());
		assertEquals("Lightning", character.getElement());

		character.setId("2");
		character.setName("Blade");
		character.setRarity(5);
		character.setElement("Wind");

		assertEquals("2", character.getId());
		assertEquals("Blade", character.getName());
		assertEquals(5, character.getRarity());
		assertEquals("Wind", character.getElement());
	}
}


