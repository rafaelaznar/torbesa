package net.ausiasmarch.starwars.service;

import java.util.List;

import javax.servlet.ServletContext;

import java.util.ArrayList;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import net.ausiasmarch.starwars.model.CharacterBean;

public class CharacterService {

    private static final String API_URL = "https://swapi.dev/api/people/";
    private ServletContext oContext = null;

    public CharacterService(ServletContext oContext) {
        this.oContext = oContext;
    }

    public List<CharacterBean> fetchAllCharacters() {
        if (this.oContext != null) {
            // obtener el atributo "characters" del contexto
            @SuppressWarnings("unchecked")
            List<CharacterBean> characters = (List<CharacterBean>) this.oContext.getAttribute("characters");
            // si oContext.getAttribute("characters") es distinto de null
            if (characters != null) {
                return characters;
            }
        }
    
        // obtener characters y guardarlo en el contexto
        System.out.println("Downloading characters....");
        List<CharacterBean> characters = new ArrayList<>();
    
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(content.toString());
            JSONArray arr = jsonResponse.getJSONArray("results"); // Solo necesitamos un array
            
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                
                String name = obj.optString("name", "Desconocido");
                String link = obj.optString("url", "");
                int id = extractIdFromUrl(link);
                
                // 1. EXTRAER DATOS CLAVE PARA LA PISTA
                String gender = obj.optString("gender", "desconocido");
                String height = obj.optString("height", "desconocido");
                String hairColor = obj.optString("hair_color", "desconocido");
                String eyeColor = obj.optString("eye_color", "desconocido");
                String speciesUrl = obj.optString("species", "");
                String skinColor = obj.optString("skin_color", "desconocido");

                // Obtener el nombre de la especie
                String speciesName = getSpeciesName(speciesUrl);
                
                // 2. CONSTRUIR LA PISTA (Llamamos a una función que construirá el texto)
                String clue = buildCharacterClue(gender, height, hairColor, eyeColor, speciesName, skinColor);
                
                // 3. PASAR LA PISTA AL CONSTRUCTOR DE CharacterBean
                characters.add(new CharacterBean(name, link, id, clue));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        this.oContext.setAttribute("characters", characters);
        return characters;
    }

    private String getSpeciesName(String speciesUrl) {
        if (speciesUrl == null || speciesUrl.isEmpty()) {
            return "desconocida";
        }
        try {
            URL url = new URL(speciesUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            
            JSONObject jsonResponse = new JSONObject(content.toString());
            return jsonResponse.optString("name", "desconocida");
            
        } catch (Exception e) {
            System.err.println("Error fetching species name: " + e.getMessage());
            return "desconocida";
        }
    }

    private String buildCharacterClue(String gender, String height, String hairColor, 
                                      String eyeColor, String speciesName, String skinColor) {
        StringBuilder clueBuilder = new StringBuilder("Soy un personaje de Star Wars. ");

        // Función de utilidad para filtrar datos nulos o no relevantes
        // SWAPI usa "n/a", "none", "unknown"
        java.util.function.Predicate<String> isUseful = s -> 
            s != null && !s.equalsIgnoreCase("n/a") && 
            !s.equalsIgnoreCase("none") && !s.equalsIgnoreCase("unknown");

        // 1. Pista de Género/Especie (Clave para droides vs humanos)
        if (isUseful.test(speciesName)) {
            clueBuilder.append("Soy de la especie: ").append(speciesName).append(". ");
        }
        
        // 2. Pista de Altura
        if (isUseful.test(height)) {
            clueBuilder.append("Mi altura es de ").append(height).append(" cm. ");
        }

        // 3. Pista de Atributos Físicos
        if (isUseful.test(hairColor) && !hairColor.equals("no hair")) {
            clueBuilder.append("Tengo pelo de color ").append(hairColor).append(". ");
        } else if (isUseful.test(skinColor)) {
            // Pista de piel para personajes no humanos o droides
            clueBuilder.append("Mi piel es de color ").append(skinColor).append(". ");
        }
        
        // 4. Pista de Ojos
        if (isUseful.test(eyeColor)) {
            clueBuilder.append("Mis ojos son de color ").append(eyeColor).append(". ");
        }
        
        // Si la pista es muy corta, añade un valor por defecto.
        if (clueBuilder.length() < 30) {
            clueBuilder.append("¡Soy uno de los primeros personajes introducidos en la saga! ");
        }

        return clueBuilder.toString().trim();
    }

    private int extractIdFromUrl(String link) {
        try {
            String[] parts = link.split("/");
            return Integer.parseInt(
                parts[parts.length - 1].isEmpty() ? parts[parts.length - 2] : parts[parts.length - 1]);
        } catch (Exception e) {
            System.err.println("Error extracting ID from URL: " + link);
            return 0; // valor por defecto en caso de error
        }
    }

    public CharacterBean getCharacterByName(String name) {
        for (CharacterBean character : fetchAllCharacters()) {
            if (character.getName().equalsIgnoreCase(name)) {
                return character;
            }
        }
        return null;
    }

    public CharacterBean getOneRandomCharacter() {
        List<CharacterBean> oCharacters = fetchAllCharacters();
        int randomIndex0 = (int) (Math.random() * oCharacters.size());
        CharacterBean selectedCharacters = oCharacters.get(randomIndex0);
        while (selectedCharacters.getName().trim().isEmpty()) {
            randomIndex0 = (int) (Math.random() * oCharacters.size());
            selectedCharacters = oCharacters.get(randomIndex0);
        }
        return selectedCharacters;
    }

    public ArrayList<String> getRandomCharacterNamesForTest(CharacterBean oSelectedCharacter, int numCharacters) {
        if (numCharacters < 1) {
            numCharacters = 4;
        }

        List<CharacterBean> allCharacters = fetchAllCharacters();
        ArrayList<String> selectedNames = new ArrayList<>();

        // Añadimos el nombre correcto
        selectedNames.add(oSelectedCharacter.getName());

        for (int i = 0; i < numCharacters - 1; i++) {
            int randomIndex = -1;

            while (randomIndex == -1) {
                randomIndex = (int) (Math.random() * allCharacters.size());
                CharacterBean candidate = allCharacters.get(randomIndex);

                // Evitamos nombres vacíos o duplicados
                if (candidate.getName().trim().isEmpty() || selectedNames.contains(candidate.getName())) {
                    randomIndex = -1; // Vuelve a buscar otro
                }
            }

            selectedNames.add(allCharacters.get(randomIndex).getName());
        }

        Collections.shuffle(selectedNames);
        return selectedNames;
    }
}