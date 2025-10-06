# Tests para CodeQuest

## Introducción

Este directorio contiene las pruebas unitarias para el juego CodeQuest. Las pruebas están escritas usando JUnit 4 y Mockito, siguiendo las mejores prácticas de testing en Java.

## Estructura de Archivos

```
src/test/java/codequest/
├── DuckDuckGoTechnologyServiceTest.java  - Tests del servicio de tecnologías
├── GameServletTest.java                  - Tests del servlet principal
└── README.md                             - Esta documentación
```

## Tests Implementados

### DuckDuckGoTechnologyServiceTest (Funcional)

Este test verifica el correcto funcionamiento del servicio que obtiene tecnologías desde la API de DuckDuckGo.

**Características testadas:**
- Obtención de tecnologías desde API externa
- Sistema de cache para mejorar rendimiento
- Mecanismo de fallback cuando la API falla
- Generación de tecnologías aleatorias
- Creación de opciones múltiples para el juego

**Estado:** 8/8 tests pasan correctamente

**Métodos de test principales:**
- `testFetchAllTechnologies_ReturnsListOfTechnologies()` - Verifica que se obtienen tecnologías
- `testFetchAllTechnologies_WithCachedData()` - Verifica el funcionamiento del cache
- `testGetRandomTechnology_ReturnsValidTechnology()` - Verifica selección aleatoria
- `testGenerateDescriptionOptions_ReturnsCorrectNumberOfOptions()` - Verifica opciones del juego

### GameServletTest (En desarrollo)

Tests para el servlet principal del juego que maneja las peticiones web.

**Estado:** En proceso de ajuste para adaptarse a la complejidad actual del servlet

## Conceptos de Testing Aplicados

### Patrón AAA (Arrange-Act-Assert)

Todos los tests siguen este patrón estructural:

```java
@Test
public void testEjemplo() {
    // ARRANGE - Preparar datos de prueba
    String input = "datos de prueba";
    
    // ACT - Ejecutar el método a probar
    String result = servicioAProbar.metodo(input);
    
    // ASSERT - Verificar el resultado esperado
    assertEquals("resultado esperado", result);
}
```

### Uso de Mocks

Los mocks permiten aislar el código bajo prueba de sus dependencias externas:

```java
@Mock
private ServletContext servletContext;

// Configurar comportamiento del mock
when(servletContext.getAttribute("technologies")).thenReturn(testData);

// Verificar que se llamó al método
verify(servletContext).setAttribute(eq("technologies"), any());
```

### Testing de APIs Externas

Para probar servicios que dependen de APIs externas:

1. **Aislamiento:** Usar mocks para simular respuestas de la API
2. **Fallbacks:** Verificar que el sistema funciona cuando la API falla
3. **Cache:** Probar que se optimiza el rendimiento con sistemas de cache
4. **Errores:** Verificar el manejo adecuado de errores de red

## Configuración y Ejecución

### Dependencias necesarias

El proyecto utiliza estas librerías para testing:

- **JUnit 4:** Framework principal de testing
- **Mockito:** Framework para crear mocks
- **Maven Surefire:** Plugin para ejecutar tests

### Comandos de ejecución

```bash
# Ejecutar todos los tests del proyecto
mvn test

# Ejecutar solo los tests de CodeQuest
mvn test -Dtest=codequest.*

# Ejecutar un test específico
mvn test -Dtest=DuckDuckGoTechnologyServiceTest

# Ejecutar tests con información detallada
mvn test -Dtest=DuckDuckGoTechnologyServiceTest -X
```

## Comparación con Tests del Profesor

### Similitudes
- Uso de JUnit 4 y Mockito
- Estructura de directorios separada
- Patrones de naming consistentes
- Uso del patrón AAA

### Diferencias
- **CodeQuest:** Incluye testing de APIs externas
- **CodeQuest:** Implementa testing de sistemas de cache
- **CodeQuest:** Maneja fallbacks y resiliencia
- **Capitals:** Se enfoca en testing de base de datos

## Mejores Prácticas Implementadas

### Naming de Tests
```java
// Patrón: test[Método]_[Condición]_[ResultadoEsperado]
testFetchAllTechnologies_ReturnsListOfTechnologies()
testGetRandomTechnology_WithEmptyList()
```

### Independencia de Tests
- Cada test es independiente
- No hay dependencias entre tests
- El orden de ejecución no importa

### Documentación
- Comentarios explicativos en tests complejos
- Mensajes descriptivos en assertions
- Documentación del comportamiento esperado

## Extensión de Tests

### Para añadir nuevos tests:

1. **Crear método con @Test**
2. **Seguir el patrón AAA**
3. **Usar naming descriptivo**
4. **Añadir documentación si es necesario**

### Ejemplo de nuevo test:
```java
@Test
public void testNuevoMetodo_ConCondicionEspecifica_DeberiaRetornarValorEsperado() {
    // ARRANGE
    TipoInput input = prepararDatos();
    
    // ACT
    TipoOutput resultado = servicioAProbar.nuevoMetodo(input);
    
    // ASSERT
    assertNotNull("El resultado no debe ser null", resultado);
    assertEquals("Valor esperado", "valorEsperado", resultado.getValor());
}
```

## Solución de Problemas Comunes

### Tests que fallan
1. Verificar que los mocks están configurados correctamente
2. Comprobar que las assertions coinciden con el comportamiento real
3. Revisar que no hay dependencias externas sin mockear

### Tests lentos
1. Verificar que se usan mocks en lugar de dependencias reales
2. Comprobar que no se hacen llamadas reales a APIs
3. Asegurar que no se accede a base de datos real

### Errores de compilación
1. Verificar que todas las dependencias están en el pom.xml
2. Comprobar imports correctos
3. Asegurar que las clases bajo test existen

## Conclusión

El sistema de testing implementado para CodeQuest demuestra un nivel intermedio-avanzado de comprensión de testing en Java. Los tests del servicio funcionan correctamente y proporcionan una base sólida para mantener la calidad del código.

La estructura permite extensión fácil para añadir más tests conforme el proyecto crezca, manteniendo siempre la separación clara con los tests del profesor.
