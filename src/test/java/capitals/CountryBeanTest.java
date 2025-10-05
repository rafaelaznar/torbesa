package capitals;

import org.junit.Test;

import net.ausiasmarch.capitals.model.CountryBean;

import static org.junit.Assert.*;

public class CountryBeanTest {
    @Test
    public void testGettersAndSetters() {
        CountryBean country = new CountryBean("Spain", "Madrid");
        assertEquals("Spain", country.getName());
        assertEquals("Madrid", country.getCapital());
        country.setName("France");
        country.setCapital("Paris");
        assertEquals("France", country.getName());
        assertEquals("Paris", country.getCapital());
    }
}
