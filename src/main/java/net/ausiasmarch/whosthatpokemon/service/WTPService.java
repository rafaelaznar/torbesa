package net.ausiasmarch.whosthatpokemon.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import net.ausiasmarch.whosthatpokemon.model.PokemonBean;

public class WTPService {
    private static final String API_URL = "https://pokeapi.co/api/v2/pokemon?limit=100000&offset=0";
    private static final String POKEMON_BY_ID = "https://pokeapi.co/api/v2/pokemon/"; // append id or name
    private static final int MAX_POKEMON_ID = 1010; // safe upper bound

    public WTPService() {
    }

    public PokemonBean getPokemonById(int id) {
        try {
            URL url = new URL(POKEMON_BY_ID + id + "/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            int status = conn.getResponseCode();
            if (status != 200) {
                // fallback: try without trailing slash
                url = new URL(POKEMON_BY_ID + id);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                status = conn.getResponseCode();
            }
            if (status != 200) {
                return null;
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();

            JSONObject obj = new JSONObject(content.toString());
            String name = obj.optString("name", null);
            String sprite = null;
            if (obj.has("sprites")) {
                JSONObject sprites = obj.getJSONObject("sprites");
                sprite = sprites.optString("front_default", null);
                // sometimes sprites.other.home.front_default exists with higher res
                if ((sprite == null || sprite.equals("null")) && sprites.has("other")) {
                    JSONObject other = sprites.getJSONObject("other");
                    if (other.has("official-artwork")) {
                        JSONObject oa = other.getJSONObject("official-artwork");
                        sprite = oa.optString("front_default", sprite);
                    }
                }
            }
            return new PokemonBean(name, sprite);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PokemonBean getRandomPokemon() {
        int id = 1 + (int) (Math.random() * (MAX_POKEMON_ID));
        PokemonBean p = getPokemonById(id);
        // If API returns null or missing sprite, retry a few times
        int attempts = 0;
        while ((p == null || p.getSpriteUrl() == null) && attempts < 5) {
            id = 1 + (int) (Math.random() * (MAX_POKEMON_ID));
            p = getPokemonById(id);
            attempts++;
        }
        return p;
    }

}
