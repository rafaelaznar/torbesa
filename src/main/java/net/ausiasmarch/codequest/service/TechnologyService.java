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

public class TechnologyService {

    // URL pública del JSON remoto (ejemplo: GitHub Gist en modo raw)
    private static final String API_URL = "https://gist.githubusercontent.com/PollySOA/c343be194ed84f4e8fd7fbd2103f68b3/raw/f2923282d6ff2b4a2622491863830dc8d8118514/gistfile1.txt";
    private ServletContext oContext = null;

    public TechnologyService(ServletContext oContext) {
        this.oContext = oContext;
    }

    /**
     * Descarga y guarda todas las tecnologías.
     */
    @SuppressWarnings("unchecked")
    public List<TechnologyBean> fetchAllTechnologies() {
        if (this.oContext != null) {
            List<TechnologyBean> cached = (List<TechnologyBean>) this.oContext.getAttribute("technologies");
            if (cached != null) {
                return cached;
            }
        }

        System.out.println("Descargando tecnologías...");
        List<TechnologyBean> technologies = new ArrayList<>();
        boolean loaded = false;

        // 1. Intenta descargar desde la URL remota
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            JSONArray arr = new JSONArray(content.toString());
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                String name = obj.optString("name", "");
                String type = obj.optString("type", "");
                String description = obj.optString("description", "");
                String category = obj.optString("category", "");
                String difficulty = obj.optString("difficulty", "");
                technologies.add(new TechnologyBean(name, type, description, category, difficulty));
            }
            loaded = true;
        } catch (Exception e) {
            System.err.println("No se pudo descargar el JSON remoto, intentando cargar el local...");
        }

        // 2. Si falla la descarga, intenta cargar el JSON local
        if (!loaded) {
            try {
                String localPath = "src/main/java/net/ausiasmarch/codequest/service/tecnologias.json";
                BufferedReader in = new BufferedReader(new InputStreamReader(new java.io.FileInputStream(localPath), "UTF-8"));
                StringBuilder content = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                JSONArray arr = new JSONArray(content.toString());
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    String name = obj.optString("name", "");
                    String type = obj.optString("type", "");
                    String description = obj.optString("description", "");
                    String category = obj.optString("category", "");
                    String difficulty = obj.optString("difficulty", "");
                    technologies.add(new TechnologyBean(name, type, description, category, difficulty));
                }
                loaded = true;
            } catch (Exception e2) {
                System.err.println("No se pudo cargar el JSON local de tecnologías: " + e2.getMessage());
            }
        }

        if (this.oContext != null) {
            this.oContext.setAttribute("technologies", technologies);
        }

        return technologies;
    }

    /**
     * Devuelve una tecnología aleatoria (con descripción válida).
     */
    public TechnologyBean getOneRandomTechnology() {
        List<TechnologyBean> techList = fetchAllTechnologies();
        int randomIndex = (int) (Math.random() * techList.size());
        TechnologyBean selected = techList.get(randomIndex);
        while (selected.getDescription().trim().isEmpty()) {
            randomIndex = (int) (Math.random() * techList.size());
            selected = techList.get(randomIndex);
        }
        return selected;
    }

    /**
     * Devuelve una tecnología aleatoria excluyendo las ya mostradas.
     * Mantiene el enfoque de Capitals.
     */
    public TechnologyBean getRandomTechnologyExcluding(List<String> excludedNames) {
        List<TechnologyBean> techList = fetchAllTechnologies();
        if (excludedNames == null) excludedNames = new ArrayList<>();

        List<TechnologyBean> available = new ArrayList<>();
        for (TechnologyBean tech : techList) {
            if (!excludedNames.contains(tech.getName()) && !tech.getDescription().trim().isEmpty()) {
                available.add(tech);
            }
        }

        if (available.isEmpty()) return null;

        int randomIndex = (int) (Math.random() * available.size());
        return available.get(randomIndex);
    }

    /**
     * Devuelve una lista de descripciones (1 correcta + n-1 incorrectas) aleatorias.
     */
    public ArrayList<String> getRandomDescriptionsForTest(TechnologyBean selectedTech, int numOptions) {
        if (numOptions < 1) numOptions = 4;

        List<TechnologyBean> techList = fetchAllTechnologies();
        ArrayList<String> selectedDescriptions = new ArrayList<>();

        selectedDescriptions.add(selectedTech.getDescription());
        while (selectedDescriptions.size() < numOptions) {
            TechnologyBean randomTech = techList.get((int) (Math.random() * techList.size()));
            String desc = randomTech.getDescription();
            if (!desc.trim().isEmpty() && !selectedDescriptions.contains(desc)) {
                selectedDescriptions.add(desc);
            }
        }

        Collections.shuffle(selectedDescriptions);
        return selectedDescriptions;
    }

    /**
     * Busca una tecnología por nombre.
     */
    public TechnologyBean getTechnologyByName(String name) {
        for (TechnologyBean tech : fetchAllTechnologies()) {
            if (tech.getName().equalsIgnoreCase(name)) {
                return tech;
            }
        }
        return null;
    }

    /**
     * Obtiene el tipo (como analogía a capital en capitals)
     */
    public String getTypeByTechnologyName(String name) {
        TechnologyBean tech = getTechnologyByName(name);
        return (tech != null) ? tech.getType() : null;
    }

    /**
     * Obtiene el nombre por tipo (inverso).
     */
    public String getTechnologyByType(String type) {
        for (TechnologyBean tech : fetchAllTechnologies()) {
            if (tech.getType().equalsIgnoreCase(type)) {
                return tech.getName();
            }
        }
        return null;
    }
}
