package net.ausiasmarch.codequest.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;

import org.json.JSONArray;
import org.json.JSONObject;

import net.ausiasmarch.codequest.model.TechnologyBean;

/**
 * Servicio para obtener tecnologías populares usando la API de GitHub.
 * Si falla la API, usa una lista local de respaldo.
 */
public class GitHubTechnologyService {

    private final ServletContext context;
    private static final List<TechnologyBean> FALLBACK_TECHNOLOGIES = new ArrayList<>();
    
    static {
        initializeFallbackTechnologies();
    }

    public GitHubTechnologyService(ServletContext context) {
        this.context = context;
    }

    /**
     * Devuelve una lista de tecnologías. Usa la API de GitHub y si falla, recurre a la lista local.
     */
    public List<TechnologyBean> fetchAllTechnologies() {
        @SuppressWarnings("unchecked")
        List<TechnologyBean> cachedTechnologies = (List<TechnologyBean>) context.getAttribute("github_technologies");
        // Si hay tecnologías en caché, las devuelve
        if (cachedTechnologies != null && !cachedTechnologies.isEmpty()) {
            return cachedTechnologies;
        }
        List<TechnologyBean> technologies = new ArrayList<>();
        try {
            technologies.addAll(fetchTechnologiesFromGitHub());
        } catch (Exception e) {
            System.out.println("No se pudo obtener tecnologías de GitHub: " + e.getMessage());
        }
        // Si hay pocas tecnologías, añade las de respaldo
        if (technologies.size() < 5) {
            technologies.addAll(FALLBACK_TECHNOLOGIES);
        }
        Collections.shuffle(technologies);
        context.setAttribute("github_technologies", technologies);
        return technologies;
    }

    public TechnologyBean getRandomTechnology() {
        List<TechnologyBean> allTechnologies = fetchAllTechnologies();
        
        if (allTechnologies.isEmpty()) {
            return null;
        }
        
        int randomIndex = (int) (Math.random() * allTechnologies.size());
        return allTechnologies.get(randomIndex);
    }

    public List<String> generateDescriptionOptions(String correctDescription) {
        List<TechnologyBean> allTechnologies = fetchAllTechnologies();
        List<String> options = new ArrayList<>();
        
        options.add(correctDescription);
        
        for (TechnologyBean tech : allTechnologies) {
            if (options.size() >= 4) break;
            
            String description = tech.getDescription();
            if (!description.equals(correctDescription)) {
                options.add(description);
            }
        }
        
        while (options.size() < 4) {
            options.add("Tecnologia de desarrollo de software");
        }
        
        Collections.shuffle(options);
        return options;
    }

    private List<TechnologyBean> fetchTechnologiesFromGitHub() {
        List<TechnologyBean> gitHubTechnologies = new ArrayList<>();
        
        gitHubTechnologies.addAll(fetchLanguageTechnologies("javascript", "lenguaje", "frontend"));
        gitHubTechnologies.addAll(fetchLanguageTechnologies("python", "lenguaje", "backend"));
        gitHubTechnologies.addAll(fetchLanguageTechnologies("java", "lenguaje", "backend"));
        gitHubTechnologies.addAll(fetchTopicTechnologies("react", "framework", "frontend"));
        gitHubTechnologies.addAll(fetchTopicTechnologies("vue", "framework", "frontend"));
        
        return gitHubTechnologies;
    }

    private List<TechnologyBean> fetchLanguageTechnologies(String language, String type, String category) {
        List<TechnologyBean> technologies = new ArrayList<>();
        
        try {
            String apiUrl = "https://api.github.com/search/repositories?q=language:" + language + 
                           "&sort=stars&per_page=3";
            
            JSONObject response = makeGitHubRequest(apiUrl);
            if (response != null && response.has("items")) {
                JSONArray items = response.getJSONArray("items");
                
                if (items.length() > 0) {
                    JSONObject topRepo = items.getJSONObject(0);
                    String description = getPredefinedDescription(language, topRepo.getInt("stargazers_count"));
                    
                    TechnologyBean tech = new TechnologyBean(
                        capitalize(language), 
                        type, 
                        description, 
                        category, 
                        "medio"
                    );
                    technologies.add(tech);
                }
            }
        } catch (Exception e) {
            System.out.println("No se pudo obtener " + language + " de GitHub: " + e.getMessage());
        }
        
        return technologies;
    }

