package net.ausiasmarch.codequest.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;

import net.ausiasmarch.codequest.dao.TechnologyDao;
import net.ausiasmarch.codequest.model.TechnologyBean;
import net.ausiasmarch.shared.connection.HikariPool;

public class TechnologyService {

    private ServletContext oContext = null;

    public TechnologyService(ServletContext oContext) {
        this.oContext = oContext;
    }

    public List<TechnologyBean> fetchAllTechnologies() {
        if (this.oContext != null) {
            // obtener el atributo "technologies" del contexto
            @SuppressWarnings("unchecked")
            List<TechnologyBean> technologies = (List<TechnologyBean>) this.oContext.getAttribute("technologies");
            // si oContext.getAttribute("technologies") es distinto de null
            if (technologies != null) {
                // Filtrar protocolos y bases de datos
                return technologies.stream()
                    .filter(tech -> !tech.getType().equalsIgnoreCase("protocolo") && 
                                   !tech.getType().equalsIgnoreCase("base de datos"))
                    .collect(java.util.stream.Collectors.toList());
            }
        }
        
        // obtener technologies y guardarlo en el contexto
        System.out.println("Loading technologies from database....");
        List<TechnologyBean> technologies = new ArrayList<>();
        
        try (Connection oConnection = HikariPool.getConnection()) {
            TechnologyDao technologyDao = new TechnologyDao(oConnection);
            List<TechnologyBean> allTechnologies = technologyDao.getAll();
            
            // Filtrar protocolos y bases de datos
            technologies = allTechnologies.stream()
                .filter(tech -> !tech.getType().equalsIgnoreCase("protocolo") && 
                               !tech.getType().equalsIgnoreCase("base de datos"))
                .collect(java.util.stream.Collectors.toList());
                
        } catch (SQLException e) {
            System.err.println("Error loading technologies: " + e.getMessage());
            e.printStackTrace();
        }
        
        if (this.oContext != null) {
            this.oContext.setAttribute("technologies", technologies);
        }
        return technologies;
    }

    public TechnologyBean getTechnologyByName(String name) {
        for (TechnologyBean technology : fetchAllTechnologies()) {
            if (technology.getName().equalsIgnoreCase(name)) {
                return technology;
            }
        }
        return null;
    }

    public String getDescriptionByTechnologyName(String name) {
        TechnologyBean technology = getTechnologyByName(name);
        return (technology != null) ? technology.getDescription() : null;
    }

    public String getTechnologyByDescription(String description) {
        for (TechnologyBean technology : fetchAllTechnologies()) {
            if (technology.getDescription().equalsIgnoreCase(description)) {
                return technology.getName();
            }
        }
        return null;
    }

    public TechnologyBean getOneRandomTechnology() {
        List<TechnologyBean> oTechnologies = fetchAllTechnologies();
        if (oTechnologies.isEmpty()) {
            return null;
        }
        
        int randomIndex = (int) (Math.random() * oTechnologies.size());
        TechnologyBean selectedTechnology = oTechnologies.get(randomIndex);
        
        // Asegurar que la tecnología tenga descripción
        while (selectedTechnology.getDescription() == null || selectedTechnology.getDescription().trim().isEmpty()) {
            randomIndex = (int) (Math.random() * oTechnologies.size());
            selectedTechnology = oTechnologies.get(randomIndex);
        }
        
        return selectedTechnology;
    }

    public ArrayList<String> getRandomDescriptionsForTest(TechnologyBean oSelectedTechnologyBean, int numDescriptions) {
        if (numDescriptions < 1) {
            numDescriptions = 4;
        }
        
        List<TechnologyBean> oTechnologies = fetchAllTechnologies();
        ArrayList<String> selectedDescriptionsList = new ArrayList<>();

        // Añadir la descripción correcta
        selectedDescriptionsList.add(oSelectedTechnologyBean.getDescription());
        
        // Añadir descripciones incorrectas
        for (int i = 0; i < numDescriptions - 1; i++) {
            int randomIndex = 0;
            while (randomIndex == 0) {
                randomIndex = (int) (Math.random() * oTechnologies.size());
                TechnologyBean randomTech = oTechnologies.get(randomIndex);
                
                if (randomTech.getDescription() == null || randomTech.getDescription().trim().isEmpty()) {
                    randomIndex = 0;
                } else {
                    if (selectedDescriptionsList.contains(randomTech.getDescription())) {
                        randomIndex = 0;
                    }
                }
            }
            selectedDescriptionsList.add(oTechnologies.get(randomIndex).getDescription());
        }

        Collections.shuffle(selectedDescriptionsList);
        return selectedDescriptionsList;
    }

    public ArrayList<String> getRandomTechnologiesForTest(TechnologyBean oSelectedTechnologyBean, int numTechnologies) {
        if (numTechnologies < 1) {
            numTechnologies = 4;
        }
        
        List<TechnologyBean> oTechnologies = fetchAllTechnologies();
        ArrayList<String> selectedTechnologiesList = new ArrayList<>();

        // Añadir la tecnología correcta
        selectedTechnologiesList.add(oSelectedTechnologyBean.getName());
        
        // Añadir tecnologías incorrectas
        for (int i = 0; i < numTechnologies - 1; i++) {
            int randomIndex = 0;
            while (randomIndex == 0) {
                randomIndex = (int) (Math.random() * oTechnologies.size());
                TechnologyBean randomTech = oTechnologies.get(randomIndex);
                
                if (selectedTechnologiesList.contains(randomTech.getName())) {
                    randomIndex = 0;
                }
            }
            selectedTechnologiesList.add(oTechnologies.get(randomIndex).getName());
        }

        Collections.shuffle(selectedTechnologiesList);
        return selectedTechnologiesList;
    }

    public List<TechnologyBean> getTechnologiesByType(String type) {
        try (Connection oConnection = HikariPool.getConnection()) {
            TechnologyDao technologyDao = new TechnologyDao(oConnection);
            return technologyDao.getTechnologiesByType(type);
        } catch (SQLException e) {
            System.err.println("Error getting technologies by type: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<TechnologyBean> getTechnologiesByCategory(String category) {
        try (Connection oConnection = HikariPool.getConnection()) {
            TechnologyDao technologyDao = new TechnologyDao(oConnection);
            return technologyDao.getTechnologiesByCategory(category);
        } catch (SQLException e) {
            System.err.println("Error getting technologies by category: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<TechnologyBean> getTechnologiesByDifficulty(String difficulty) {
        try (Connection oConnection = HikariPool.getConnection()) {
            TechnologyDao technologyDao = new TechnologyDao(oConnection);
            return technologyDao.getTechnologiesByDifficulty(difficulty);
        } catch (SQLException e) {
            System.err.println("Error getting technologies by difficulty: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public int getTechnologyCount() {
        try (Connection oConnection = HikariPool.getConnection()) {
            TechnologyDao technologyDao = new TechnologyDao(oConnection);
            return technologyDao.count();
        } catch (SQLException e) {
            System.err.println("Error getting technology count: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }
}

