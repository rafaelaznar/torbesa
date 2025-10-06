package codequest;

import java.util.List;

import javax.servlet.ServletContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;

import net.ausiasmarch.codequest.model.TechnologyBean;
import net.ausiasmarch.codequest.service.DuckDuckGoTechnologyService;

/**
 * Test class para DuckDuckGoTechnologyService
 * 
 * Este test verifica que el servicio funcione correctamente tanto
 * obteniendo tecnologías de la API como usando el fallback
 */
@RunWith(MockitoJUnitRunner.class)
public class DuckDuckGoTechnologyServiceTest {

    @Mock
    private ServletContext servletContext;

    private DuckDuckGoTechnologyService technologyService;

    @Before
    public void setUp() {
        // Crear el servicio con el mock del contexto
        technologyService = new DuckDuckGoTechnologyService(servletContext);
    }

    @Test
    public void testFetchAllTechnologies_ReturnsListOfTechnologies() {
        // Act - Ejecutar el método
        List<TechnologyBean> technologies = technologyService.fetchAllTechnologies();
        
        // Assert - Verificar que retorna una lista no vacía
        assertNotNull("La lista de tecnologías no debe ser null", technologies);
        assertFalse("La lista de tecnologías no debe estar vacía", technologies.isEmpty());
        assertTrue("Debe haber al menos 10 tecnologías", technologies.size() >= 10);
    }

    @Test
    public void testFetchAllTechnologies_ContainsValidTechnologyBeans() {
        // Act
        List<TechnologyBean> technologies = technologyService.fetchAllTechnologies();
        
        // Assert - Verificar que todas las tecnologías son válidas
        for (TechnologyBean tech : technologies) {
            assertNotNull("El TechnologyBean no debe ser null", tech);
            assertNotNull("El nombre no debe ser null", tech.getName());
            assertNotNull("La descripción no debe ser null", tech.getDescription());
            assertFalse("El nombre no debe estar vacío", tech.getName().trim().isEmpty());
            assertFalse("La descripción no debe estar vacía", tech.getDescription().trim().isEmpty());
            assertTrue("El nombre debe tener al menos 2 caracteres", tech.getName().length() >= 2);
        }
    }

    @Test
    public void testFetchAllTechnologies_WithCachedData() {
        // Arrange - Crear lista de tecnologías en caché
        TechnologyBean java = new TechnologyBean();
        java.setName("Java");
        java.setDescription("Lenguaje de programación orientado a objetos");
        
        TechnologyBean python = new TechnologyBean();
        python.setName("Python");
        python.setDescription("Lenguaje de programación de alto nivel");
        
        List<TechnologyBean> cachedTechnologies = List.of(java, python);
        when(servletContext.getAttribute("duckduckgo_technologies")).thenReturn(cachedTechnologies);
        
        // Act
        List<TechnologyBean> technologies = technologyService.fetchAllTechnologies();
        
        // Assert - Debe usar los datos en caché
        assertEquals("Debe retornar los datos en caché", cachedTechnologies, technologies);
        assertEquals("Debe tener 2 tecnologías", 2, technologies.size());
    }

    @Test
    public void testFetchAllTechnologies_SetsCache() {
        // Arrange - No hay datos en caché
        when(servletContext.getAttribute("duckduckgo_technologies")).thenReturn(null);
        
        // Act
        List<TechnologyBean> technologies = technologyService.fetchAllTechnologies();
        
        // Assert - Debe guardar en caché
        verify(servletContext).setAttribute(eq("duckduckgo_technologies"), eq(technologies));
        assertNotNull(technologies);
        assertFalse(technologies.isEmpty());
    }

    @Test
    public void testGetRandomTechnology_ReturnsValidTechnology() {
        // Act - Ejecutar varias veces para verificar que funciona
        TechnologyBean tech1 = technologyService.getRandomTechnology();
        TechnologyBean tech2 = technologyService.getRandomTechnology();
        TechnologyBean tech3 = technologyService.getRandomTechnology();
        
        // Assert
        assertNotNull("La tecnología aleatoria no debe ser null", tech1);
        assertNotNull("La tecnología aleatoria no debe ser null", tech2);
        assertNotNull("La tecnología aleatoria no debe ser null", tech3);
        
        assertNotNull("El nombre no debe ser null", tech1.getName());
        assertNotNull("El nombre no debe ser null", tech2.getName());
        assertNotNull("El nombre no debe ser null", tech3.getName());
        
        assertFalse("El nombre no debe estar vacío", tech1.getName().isEmpty());
        assertFalse("El nombre no debe estar vacío", tech2.getName().isEmpty());
        assertFalse("El nombre no debe estar vacío", tech3.getName().isEmpty());
    }

    @Test
    public void testGenerateDescriptionOptions_ReturnsCorrectNumberOfOptions() {
        // Arrange
        String correctDescription = "Lenguaje de programación orientado a objetos";
        
        // Act
        List<String> options = technologyService.generateDescriptionOptions(correctDescription);
        
        // Assert
        assertNotNull("Las opciones no deben ser null", options);
        assertEquals("Debe tener exactamente 4 opciones", 4, options.size());
        assertTrue("Debe contener la descripción correcta", options.contains(correctDescription));
    }

    @Test
    public void testGenerateDescriptionOptions_AllOptionsAreValid() {
        // Arrange
        String correctDescription = "Framework de JavaScript para interfaces de usuario";
        
        // Act
        List<String> options = technologyService.generateDescriptionOptions(correctDescription);
        
        // Assert - Verificar que todas las opciones son válidas
        for (String option : options) {
            assertNotNull("Ninguna opción debe ser null", option);
            assertFalse("Ninguna opción debe estar vacía", option.trim().isEmpty());
            assertTrue("Las opciones deben tener al menos 5 caracteres", option.length() >= 5);
        }
    }

    @Test
    public void testGenerateDescriptionOptions_ShufflesOptions() {
        // Este test verifica que las opciones se mezclan (no siempre en el mismo orden)
        // Arrange
        String correctDescription = "Base de datos NoSQL orientada a documentos";
        
        // Act - Generar opciones múltiples veces
        List<String> options1 = technologyService.generateDescriptionOptions(correctDescription);
        List<String> options2 = technologyService.generateDescriptionOptions(correctDescription);
        List<String> options3 = technologyService.generateDescriptionOptions(correctDescription);
        
        // Assert - Todas deben contener la descripción correcta
        assertTrue("Primera generación debe contener la respuesta correcta", options1.contains(correctDescription));
        assertTrue("Segunda generación debe contener la respuesta correcta", options2.contains(correctDescription));
        assertTrue("Tercera generación debe contener la respuesta correcta", options3.contains(correctDescription));
        
        // Al menos una de las listas debe tener un orden diferente (prueba probabilística)
        boolean ordersDiffer = !options1.equals(options2) || !options2.equals(options3) || !options1.equals(options3);
        assertTrue("Las opciones deben aparecer en orden aleatorio", ordersDiffer);
    }
}