    private List<TechnologyBean> fetchTopicTechnologies(String topic, String type, String category) {
        List<TechnologyBean> technologies = new ArrayList<>();
        
        try {
            String apiUrl = "https://api.github.com/search/repositories?q=topic:" + topic + 
                           "&sort=stars&per_page=2";
            
            JSONObject response = makeGitHubRequest(apiUrl);
            if (response != null && response.has("items")) {
                JSONArray items = response.getJSONArray("items");
                
                if (items.length() > 0) {
                    JSONObject topRepo = items.getJSONObject(0);
                    String description = getPredefinedDescription(topic, topRepo.getInt("stargazers_count"));
                    
                    TechnologyBean tech = new TechnologyBean(
                        capitalize(topic), 
                        type, 
                        description, 
                        category, 
                        "medio"
                    );
                    technologies.add(tech);
                }
            }
        } catch (Exception e) {
            System.out.println("No se pudo obtener " + topic + " de GitHub: " + e.getMessage());
        }
        
        return technologies;
    }

    private JSONObject makeGitHubRequest(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setRequestProperty("User-Agent", "CodeQuest-Java-Game");

        // Leer token de variable de entorno
        String githubToken = System.getenv("GITHUB_TOKEN");
        if (githubToken != null && !githubToken.isEmpty()) {
            connection.setRequestProperty("Authorization", "Bearer " + githubToken);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new Exception("GitHub API returned HTTP " + responseCode);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            return new JSONObject(response.toString());
        }
    }

    private String capitalize(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }

    /**
     * Obtiene descripción predefinida en español basada en la tecnología
     */
    private String getPredefinedDescription(String technology, int stars) {
        String tech = technology.toLowerCase();
        
        switch (tech) {
            case "javascript":
                return "Lenguaje de programación dinámico usado en desarrollo web frontend y backend";
            case "python":
                return "Lenguaje interpretado versátil, ideal para ciencia de datos y desarrollo web";
            case "java":
                return "Lenguaje orientado a objetos robusto y multiplataforma para aplicaciones empresariales";
            case "react":
                return "Biblioteca de JavaScript para crear interfaces de usuario interactivas y modernas";
            case "vue":
                return "Framework progresivo de JavaScript para desarrollo frontend moderno y reactivo";
            case "angular":
                return "Framework completo de TypeScript para aplicaciones web de gran escala";
            case "node":
            case "nodejs":
                return "Entorno de ejecución de JavaScript del lado del servidor";
            case "spring":
                return "Framework de Java para desarrollo de aplicaciones empresariales robustas";
            case "django":
                return "Framework web de Python para desarrollo rápido y limpio";
            case "flutter":
                return "Framework de Google para crear aplicaciones nativas multiplataforma";
            default:
                return "Tecnología popular con " + formatNumber(stars) + " estrellas en GitHub";
        }
    }

    /**
     * Formatea números para mostrar de forma legible
     */
    private String formatNumber(int number) {
        if (number >= 1000000) {
            return String.format("%.1fM", number / 1000000.0);
        } else if (number >= 1000) {
            return String.format("%.1fK", number / 1000.0);
        } else {
            return String.valueOf(number);
        }
    }

    private static void initializeFallbackTechnologies() {
        FALLBACK_TECHNOLOGIES.add(new TechnologyBean("Java", "lenguaje", 
            "Lenguaje de programacion orientado a objetos y multiplataforma", 
            "backend", "medio"));
            
        FALLBACK_TECHNOLOGIES.add(new TechnologyBean("JavaScript", "lenguaje", 
            "Lenguaje dinamico para desarrollo web frontend y backend", 
            "fullstack", "facil"));
            
        FALLBACK_TECHNOLOGIES.add(new TechnologyBean("Python", "lenguaje", 
            "Lenguaje interpretado facil de aprender y muy versatil", 
            "general", "facil"));
            
        FALLBACK_TECHNOLOGIES.add(new TechnologyBean("React", "framework", 
            "Biblioteca para construir interfaces de usuario interactivas", 
            "frontend", "medio"));
            
        FALLBACK_TECHNOLOGIES.add(new TechnologyBean("Spring Boot", "framework", 
            "Framework de Java para crear aplicaciones web robustas", 
            "backend", "dificil"));
            
        FALLBACK_TECHNOLOGIES.add(new TechnologyBean("Vue.js", "framework", 
            "Framework progresivo para construir interfaces modernas", 
            "frontend", "medio"));
            
        FALLBACK_TECHNOLOGIES.add(new TechnologyBean("Angular", "framework", 
            "Framework completo para aplicaciones web empresariales", 
            "frontend", "dificil"));
            
        FALLBACK_TECHNOLOGIES.add(new TechnologyBean("Node.js", "runtime", 
            "Entorno de ejecucion de JavaScript en el servidor", 
            "backend", "medio"));
            
        FALLBACK_TECHNOLOGIES.add(new TechnologyBean("MongoDB", "herramienta", 
            "Base de datos NoSQL orientada a documentos", 
            "backend", "medio"));
            
        FALLBACK_TECHNOLOGIES.add(new TechnologyBean("Docker", "herramienta", 
            "Plataforma de contenedores para empaquetar aplicaciones", 
            "devops", "medio"));
    }
}
