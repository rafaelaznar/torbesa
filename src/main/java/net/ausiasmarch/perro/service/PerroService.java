package net.ausiasmarch.perro.service;

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

import net.ausiasmarch.capitals.model.CountryBean;

public class PerroService {
     private static final String API_URL = "https://dog.ceo/api/breeds/image/random";
    private ServletContext oContext = null;

    public PerroService(ServletContext oContext) {
        this.oContext = oContext;
    }
        public static class PreguntaRaza {
            public String imagenUrl;
            public String razaCorrecta;
            public List<String> opciones;
            public PreguntaRaza(String imagenUrl, String razaCorrecta, List<String> opciones) {
                this.imagenUrl = imagenUrl;
                this.razaCorrecta = razaCorrecta;
                this.opciones = opciones;
            }
        }

        // Simulación: deberías obtener estos datos de la API real
        public PreguntaRaza obtenerPreguntaRaza() {
            String imagenUrl = "https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg";
            String razaCorrecta = "Afghan Hound";
            List<String> opciones = new ArrayList<>();
            opciones.add(razaCorrecta);
            opciones.add("Beagle");
            opciones.add("Boxer");
            Collections.shuffle(opciones);
            return new PreguntaRaza(imagenUrl, razaCorrecta, opciones);
        }

    public List<CountryBean> fetchAllCountries() {

        if (this.oContext != null) {
            // obtener el atributo "countries" del contexto
            @SuppressWarnings("unchecked")
            List<CountryBean> countries = (List<CountryBean>) this.oContext.getAttribute("countries");
            // si oContext.getAttribute("countries") es distinto de null
            if (countries != null) {
                return countries;
            }
        }
        // obtener countries y guardarlo en el contexto
        System.out.println("Downloading countries....");
        List<CountryBean> countries = new ArrayList<>();
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
                String capital = obj.has("capital") ? obj.getJSONArray("capital").optString(0, "") : "";
                countries.add(new CountryBean(name, capital));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.oContext.setAttribute("countries", countries);
        return countries;

    }

    public CountryBean getCountryByName(String name) {
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

    public CountryBean getOneRandomCountry() {
        List<CountryBean> oCountries = fetchAllCountries();
        int randomIndex0 = (int) (Math.random() * oCountries.size());
        CountryBean selectedCountry = oCountries.get(randomIndex0);
        while (selectedCountry.getCapital().trim().isEmpty()) {
            randomIndex0 = (int) (Math.random() * oCountries.size());
            selectedCountry = oCountries.get(randomIndex0);
        }
        return selectedCountry;
    }

    public ArrayList<String> getRandomCapitalsForTest(CountryBean oSelectedCountryBean, int numCapitals) {
        if (numCapitals < 1) {
            numCapitals = 4;
        }
        List<CountryBean> oCountries = fetchAllCountries();

        ArrayList<String> selectedCapitalsList = new ArrayList<>();

        selectedCapitalsList.add(oSelectedCountryBean.getCapital());
        for (int i = 0; i < numCapitals - 1; i++) {
            int randomIndex = 0;
            while (randomIndex == 0) {
                randomIndex = (int) (Math.random() * oCountries.size());
                if (oCountries.get(randomIndex).getCapital().trim().isEmpty()) {
                    randomIndex = 0;
                } else {
                    if (selectedCapitalsList.contains(oCountries.get(randomIndex).getCapital())) {
                        randomIndex = 0;
                    }
                }
            }
            selectedCapitalsList.add(oCountries.get(randomIndex).getCapital());
        }

        Collections.shuffle(selectedCapitalsList);
        return selectedCapitalsList;

    }
}
