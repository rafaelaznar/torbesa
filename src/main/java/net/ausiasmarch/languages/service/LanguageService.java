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
    public static List<String> dictionary = Arrays.asList("Rojo", "Amarillo", "Morado", "Marrón", "Blanco",
                                                           "Muebles", "Mesa", "Sillón", "Armario", "Cajón",
                                                           "Baño", "Salón", "Tenedores", "Tazón", "Automovil",
                                                           "Casa", "Agua", "Árbol", "Sol", "Biblioteca");

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

        return dictionary.get(random.nextInt(dictionary.size()));
    }

    public List<String> getRandomWordsOptionsList(String selectedWordTranslated, int numWords) {

        if (numWords < 1) {
            numWords = 4;
        }

        List<String> randomWordsOptionsList = new ArrayList<>();

        randomWordsOptionsList.add(selectedWordTranslated);

        for (int i = 0; i < numWords - 1; i++) {
            int randomIndex = 0;
            while (randomIndex == 0) {
                randomIndex = (int) (Math.random() * dictionary.size());
                if (dictionary.get(randomIndex).trim().isEmpty()) {
                    randomIndex = 0;
                } else {
                    if (randomWordsOptionsList.contains(dictionary.get(randomIndex))) {
                        randomIndex = 0;
                    }
                }
            }
            randomWordsOptionsList.add(dictionary.get(randomIndex));
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