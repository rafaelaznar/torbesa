package net.ausiasmarch.whosthatpokemon.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import net.ausiasmarch.whosthatpokemon.model.PokemonBean;

public class WTPService {
    //importaremos el api de pokeapi usando su endpoint
    private static final String API_URL = "https://pokeapi.co/api/v2/pokemon?limit=100000&offset=0";
    private static final String POKEMON_BY_ID = "https://pokeapi.co/api/v2/pokemon/";
    private static final int MAX_POKEMON_ID = 1010;

    public WTPService() {
    }

    //usando el id del pokemon nos devuelve su nombre y sprite
    public PokemonBean getPokemonById(int id) {
        try {
            //montamos la url usando el id y así usarlo
            URL url = new URL(POKEMON_BY_ID + id + "/");
            //se conecta a la api usando el metodo get
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            int status = conn.getResponseCode();
            //si la conexión falla lo vuelve a intentar sin la barra del final
            if (status != 200) {
                url = new URL(POKEMON_BY_ID + id);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                status = conn.getResponseCode();
            }
            //si despues de intentarlo sin barra sigue fallando devuelve null
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
        //genera un id aleatorio entre 1 y el maximo de pokemons que hay en la api para así 
        int id = 1 + (int) (Math.random() * (MAX_POKEMON_ID));
        //nos manda a la funcion de arriba para que nos devuelva un pokemon
        PokemonBean p = getPokemonById(id);
        //si la api no nos devuelve nada o no tiene sprite lo intentará 5 veces más
        int attempts = 0;
        while ((p == null || p.getSpriteUrl() == null) && attempts < 5) {
            id = 1 + (int) (Math.random() * (MAX_POKEMON_ID));
            p = getPokemonById(id);
            attempts++;
        }
        //devuelve el pokemon
        return p;
    }
}
