package net.ausiasmarch.pokemon.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.ServletContext;

import org.json.JSONArray;
import org.json.JSONObject;

import net.ausiasmarch.pokemon.model.Pokemon;

public class PokemonService {
    private static final String API_URL = "https://pokeapi.co/api/v2/pokemon?limit=1010";
    private ServletContext oContext = null;

    public PokemonService(ServletContext oContext) {
        this.oContext = oContext;
    }

    public List<Pokemon> fetchAllPokemonList() {

        if (this.oContext != null) {
            @SuppressWarnings("unchecked")
            List<Pokemon> pokemons = (List<Pokemon>) this.oContext.getAttribute("pokemons");
            if (pokemons != null) {
                return pokemons;
            }
        }
        System.out.println("Downloading pokemons....");
        List<Pokemon> pokemons = new ArrayList<>();
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

            JSONArray arr = null;
            String json = content.toString().trim();
            if (json.startsWith("[")) {
                arr = new JSONArray(json);
            } else if (json.startsWith("{")) {
                JSONObject root = new JSONObject(json);
                if (root.has("results") && root.get("results") instanceof JSONArray) {
                    arr = root.getJSONArray("results");
                } else {
                    for (String key : root.keySet()) {
                        Object value = root.get(key);
                        if (value instanceof JSONArray) {
                            arr = (JSONArray) value;
                            break;
                        }
                    }
                }
            }

            if (arr != null) {
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.optJSONObject(i);
                    if (obj == null)
                        continue;
                    String name = obj.optString("name", null);
                    if (name == null || name.trim().isEmpty())
                        continue;
                    pokemons.add(new Pokemon(i + 1, name));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.oContext != null) {
            this.oContext.setAttribute("pokemons", pokemons);
        }
        return pokemons;

    }

    public Pokemon getPokemonByName(String name) {
        for (Pokemon pokemon : fetchAllPokemonList()) {
            if (pokemon.getName().equalsIgnoreCase(name)) {
                return pokemon;
            }
        }
        return null;
    }

    public Pokemon getOneRandomPokemonByName() {
        List<Pokemon> list = fetchAllPokemonList();
        if (list == null || list.isEmpty())
            return null;
        int idx = ThreadLocalRandom.current().nextInt(list.size());
        return list.get(idx);
    }

    public ArrayList<String> getRandomNamesForTest(Pokemon selectedPokemon, int numOptions) {
        if (numOptions < 1)
            numOptions = 4;
        List<Pokemon> list = fetchAllPokemonList();
        ArrayList<String> names = new ArrayList<>();
        if (selectedPokemon == null)
            return names;
        names.add(selectedPokemon.getName());
        int attempts = 0;
        while (names.size() < numOptions && attempts < numOptions * 5) {
            attempts++;
            int rnd = ThreadLocalRandom.current().nextInt(list.size());
            String candidate = list.get(rnd).getName();
            if (!names.contains(candidate)) {
                names.add(candidate);
            }
        }
        Collections.shuffle(names);
        return names;
    }

    public Pokemon fetchPokemonDetails(int id) {
        String urlString = "https://pokeapi.co/api/v2/pokemon/" + id + "/";
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            JSONObject obj = new JSONObject(content.toString());
            String name = obj.getString("name");
            JSONArray abilitiesArray = obj.getJSONArray("abilities");
            List<String> abilities = new ArrayList<>();

            for (int i = 0; i < abilitiesArray.length(); i++) {
                JSONObject abilityObj = abilitiesArray.getJSONObject(i).getJSONObject("ability");
                String abilityName = abilityObj.getString("name");
                abilities.add(abilityName);
            }

            Pokemon pokemon = new Pokemon(id, name);
            pokemon.setAbilities(abilities);
            return pokemon;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
