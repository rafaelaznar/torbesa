package net.ausiasmarch.kimetsu.service;


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

import net.ausiasmarch.kimetsu.model.KimetsuBean;

public class KimetsuService {
     private static final String API_URL = "https://www.demonslayer-api.com/api/v1/characters"; // Cambia esto por la fuente real de datos de Kimetsu
    private ServletContext oContext = null;

    public KimetsuService(ServletContext oContext) {
        this.oContext = oContext;
    }

    public List<KimetsuBean> fetchAllKimetsu() {

        if (this.oContext != null) {
            // obtener el atributo "kimetsus" del contexto
            @SuppressWarnings("unchecked")
            List<KimetsuBean> kimetsus = (List<KimetsuBean>) this.oContext.getAttribute("kimetsus");
            // si oContext.getAttribute("kimetsus") es distinto de null
            if (kimetsus != null) {
                return kimetsus;
            }
        }
        // obtener kimetsus y guardarlo en el contexto
        System.out.println("Downloading kimetsu data....");
        List<KimetsuBean> kimetsus = new ArrayList<>();
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
                String name = obj.getString("name");
                String correct = obj.has("correct") ? obj.getString("correct") : "";
                kimetsus.add(new KimetsuBean(name, correct));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.oContext.setAttribute("kimetsus", kimetsus);
        System.out.println(kimetsus);
        return kimetsus;

    }

    public KimetsuBean getKimetsuByName(String name) {
        for (KimetsuBean kimetsu : fetchAllKimetsu()) {
            if (kimetsu.getName().equalsIgnoreCase(name)) {
                return kimetsu;
            }
        }
        return null;
    }

    public String getCorrectByKimetsuName(String name) {
        KimetsuBean kimetsu = getKimetsuByName(name);
        return (kimetsu != null) ? kimetsu.getCorrect() : null;
    }

    public KimetsuBean getOneRandomKimetsu() {
        List<KimetsuBean> oKimetsus = fetchAllKimetsu();
        int randomIndex0 = (int) (Math.random() * oKimetsus.size());
        KimetsuBean selectedKimetsu = oKimetsus.get(randomIndex0);
        while (selectedKimetsu.getCorrect().trim().isEmpty()) {
            randomIndex0 = (int) (Math.random() * oKimetsus.size());
            selectedKimetsu = oKimetsus.get(randomIndex0);
        }
        return selectedKimetsu;
    }

    public ArrayList<String> getRandomKimetsuOptionsForTest(KimetsuBean oSelectedKimetsuBean, int numOptions) {
        if (numOptions < 1) {
            numOptions = 4;
        }
        List<KimetsuBean> oKimetsus = fetchAllKimetsu();

        ArrayList<String> selectedOptionsList = new ArrayList<>();

        selectedOptionsList.add(oSelectedKimetsuBean.getCorrect());
        for (int i = 0; i < numOptions - 1; i++) {
            int randomIndex = 0;
            while (randomIndex == 0) {
                randomIndex = (int) (Math.random() * oKimetsus.size());
                if (oKimetsus.get(randomIndex).getCorrect().trim().isEmpty()) {
                    randomIndex = 0;
                } else {
                    if (selectedOptionsList.contains(oKimetsus.get(randomIndex).getCorrect())) {
                        randomIndex = 0;
                    }
                }
            }
            selectedOptionsList.add(oKimetsus.get(randomIndex).getCorrect());
        }

        Collections.shuffle(selectedOptionsList);
        return selectedOptionsList;

    }

}
