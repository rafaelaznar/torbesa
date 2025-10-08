package net.ausiasmarch.harrypotter.service;

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

import net.ausiasmarch.harrypotter.model.HarryPotterCharacterBean;

public class HarryPotterCharacterService {

    private static final String API_URL = "https://hp-api.onrender.com/api/characters";
    private ServletContext oContext = null;

    public HarryPotterCharacterService(ServletContext oContext) {
        this.oContext = oContext;
    }

    // ✅ Descarga y guarda los personajes de la API en memoria (ServletContext)
    public List<HarryPotterCharacterBean> fetchAllCharacters() {
        if (this.oContext != null) {
            @SuppressWarnings("unchecked")
            List<HarryPotterCharacterBean> characters =
                    (List<HarryPotterCharacterBean>) this.oContext.getAttribute("harryPotterCharacters");
            if (characters != null) {
                return characters;
            }
        }

        System.out.println("Downloading Harry Potter characters...");
        List<HarryPotterCharacterBean> characters = new ArrayList<>();

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
                String name = obj.optString("name", "");
                String house = obj.optString("house", "");

                // Nos interesan personajes con nombre y casa
                if (!name.isEmpty() && !house.isEmpty()) {
                    characters.add(new HarryPotterCharacterBean(name, house));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (this.oContext != null) {
            this.oContext.setAttribute("harryPotterCharacters", characters);
        }

        return characters;
    }

    // ✅ Obtener personaje por nombre
    public HarryPotterCharacterBean getCharacterByName(String name) {
        for (HarryPotterCharacterBean character : fetchAllCharacters()) {
            if (character.getName().equalsIgnoreCase(name)) {
                return character;
            }
        }
        return null;
    }

    // ✅ Obtener una pregunta aleatoria: personaje + opciones de casa
    public HarryPotterCharacterBean getOneRandomCharacter() {
        List<HarryPotterCharacterBean> characters = fetchAllCharacters();
        int randomIndex = (int) (Math.random() * characters.size());
        return characters.get(randomIndex);
    }

    // ✅ Generar lista de opciones (1 correcta + varias falsas)
    public ArrayList<String> getRandomHousesForTest(HarryPotterCharacterBean selectedCharacter, int numOptions) {
        if (numOptions < 2) {
            numOptions = 4;
        }

        List<HarryPotterCharacterBean> allCharacters = fetchAllCharacters();
        ArrayList<String> options = new ArrayList<>();
        options.add(selectedCharacter.getHouse());

        while (options.size() < numOptions) {
            int randomIndex = (int) (Math.random() * allCharacters.size());
            String house = allCharacters.get(randomIndex).getHouse();
            if (!house.trim().isEmpty() && !options.contains(house)) {
                options.add(house);
            }
        }

        Collections.shuffle(options);
        return options;
    }

     // ✅ Método para inicializar los datos al arrancar la aplicación
    public static void initialize(ServletContext context) {
        HarryPotterCharacterService service = new HarryPotterCharacterService(context);
        List<HarryPotterCharacterBean> characters = service.fetchAllCharacters();
        System.out.println("✅ Harry Potter characters initialized: " + characters.size() + " loaded.");
    }

}