package tests.unit;

import org.junit.Assert;
import org.junit.Test;

import net.ausiasmarch.models.Country;

import java.util.Arrays;
import java.util.List;

public class CountryTest {
    @Test
    public void testCountryGettersSetters() {
        Country country = new Country();
        country.setId(1);
        country.setCountryName("France");
        List<String> borders = Arrays.asList("DEU", "ESP", "ITA");
        country.setBorders(borders);
        Assert.assertEquals(1, country.getId());
        Assert.assertEquals("France", country.getCountryName());
        Assert.assertEquals(borders, country.getBorders());
    }
}
