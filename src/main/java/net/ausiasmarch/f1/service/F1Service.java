package net.ausiasmarch.f1.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import net.ausiasmarch.f1.model.DriverBean;

public class F1Service {

    private static final String API_URL_TEMPLATE = "https://api.openf1.org/v1/drivers?driver_number=%d&session_key=9158";
    private ServletContext oContext = null;

    public F1Service(ServletContext oContext) {
        this.oContext = oContext;
    }

    public List<DriverBean> fetchDrivers() {

        if (this.oContext != null) {
            @SuppressWarnings("unchecked")
            List<DriverBean> drivers = (List<DriverBean>) this.oContext.getAttribute("drivers");
            if (drivers != null) {
                return drivers;
            }
        }

        List<DriverBean> drivers = new ArrayList<>();
    HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(String.format(API_URL_TEMPLATE, 1)))
                .timeout(Duration.ofSeconds(10))
                .header("Accept", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                String body = response.body();
                JSONArray arr = new JSONArray(body);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    // API fields: full_name and team_name
                    String fullName = obj.optString("full_name", "");
                    String teamName = obj.optString("team_name", "");
                    drivers.add(new DriverBean(fullName, teamName));
                }
            } else {
                // non-200, return empty list (or keep previous caching behavior)
                System.err.println("F1Service: unexpected response status " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (this.oContext != null) {
            this.oContext.setAttribute("drivers", drivers);
        }
        return drivers;
    }

    public DriverBean getOneRandomDriver() {
        List<DriverBean> d = fetchDrivers();
        if (d.isEmpty()) return new DriverBean("", "");
        int idx = (int) (Math.random() * d.size());
        return d.get(idx);
    }

    /**
     * Fetch a driver by explicit driver number (returns first element or null)
     */
    public DriverBean fetchDriverByNumber(int driverNumber) {
        HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
        String url = String.format(API_URL_TEMPLATE, driverNumber);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofSeconds(10)).header("Accept", "application/json").GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                JSONArray arr = new JSONArray(response.body());
                if (arr.length() > 0) {
                    JSONObject obj = arr.getJSONObject(0);
                    String fullName = obj.optString("full_name", "");
                    String teamName = obj.optString("team_name", "");
                    return new DriverBean(fullName, teamName);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Try to fetch a driver by full name using the API (if supported). Falls back to cached list.
     */
    public DriverBean fetchDriverByFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) return null;
        String encoded = URLEncoder.encode(fullName.trim(), StandardCharsets.UTF_8);
        String url = String.format("https://api.openf1.org/v1/drivers?full_name=%s&session_key=9158", encoded);
        HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofSeconds(10)).header("Accept", "application/json").GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                JSONArray arr = new JSONArray(response.body());
                if (arr.length() > 0) {
                    JSONObject obj = arr.getJSONObject(0);
                    String fn = obj.optString("full_name", "");
                    String tn = obj.optString("team_name", "");
                    return new DriverBean(fn, tn);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // fallback: search cached drivers by full name
        for (DriverBean d : fetchDrivers()) {
            if (d.getName() != null && d.getName().equalsIgnoreCase(fullName.trim())) {
                return d;
            }
        }
        return null;
    }

    /**
     * Try random driver numbers until a valid driver is returned (max attempts)
     */
    public DriverBean fetchRandomDriver() {
        int attempts = 0;
        while (attempts < 8) {
            int randomNumber = 1 + (int) (Math.random() * 99); // 1..99
            DriverBean db = fetchDriverByNumber(randomNumber);
            if (db != null && db.getName() != null && !db.getName().isEmpty()) {
                return db;
            }
            attempts++;
        }
        // fallback to cached list
        return getOneRandomDriver();
    }

    public List<String> getDistinctTeamsFromCache() {
        List<DriverBean> list = fetchDrivers();
        java.util.Set<String> set = new java.util.LinkedHashSet<>();
        for (DriverBean d : list) {
            if (d.getTeam() != null && !d.getTeam().isEmpty()) {
                set.add(d.getTeam());
            }
        }
        return new ArrayList<>(set);
    }

}
