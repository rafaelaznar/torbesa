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
            JSONArray arr = new JSONArray(content.toString());
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                String name = obj.optString("name", "Desconocido");
                String link = obj.optString("url", "");
                int id = extractIdFromUrl(link); // método auxiliar que toma el número final del URL
                String imageUrl = "https://starwars-visualguide.com/assets/img/characters/" + id + ".jpg";

                characters.add(new CharacterBean(name, link, id, imageUrl));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.oContext.setAttribute("countries", characters);
        return characters;

    }

    private int extractIdFromUrl(String link) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'extractIdFromUrl'");
    }

    public CharacterBean getCharacterByName(String name) {
        for (CharacterBean character : fetchAllCharacters()) {
            if (character.getName().equalsIgnoreCase(name)) {
                return character;
            }
        }
        return null;
    }

    public String getCharacterNameByImageUrl(String imageUrl) {
        for (CharacterBean character : fetchAllCharacters()) {
            if (character.getImageUrl().equalsIgnoreCase(imageUrl)) {
                return character.getName();
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