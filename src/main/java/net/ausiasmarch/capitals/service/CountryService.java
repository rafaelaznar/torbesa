package net.ausiasmarch.capitals.service;

import java.util.List;

import javax.servlet.ServletContext;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import net.ausiasmarch.capitals.model.CountryBean;

public class CountryService {

    private static final String API_URL = "https://restcountries.com/v3.1/all?fields=name&fields=capital";
    private ServletContext oContext = null;

    public CountryService(ServletContext oContext) {
        this.oContext = oContext;
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
                List<String> borders = new ArrayList<>();
                if (obj.has("borders")) {
                    JSONArray bordersArr = obj.getJSONArray("borders");
                    for (int j = 0; j < bordersArr.length(); j++) {
                        borders.add(bordersArr.getString(j));
                    }
                }
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

}