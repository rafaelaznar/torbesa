package net.ausiasmarch.kimetsu.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletContext;

import org.json.JSONArray;
import org.json.JSONObject;

import net.ausiasmarch.kimetsu.model.KimetsuBean;

public class KimetsuService {

    private static final String API_URL = "https://www.demonslayer-api.com/api/v1/characters?limit=50&page=1";
    private ServletContext oContext = null;

    public KimetsuService(ServletContext oContext) {
        this.oContext = oContext;
    }

    public List<KimetsuBean> getAllKimetsu() {
        if (this.oContext != null) {
            @SuppressWarnings("unchecked")
            List<KimetsuBean> kimetsus = (List<KimetsuBean>) this.oContext.getAttribute("kimetsus");
            if (kimetsus != null && !kimetsus.isEmpty()) {
                return kimetsus;
            }
        }

        List<KimetsuBean> kimetsus = new ArrayList<>();
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            // Leer el objeto raíz
            JSONObject rootObj = new JSONObject(content.toString());
            JSONArray arr = rootObj.getJSONArray("content"); // extraer "content"

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                KimetsuBean kimetsuBean = new KimetsuBean();
                kimetsuBean.setName(obj.optString("name", "Desconocido"));
                kimetsuBean.setQuote(obj.optString("quote", "Sin frase disponible"));
                kimetsus.add(kimetsuBean);
            }

        } catch (Exception e) {
            System.err.println("Error al descargar los datos de la API: " + e.getMessage());
            e.printStackTrace();
        }

        if (this.oContext != null) {
            this.oContext.setAttribute("kimetsus", kimetsus);
        }

        return kimetsus;
    }

    public KimetsuBean getOneRandomKimetsu() {
        List<KimetsuBean> oKimetsus = getAllKimetsu();
        if (oKimetsus.isEmpty()) {
            System.err.println("⚠ No se descargaron personajes de Kimetsu.");
            return null;
        }
        int randomIndex = (int) (Math.random() * oKimetsus.size());
        return oKimetsus.get(randomIndex);
    }

    public ArrayList<String> getRandomKimetsuOptionsForTest(KimetsuBean selected, int numOptions) {
        ArrayList<String> optionsList = new ArrayList<>();
        List<KimetsuBean> allKimetsus = getAllKimetsu();

        if (selected == null || allKimetsus.isEmpty()) {
            return optionsList;
        }

        optionsList.add(selected.getQuote());

        while (optionsList.size() < numOptions && optionsList.size() < allKimetsus.size()) {
            int randomIndex = (int) (Math.random() * allKimetsus.size());
            String randomQuote = allKimetsus.get(randomIndex).getQuote();
            if (!randomQuote.isBlank() && !optionsList.contains(randomQuote)) {
                optionsList.add(randomQuote);
            }
        }

        Collections.shuffle(optionsList);
        return optionsList;
    }

    public KimetsuBean getKimetsuByName(String name) {
        for (KimetsuBean kimetsu : getAllKimetsu()) {
            if (kimetsu.getName().equalsIgnoreCase(name)) {
                return kimetsu;
            }
        }
        return null;
    }
}
