package net.ausiasmarch.languages.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

public class LanguageService {

    private static Random random = new Random();
    private static final String API_URL = "https://api.mymemory.translated.net/get?q=%s&langpair=es|en";
    public static List<String> spanishDictionary = Arrays.asList("Rojo", "Amarillo", "Morado", "Marron", "Blanco",
                                                           "Muebles", "Mesa", "Sillon", "Armario", "Cajon",
                                                           "Toalla", "Salon", "Tenedores", "Ascensor", "Automovil",
                                                           "Casa", "Agua", "Arbol", "Sol", "Biblioteca");

    public static List<String> englishDictionary = Arrays.asList("red", "yellow", "purple", "brown", "white",
                                                           "furniture", "table", "armchair", "drawer",
                                                           "towel", "lounge", "forks", "elevator", "car",
                                                           "Casa", "Agua", "tree", "Sun", "library");
    public LanguageService() {
    }


    /*private ServletContext oContext = null;

    public LanguageService(ServletContext oContext) {
        this.oContext = oContext;
    }*/


    public static String translateWord(String selectedWord) {
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
    }

    public String getOneRandomWord() {

        return spanishDictionary.get(random.nextInt(spanishDictionary.size()));
    }

    public List<String> getRandomWordsOptionsList(String selectedWord, int numWords) {

        if (numWords < 1) {
            numWords = 4;
        }

        List<String> randomWordsOptionsList = new ArrayList<>();

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

    /*public CountryBean getCountryByName(String name) {
        for (CountryBean country : fetchAllCountries()) {
            if (country.getName().equalsIgnoreCase(name)) {
                return country;
            }
        }
        return null;
    }

    public String getCapitalByCountryName(String name) {
        CountryBean country = getCountryByName(name);
        return (country != null) ? country.getCapital() : null;
    }

    public String getCountryByCapitalName(String name) {
        for (CountryBean country : fetchAllCountries()) {
            if (country.getCapital().equalsIgnoreCase(name)) {
                return country.getName();
            }
        }
        return null;
    }
*/
}