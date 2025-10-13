package codequest;

import java.util.List;
import javax.servlet.ServletContext;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;
import net.ausiasmarch.codequest.model.TechnologyBean;
import net.ausiasmarch.codequest.service.GitHubTechnologyService;

/**
 * Pruebas para el servicio de tecnologías de CodeQuest.
 * Verifica que se obtienen tecnologías y opciones correctamente.
 */
@RunWith(MockitoJUnitRunner.class)
public class GitHubTechnologyServiceTest {

    @Mock
    private ServletContext servletContext;

    private GitHubTechnologyService technologyService;

    @Before
    public void setUp() {
        technologyService = new GitHubTechnologyService(servletContext);
    }

    /**
     * Comprueba que se obtiene una lista válida de tecnologías.
     */
    @Test
    public void testFetchAllTechnologies_ReturnsListOfTechnologies() {
        List<TechnologyBean> technologies = technologyService.fetchAllTechnologies();
        assertNotNull("La lista de tecnologías no debe ser null", technologies);
        assertFalse("La lista de tecnologías no debe estar vacía", technologies.isEmpty());
        assertTrue("Debe haber al menos 5 tecnologías disponibles", technologies.size() >= 5);
        for (TechnologyBean tech : technologies) {
            assertNotNull("El nombre de la tecnología no debe ser null", tech.getName());
            assertNotNull("La descripción no debe ser null", tech.getDescription());
            assertNotNull("El tipo no debe ser null", tech.getType());
        }
    }

    /**
     * Comprueba que se obtiene una tecnología aleatoria válida.
     */
    @Test
    public void testGetRandomTechnology_ReturnsValidTechnology() {
        TechnologyBean technology = technologyService.getRandomTechnology();
        assertNotNull("La tecnología aleatoria no debe ser null", technology);
        assertNotNull("El nombre no debe ser null", technology.getName());
        assertNotNull("La descripción no debe ser null", technology.getDescription());
        assertTrue("El nombre no debe estar vacío", !technology.getName().trim().isEmpty());
    }

    /**
     * Comprueba que se generan 4 opciones de descripción para el juego.
     */
    @Test
    public void testGenerateDescriptionOptions_ReturnsCorrectNumberOfOptions() {
        String correctDescription = "Framework de JavaScript para interfaces de usuario";
        List<String> options = technologyService.generateDescriptionOptions(correctDescription);
        assertNotNull("Las opciones no deben ser null", options);
        assertEquals("Debe haber exactamente 4 opciones", 4, options.size());
        assertTrue("Las opciones deben incluir la descripcion correcta", 
                   options.contains(correctDescription));
    }
}
