package codequest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;

import net.ausiasmarch.codequest.model.TechnologyBean;
import net.ausiasmarch.codequest.service.TechnologyService;

public class TechnologyServiceTest {

    private TechnologyService techService;
    private ServletContext mockContext;

    @Before
    public void setUp() {
        mockContext = mock(ServletContext.class);
        techService = new TechnologyService(mockContext);
    }

    @Test
    public void testFetchAllTechnologies() {
        List<TechnologyBean> techList = techService.fetchAllTechnologies();
        assertNotNull("La lista no debe ser nula", techList);
        assertTrue("Debe tener al menos una tecnología", techList.size() > 0);
    }

    @Test
    public void testGetRandomTechnologyExcluding() {
        List<String> excluded = new ArrayList<>();
        excluded.add("Java"); // Suponiendo que Java está en tu JSON
        TechnologyBean tech = techService.getRandomTechnologyExcluding(excluded);
        assertNotNull("Debe devolver una tecnología válida", tech);
        assertFalse("No debe estar en la lista de excluidos", excluded.contains(tech.getName()));
    }

    @Test
    public void testGetRandomDescriptionsForTest() {
        TechnologyBean tech = techService.getOneRandomTechnology();
        List<String> options = techService.getRandomDescriptionsForTest(tech, 4);
        assertNotNull("Debe devolver opciones", options);
        assertEquals("Debe devolver 4 opciones", 4, options.size());
        assertTrue("Debe contener la descripción correcta", options.contains(tech.getDescription()));
    }
}
