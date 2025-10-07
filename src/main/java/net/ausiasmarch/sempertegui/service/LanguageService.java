package net.ausiasmarch.sempertegui.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
//import org.springframework.web.client.RestTemplate;

public class LanguageService {

    private static final String API_URL = "https://api.mymemory.translated.net/get?q=%s&langpair=es|en";
    public static List<String> spanishDictionary = Arrays.asList("Rojo", "Amarillo", "Morado", "Marron", "Blanco",
                                                           "Muebles", "Mesa", "Sillon", "Armario", "Cajon",
                                                           "Toalla", "Salon", "Tenedores", "Ascensor", "Automovil",
                                                           "Casa", "Agua", "Arbol", "Sol", "Biblioteca",
                                                           "Almohada", "Precipicio", "Cocina", "Palo", "Salchicha",
                                                           "Rosa", "Negro", "Azul", "Verde","Cromo");

    public LanguageService() {
    }

    public static String translateWord(String selectedWord) {
        try {
            URL url = new URL(String.format(API_URL, selectedWord));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject json = new JSONObject(response.toString());
            return json.getJSONObject("responseData").getString("translatedText");

        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

   /* public static String translateWord(String selectedWord) {
        try {
            String url = String.format(API_URL, selectedWord);
            RestTemplate restTemplate = new RestTemplate();
            String content = restTemplate.getForObject(url, String.class);
            
            JSONObject json = new JSONObject(content);
            
            return json.getJSONObject("responseData").getString("translatedText");

        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }*/

    public String getOneRandomWord() {

        return spanishDictionary.get(new Random().nextInt(spanishDictionary.size()));
    }

    public List<String> getRandomWordsOptionsList(String selectedWord, int numWords) {

        if (numWords < 1) {
            numWords = 4;
        }

        List<String> randomWordsOptionsList = new ArrayList<String>();

        randomWordsOptionsList.add(selectedWord);

        for (int i = 0; i < numWords - 1; i++) {
            int randomIndex = 0;
            while (randomIndex == 0) {
                randomIndex = (int) (Math.random() * spanishDictionary.size());
                if (spanishDictionary.get(randomIndex).trim().isEmpty()) {
                    randomIndex = 0;
                } else {
                    if (randomWordsOptionsList.contains(spanishDictionary.get(randomIndex))) {
                        randomIndex = 0;
                    }
                }
            }
            randomWordsOptionsList.add(spanishDictionary.get(randomIndex));
        }

        Collections.shuffle(randomWordsOptionsList);
        return randomWordsOptionsList;

    }

}