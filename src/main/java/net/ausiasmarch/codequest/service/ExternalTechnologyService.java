package net.ausiasmarch.codequest.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;

import net.ausiasmarch.codequest.model.TechnologyBean;

public class ExternalTechnologyService {

    private ServletContext oContext = null;

    public ExternalTechnologyService(ServletContext oContext) {
        this.oContext = oContext;
    }

    public List<TechnologyBean> fetchAllTechnologies() {
        
        if (this.oContext != null) {
            @SuppressWarnings("unchecked")
            List<TechnologyBean> technologies = (List<TechnologyBean>) this.oContext.getAttribute("external_technologies");
            if (technologies != null) {
                return technologies;
            }
        }
        
        System.out.println("Downloading technologies from external APIs....");
        List<TechnologyBean> technologies = new ArrayList<>();
        
        // Añadir tecnologías base (fallback)
        technologies.addAll(getBaseTechnologies());
        
        // Intentar obtener datos dinámicos de APIs externas
        try {
            technologies.addAll(fetchFromStackOverflow());
        } catch (Exception e) {
            System.out.println("Warning: Could not fetch from StackOverflow API: " + e.getMessage());
        }
        
        // Mezclar la lista para orden aleatorio
        Collections.shuffle(technologies);
        
        if (this.oContext != null) {
            this.oContext.setAttribute("external_technologies", technologies);
        }
        
        return technologies;
    }

    private List<TechnologyBean> getBaseTechnologies() {
        List<TechnologyBean> technologies = new ArrayList<>();
        
        // Tecnologías base que siempre estarán disponibles
        technologies.add(new TechnologyBean("JavaScript", "lenguaje", 
            "Lenguaje de programación interpretado para crear interactividad en páginas web", 
            "frontend", "medio"));
        
        technologies.add(new TechnologyBean("Python", "lenguaje", 
            "Lenguaje interpretado versátil y fácil de aprender", 
            "backend", "medio"));
            
        technologies.add(new TechnologyBean("Java", "lenguaje", 
            "Lenguaje orientado a objetos para aplicaciones empresariales", 
            "backend", "dificil"));
            
        technologies.add(new TechnologyBean("React", "framework", 
            "Librería de JavaScript para construir interfaces de usuario", 
            "frontend", "medio"));
            
        technologies.add(new TechnologyBean("Node.js", "runtime", 
            "JavaScript en el servidor, permite full-stack con un solo lenguaje", 
            "fullstack", "medio"));
            
        technologies.add(new TechnologyBean("Spring Boot", "framework", 
            "Framework de Java para crear aplicaciones web robustas", 
            "backend", "dificil"));
            
        technologies.add(new TechnologyBean("Vue.js", "framework", 
            "Framework progresivo de JavaScript para construir interfaces", 
            "frontend", "medio"));
            
        technologies.add(new TechnologyBean("Angular", "framework", 
            "Framework completo de TypeScript para aplicaciones web", 
            "frontend", "dificil"));
            
        technologies.add(new TechnologyBean("Express.js", "framework", 
            "Framework minimalista para Node.js", 
            "backend", "facil"));
            
        technologies.add(new TechnologyBean("MongoDB", "herramienta", 
            "Base de datos NoSQL orientada a documentos", 
            "backend", "medio"));

        technologies.add(new TechnologyBean("Docker", "herramienta", 
            "Plataforma de contenedores para empaquetar aplicaciones", 
            "devops", "medio"));

        technologies.add(new TechnologyBean("Git", "herramienta", 
            "Sistema de control de versiones distribuido", 
            "devops", "facil"));
        
        return technologies;
    }
    
    private List<TechnologyBean> fetchFromStackOverflow() throws Exception {
        List<TechnologyBean> technologies = new ArrayList<>();
        
        // Nota: StackOverflow API tiene limitaciones, usamos datos simulados por simplicidad
        // En un entorno real, necesitarías registrar tu aplicación para obtener una API key
        
        // Simulamos tecnologías populares que podrían venir de la API
        technologies.add(new TechnologyBean("TypeScript", "lenguaje", 
            "Superset de JavaScript que añade tipado estático", 
            "frontend", "medio"));
            
        technologies.add(new TechnologyBean("Kubernetes", "herramienta", 
            "Plataforma de orquestación de contenedores", 
            "devops", "dificil"));
            
        technologies.add(new TechnologyBean("Redis", "herramienta", 
            "Base de datos en memoria para cache y sesiones", 
            "backend", "medio"));
        
        return technologies;
    }

    public TechnologyBean getRandomTechnology() {
        List<TechnologyBean> technologies = fetchAllTechnologies();
        if (technologies.isEmpty()) {
            return null;
        }
        Collections.shuffle(technologies);
        return technologies.get(0);
    }

    public List<String> generateDescriptionOptions(String correctDescription) {
        List<String> options = new ArrayList<>();
        options.add(correctDescription);
        
        // Añadir descripciones falsas pero creíbles
        List<String> fakeDescriptions = List.of(
            "Herramienta de diseño gráfico para crear interfaces visuales",
            "Base de datos relacional de alto rendimiento y escalabilidad",
            "Sistema operativo especializado en servidores de red",
            "Protocolo de comunicación para sistemas distribuidos",
            "Algoritmo de machine learning para clasificación automática",
            "Arquitectura de microservicios en la nube pública",
            "Metodología ágil para gestión eficiente de proyectos",
            "Compilador optimizado para código de bajo nivel y rendimiento",
            "Librería de criptografía para seguridad avanzada",
            "Framework de testing para pruebas automatizadas",
            "Motor de renderizado para aplicaciones 3D",
            "Sistema de gestión de dependencias multiplataforma"
        );
        
        Collections.shuffle(fakeDescriptions);
        for (int i = 0; i < 3 && i < fakeDescriptions.size(); i++) {
            if (!fakeDescriptions.get(i).equals(correctDescription)) {
                options.add(fakeDescriptions.get(i));
            }
        }
        
        // Asegurar que tenemos exactamente 4 opciones
        while (options.size() < 4) {
            for (String fake : fakeDescriptions) {
                if (!options.contains(fake)) {
                    options.add(fake);
                    break;
                }
            }
        }
        
        Collections.shuffle(options);
        return options;
    }
}