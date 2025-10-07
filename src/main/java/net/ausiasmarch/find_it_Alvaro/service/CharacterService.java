package net.ausiasmarch.find_it_Alvaro.service;

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

import net.ausiasmarch.find_it_Alvaro.model.CharacterBean;

public class CharacterService {

    private static final String API_URL = "https://hsr-api.vercel.app/api/v1/characters";
    private ServletContext oContext = null;

    public CharacterService(ServletContext oContext) {
        this.oContext = oContext;
    }

    public List<CharacterBean> fetchAllCharacters() {
        if (this.oContext != null) {
            @SuppressWarnings("unchecked")
            List<CharacterBean> characters = (List<CharacterBean>) this.oContext.getAttribute("characters");
            if (characters != null) {
                return characters;
            }
        }
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
                String id = obj.optString("id", "");
                String name = obj.optString("name", "");
                int rarity = obj.optInt("rarity", 0);
                String element = obj.optString("element", "");
                characters.add(new CharacterBean(id, name, rarity, element));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.oContext.setAttribute("characters", characters);
        return characters;
    }

    // Buscar personaje por nombre
    public CharacterBean getCharacterByName(String name) {
        List<CharacterBean> characters = fetchAllCharacters();
        for (CharacterBean character : characters) {
            if (character.getName().equalsIgnoreCase(name)) {
                return character;
            }
        }
        return null;
    }

    // Buscar personaje por id
    public CharacterBean getCharacterById(String id) {
        List<CharacterBean> characters = fetchAllCharacters();
        for (CharacterBean character : characters) {
            if (character.getId().equalsIgnoreCase(id)) {
                return character;
            }
        }
        return null;
    }

    // Buscar personajes por rareza
    public List<CharacterBean> getCharactersByRarity(int rarity) {
        List<CharacterBean> characters = fetchAllCharacters();
        List<CharacterBean> result = new ArrayList<>();
        for (CharacterBean character : characters) {
            if (character.getRarity() == rarity) {
                result.add(character);
            }
        }
        return result;
    }

    // Buscar personajes por elemento
    public List<CharacterBean> getCharactersByElement(String element) {
        List<CharacterBean> characters = fetchAllCharacters();
        List<CharacterBean> result = new ArrayList<>();
        for (CharacterBean character : characters) {
            if (character.getElement().equalsIgnoreCase(element)) {
                result.add(character);
            }
        }
        return result;
    }

    //Cambiar el metodo para que devuelva un personaje aleatorio
    public CharacterBean getOneRandomCharacter() {
        List<CharacterBean> oCharacters = fetchAllCharacters();
        if (oCharacters == null || oCharacters.isEmpty()) {
            System.out.println("No characters available for random selection.");
            return null;
        }
        int randomIndex0 = (int) (Math.random() * oCharacters.size());
        CharacterBean selectedCharacter = oCharacters.get(randomIndex0);
        int attempts = 0;
        while (selectedCharacter.getElement().trim().isEmpty() && attempts < oCharacters.size()) {
            randomIndex0 = (int) (Math.random() * oCharacters.size());
            selectedCharacter = oCharacters.get(randomIndex0);
            attempts++;
        }
        if (selectedCharacter.getElement().trim().isEmpty()) {
            System.out.println("No valid character found with non-empty element.");
            return null;
        }
        return selectedCharacter;
    }

        // Cambiar el metodo para que devuelva varios personajes aleatorios
    public List<CharacterBean> getRandomCharactersForTest(CharacterBean oSelectedCharacterBean, int numElements) {
        List<CharacterBean> allCharacters = fetchAllCharacters();

        if (numElements < 1) {
            numElements = 4;
        }

        // Filtramos personajes con elemento válido
        List<CharacterBean> validCharacters = new ArrayList<>();
        for (CharacterBean c : allCharacters) {
            if (c.getElement() != null && !c.getElement().trim().isEmpty()) {
                validCharacters.add(c);
            }
        }

        // Barajamos la lista para obtener aleatorios
        Collections.shuffle(validCharacters);

        // Lista final que devolveremos
        List<CharacterBean> selectedCharacters = new ArrayList<>();

        // Añadimos el personaje seleccionado al principio
        selectedCharacters.add(oSelectedCharacterBean);

        // Añadimos aleatorios hasta alcanzar el número pedido (sin repetir elemento)
        for (CharacterBean c : validCharacters) {
            if (selectedCharacters.size() >= numElements) break;
            boolean alreadyContainsSameElement = selectedCharacters.stream()
                .anyMatch(ch -> ch.getElement().equalsIgnoreCase(c.getElement()));
            if (!alreadyContainsSameElement) {
                selectedCharacters.add(c);
            }
        }

        // Barajamos el orden final
        Collections.shuffle(selectedCharacters);

        return selectedCharacters;
    }


}