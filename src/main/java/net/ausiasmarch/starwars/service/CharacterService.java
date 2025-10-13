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
                JSONArray speciesArray = obj.optJSONArray("species");
                String speciesUrl = "";

                if (speciesArray.length() > 0) {
                    speciesUrl = speciesArray.getString(0);
                }

                String speciesName = getSpeciesName(speciesUrl);

                characters.add(new CharacterBean(name, link, id, speciesName));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        this.oContext.setAttribute("characters", characters);
        return characters;
    }

    private int extractIdFromUrl(String link) {
        try {
            String[] parts = link.split("/");
            return Integer.parseInt(
                parts[parts.length - 1].isEmpty() ? parts[parts.length - 2] : parts[parts.length - 1]);
        } catch (Exception e) {
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

    public List<String> fetchAllSpeciesNames() {
        List<String> speciesNames = new ArrayList<>();
        for (CharacterBean character : fetchAllCharacters()) {
            String species = character.getSpecies();
            if (!speciesNames.contains(species)) {
                speciesNames.add(species);
            }
        }
        Collections.sort(speciesNames);
        return speciesNames;
    }

    public String getSpeciesName(String speciesUrl) {
        if (speciesUrl == null || speciesUrl.isEmpty() || speciesUrl.equalsIgnoreCase("n/a")) {
            return "Human";
        }

        try {
            URL url = new URL(speciesUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(content.toString());
            return jsonResponse.optString("name", "Especie Desconocida");

        } catch (Exception e) {
            e.printStackTrace();
            return "Especie Desconocida";
        }
    }
}