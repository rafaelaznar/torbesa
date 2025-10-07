package net.ausiasmarch.genshinPav.service;

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

import net.ausiasmarch.genshinPav.model.GenshinBean;

public class GenshinService {

    private static final String API_URL = "https://genshin.jmp.blue/characters/all";
    private ServletContext oContext = null;

    public GenshinService(ServletContext oContext) {
        this.oContext = oContext;
    }

    public List<GenshinBean> fetchAllCharacters() {
        List<GenshinBean> characters = null;
        if (this.oContext != null) {
            @SuppressWarnings("unchecked")
            List<GenshinBean> ctxCharacters = (List<GenshinBean>) this.oContext.getAttribute("characters");
            if (ctxCharacters != null && !ctxCharacters.isEmpty()) {
                return ctxCharacters;
            }
        }
        // Si no hay personajes en el contexto o está vacío, descarga de la API
        System.out.println("Entrando a descarga de personajes");
        System.out.println("Downloading characters....");
        characters = new ArrayList<>();
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
            System.out.println("Respuesta de la API: " + content.toString());
            JSONArray arr = new JSONArray(content.toString());
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                String name = obj.getString("name");
                String vision = obj.optString("vision", "");
                characters.add(new GenshinBean(name, vision));
            }
            System.out.println("Personajes parseados: " + characters.size());
        } catch (Exception e) {
            System.err.println("Error descargando personajes de la API: " + e.getMessage());
        }
        // Guarda en contexto solo si hay personajes
        if (this.oContext != null && !characters.isEmpty()) {
            this.oContext.setAttribute("characters", characters);
        }
        return characters;
    }

    public GenshinBean getElementByName(String name) {
        for (GenshinBean element : fetchAllCharacters()) {
            if (element.getName().equalsIgnoreCase(name)) {
                return element;
            }
        }
        return null;
    }

    public String getelementByElementName(String name) {
        GenshinBean element = getElementByName(name);
        return (element != null) ? element.getVision() : null;
    }

    public String getElementByelementName(String name) {
        for (GenshinBean element : fetchAllCharacters()) {
            if (element.getVision().equalsIgnoreCase(name)) {
                return element.getName();
            }
        }
        return null;
    }

    public GenshinBean getOneRandomElement() {
        List<GenshinBean> oCharacters = fetchAllCharacters();
        if (oCharacters == null || oCharacters.isEmpty()) {
            throw new IllegalStateException("No hay personajes disponibles para seleccionar.");
        }
        int randomIndex0 = (int) (Math.random() * oCharacters.size());
        GenshinBean selectedElement = oCharacters.get(randomIndex0);
        // Si el campo vision está vacío, busca otro (máximo 10 intentos para evitar bucles infinitos)
        int attempts = 0;
        while (selectedElement.getVision().trim().isEmpty() && attempts < 10) {
            randomIndex0 = (int) (Math.random() * oCharacters.size());
            selectedElement = oCharacters.get(randomIndex0);
            attempts++;
        }
        return selectedElement;
    }

    public ArrayList<String> getRandomgenshinForTest(GenshinBean oSelectedElementBean, int numgenshin) {
        if (numgenshin < 1) {
            numgenshin = 4;
        }
        List<GenshinBean> oCharacters = fetchAllCharacters();

        ArrayList<String> selectedgenshinList = new ArrayList<>();

        selectedgenshinList.add(oSelectedElementBean.getVision());
        for (int i = 0; i < numgenshin - 1; i++) {
            int randomIndex = 0;
            while (randomIndex == 0) {
                randomIndex = (int) (Math.random() * oCharacters.size());
                if (oCharacters.get(randomIndex).getVision().trim().isEmpty()) {
                    randomIndex = 0;
                } else {
                    if (selectedgenshinList.contains(oCharacters.get(randomIndex).getVision())) {
                        randomIndex = 0;
                    }
                }
            }
            selectedgenshinList.add(oCharacters.get(randomIndex).getVision());
        }

        Collections.shuffle(selectedgenshinList);
        return selectedgenshinList;

    }

}