package net.ausiasmarch.codequest.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;

import org.json.JSONObject;

import net.ausiasmarch.codequest.model.TechnologyBean;

/**
 * Servicio que utiliza la API de DuckDuckGo para obtener información sobre tecnologías.
 * Si la API falla, utiliza una lista predefinida de tecnologías.
 */
public class DuckDuckGoTechnologyService {

    private final ServletContext context;
    
    // Lista de tecnologías base que usamos si la API falla
    private static final List<TechnologyBean> FALLBACK_TECHNOLOGIES = new ArrayList<>();
    
    // Inicializamos las tecnologías de respaldo cuando se carga la clase
    static {
        initializeFallbackTechnologies();
    }

    /**
     * Constructor que recibe el contexto del servlet
     */
    public DuckDuckGoTechnologyService(ServletContext context) {
        this.context = context;
    }

    /**
     * MÉTODO PRINCIPAL: Obtiene todas las tecnologías disponibles
     * 
     * ESTRATEGIA:
     * 1. Primero verifica si ya tenemos tecnologías guardadas en el contexto
     * 2. Si no las tenemos, intenta obtenerlas de DuckDuckGo
     * 3. Si DuckDuckGo falla, usa las tecnologías predefinidas
     * 4. Siempre devuelve algo, nunca falla completamente
     */
    public List<TechnologyBean> fetchAllTechnologies() {
        // Verificar si ya tenemos tecnologías cargadas en memoria
        @SuppressWarnings("unchecked")
        List<TechnologyBean> cachedTechnologies = (List<TechnologyBean>) context.getAttribute("duckduckgo_technologies");
        
        if (cachedTechnologies != null && !cachedTechnologies.isEmpty()) {
            System.out.println("✓ Usando tecnologías ya cargadas en memoria");
            return cachedTechnologies;
        }

        System.out.println("→ Cargando tecnologías desde DuckDuckGo API...");
        List<TechnologyBean> technologies = new ArrayList<>();
        
        // Intentamos enriquecer algunas tecnologías con datos de DuckDuckGo
        technologies.addAll(enrichTechnologiesWithDuckDuckGo());
        
        // Si no conseguimos suficientes, añadimos las de respaldo
        if (technologies.size() < 5) {
            System.out.println("⚠ Pocas tecnologías obtenidas de API, añadiendo tecnologías de respaldo");
            technologies.addAll(FALLBACK_TECHNOLOGIES);
        }
        
        // Mezclamos para que aparezcan en orden aleatorio
        Collections.shuffle(technologies);
        
        // Guardamos en el contexto para no tener que volver a cargar
        context.setAttribute("duckduckgo_technologies", technologies);
        
        System.out.println("✓ Total de tecnologías cargadas: " + technologies.size());
        return technologies;
    }

    /**
     * Obtiene una tecnología aleatoria para el juego
     * Es el método que usa el GameServlet para cada pregunta
     */
    public TechnologyBean getRandomTechnology() {
        List<TechnologyBean> allTechnologies = fetchAllTechnologies();
        
        if (allTechnologies.isEmpty()) {
            System.err.println("❌ ERROR: No hay tecnologías disponibles");
            return null;
        }
        
        // Elegimos una tecnología al azar
        int randomIndex = (int) (Math.random() * allTechnologies.size());
        TechnologyBean selectedTech = allTechnologies.get(randomIndex);
        
        System.out.println("→ Tecnología seleccionada: " + selectedTech.getName());
        return selectedTech;
    }

    /**
     * Genera opciones de respuesta para el juego (incluyendo la correcta)
     * Similar a como funciona en el juego de capitales
     */
    public List<String> generateDescriptionOptions(String correctDescription) {
        List<TechnologyBean> allTechnologies = fetchAllTechnologies();
        List<String> options = new ArrayList<>();
        
        // Añadimos la descripción correcta
        options.add(correctDescription);
        
        // Añadimos 3 descripciones incorrectas
        for (TechnologyBean tech : allTechnologies) {
            if (options.size() >= 4) break;
            
            String techDescription = tech.getDescription();
            if (!techDescription.equals(correctDescription)) {
                options.add(techDescription);
            }
        }
        
        // Si no tenemos suficientes opciones, añadimos algunas genéricas
        while (options.size() < 4) {
            options.add("Tecnología de desarrollo web moderna");
        }
        
        // Mezclamos para que la respuesta correcta no esté siempre en la misma posición
        Collections.shuffle(options);
        
        return options;
    }

