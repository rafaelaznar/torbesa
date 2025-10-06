package net.ausiasmarch.genshin.service;

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

import net.ausiasmarch.genshin.model.GenshinBean;

public class GenshinService {

    private static final String API_URL = "https://https://genshin.jmp.blue/characters";
    private ServletContext oContext = null;

    public GenshinService(ServletContext oContext) {
        this.oContext = oContext;
    }

    public List<GenshinBean> fetchAllCharacters() {

        if (this.oContext != null) {
            // obtener el atributo "characters" del contexto
            @SuppressWarnings("unchecked")
            List<GenshinBean> characters = (List<GenshinBean>) this.oContext.getAttribute("characters");
            // si oContext.getAttribute("characters") es distinto de null
            if (characters != null) {
                return characters;
            }
        }
        // obtener characters y guardarlo en el contexto
        System.out.println("Downloading characters....");
        List<GenshinBean> characters = new ArrayList<>();
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
                String name = obj.getJSONObject("name").getString("common");
                String vision = obj.has("vision") ? obj.getJSONArray("vision").optString(0, "") : "";
                characters.add(new GenshinBean(name, vision));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.oContext.setAttribute("characters", characters);
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
        return (element != null) ? element.getElement() : null;
    }

    public String getElementByelementName(String name) {
        for (GenshinBean element : fetchAllCharacters()) {
            if (element.getElement().equalsIgnoreCase(name)) {
                return element.getName();
            }
        }
        return null;
    }

    public GenshinBean getOneRandomElement() {
        List<GenshinBean> oCharacters = fetchAllCharacters();
        int randomIndex0 = (int) (Math.random() * oCharacters.size());
        GenshinBean selectedElement = oCharacters.get(randomIndex0);
        while (selectedElement.getElement().trim().isEmpty()) {
            randomIndex0 = (int) (Math.random() * oCharacters.size());
            selectedElement = oCharacters.get(randomIndex0);
        }
        return selectedElement;
    }

    public ArrayList<String> getRandomgenshinForTest(GenshinBean oSelectedElementBean, int numgenshin) {
        if (numgenshin < 1) {
            numgenshin = 4;
        }
        List<GenshinBean> oCharacters = fetchAllCharacters();

        ArrayList<String> selectedgenshinList = new ArrayList<>();

        selectedgenshinList.add(oSelectedElementBean.getElement());
        for (int i = 0; i < numgenshin - 1; i++) {
            int randomIndex = 0;
            while (randomIndex == 0) {
                randomIndex = (int) (Math.random() * oCharacters.size());
                if (oCharacters.get(randomIndex).getElement().trim().isEmpty()) {
                    randomIndex = 0;
                } else {
                    if (selectedgenshinList.contains(oCharacters.get(randomIndex).getElement())) {
                        randomIndex = 0;
                    }
                }
            }
            selectedgenshinList.add(oCharacters.get(randomIndex).getElement());
        }

        Collections.shuffle(selectedgenshinList);
        return selectedgenshinList;

    }

}