    /**
     * MÉTODO AVANZADO: Intenta obtener información real de DuckDuckGo
     * 
     * ¿CÓMO FUNCIONA LA API DE DUCKDUCKGO?
     * - Hacemos una petición HTTP GET a su API
     * - Le pasamos el nombre de la tecnología como parámetro
     * - Nos devuelve un JSON con información
     * - Extraemos la descripción y creamos nuestro TechnologyBean
     */
    private List<TechnologyBean> enrichTechnologiesWithDuckDuckGo() {
        List<TechnologyBean> enrichedTechnologies = new ArrayList<>();
        
        // Lista de tecnologías que queremos buscar en DuckDuckGo
        String[] techNames = {"JavaScript", "Python", "React", "Node.js", "Docker"};
        
        for (String techName : techNames) {
            try {
                TechnologyBean enrichedTech = fetchTechnologyFromDuckDuckGo(techName);
                if (enrichedTech != null) {
                    enrichedTechnologies.add(enrichedTech);
                    System.out.println("✓ Obtenida información de " + techName + " desde DuckDuckGo");
                }
            } catch (Exception e) {
                System.out.println("⚠ No se pudo obtener " + techName + " de DuckDuckGo: " + e.getMessage());
            }
        }
        
        return enrichedTechnologies;
    }

    /**
     * Hace la llamada real a la API de DuckDuckGo
     * Este es el código más técnico, pero te explico paso a paso:
     */
    private TechnologyBean fetchTechnologyFromDuckDuckGo(String technologyName) throws Exception {
        // 1. Construimos la URL de la API
        String apiUrl = "https://api.duckduckgo.com/?q=" + technologyName + 
                       "&format=json&no_html=1&skip_disambig=1";
        
        // 2. Abrimos una conexión HTTP
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000); // 5 segundos máximo
        connection.setReadTimeout(5000);
        
        // 3. Verificamos el código de respuesta HTTP
        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new Exception("API returned HTTP " + responseCode);
        }
        
        // 4. Leemos la respuesta usando try-with-resources para cerrar automáticamente
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            
            // 4. Parseamos el JSON
            JSONObject jsonResponse = new JSONObject(response.toString());
            
            // 5. Extraemos la información que necesitamos
            String abstractText = jsonResponse.optString("Abstract", "");
            
            if (!abstractText.isEmpty()) {
                // 6. Creamos nuestro TechnologyBean con la información obtenida
                return new TechnologyBean(
                    technologyName,
                    "lenguaje", // tipo por defecto
                    abstractText.length() > 200 ? abstractText.substring(0, 200) + "..." : abstractText,
                    "general", // categoría por defecto
                    "medio" // dificultad por defecto
                );
            }
            
            return null;
        }
    }

    /**
     * Inicializa las tecnologías de respaldo
     * Estas son las que usamos si DuckDuckGo no funciona
     * Son las mismas que tenías antes, pero organizadas de forma más limpia
     */
    private static void initializeFallbackTechnologies() {
        FALLBACK_TECHNOLOGIES.add(new TechnologyBean("Java", "lenguaje", 
            "Lenguaje de programación orientado a objetos, robusto y multiplataforma", 
            "backend", "medio"));
            
        FALLBACK_TECHNOLOGIES.add(new TechnologyBean("JavaScript", "lenguaje", 
            "Lenguaje de programación dinámico para web, tanto frontend como backend", 
            "fullstack", "facil"));
            
        FALLBACK_TECHNOLOGIES.add(new TechnologyBean("Python", "lenguaje", 
            "Lenguaje de programación interpretado, fácil de aprender y muy versátil", 
            "general", "facil"));
            
        FALLBACK_TECHNOLOGIES.add(new TechnologyBean("React", "framework", 
            "Biblioteca de JavaScript para construir interfaces de usuario interactivas", 
            "frontend", "medio"));
            
        FALLBACK_TECHNOLOGIES.add(new TechnologyBean("Spring Boot", "framework", 
            "Framework de Java para crear aplicaciones web robustas de forma rápida", 
            "backend", "dificil"));
            
        FALLBACK_TECHNOLOGIES.add(new TechnologyBean("Vue.js", "framework", 
            "Framework progresivo de JavaScript para construir interfaces modernas", 
            "frontend", "medio"));
            
        FALLBACK_TECHNOLOGIES.add(new TechnologyBean("Angular", "framework", 
            "Framework completo de TypeScript para aplicaciones web empresariales", 
            "frontend", "dificil"));
            
        FALLBACK_TECHNOLOGIES.add(new TechnologyBean("Node.js", "runtime", 
            "Entorno de ejecución de JavaScript en el servidor", 
            "backend", "medio"));
            
        FALLBACK_TECHNOLOGIES.add(new TechnologyBean("MongoDB", "herramienta", 
            "Base de datos NoSQL orientada a documentos, flexible y escalable", 
            "backend", "medio"));
            
        FALLBACK_TECHNOLOGIES.add(new TechnologyBean("Docker", "herramienta", 
            "Plataforma de contenedores para empaquetar y distribuir aplicaciones", 
            "devops", "medio"));
    }
}
