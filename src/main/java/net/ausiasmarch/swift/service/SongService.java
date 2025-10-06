package net.ausiasmarch.swift.service;

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
import net.ausiasmarch.swift.model.SongBean;

public class SongService {
    private static final String API_URL = "https://taylor-swift-api.sarbo.workers.dev/songs?fields=title&fields=album_id";
    private ServletContext oContext = null;

    public SongService(ServletContext oContext) {
        this.oContext = oContext;
    }

    public List<SongBean> fetchAllSongs() {

        if (this.oContext != null) {
            // obtener el atributo "songs" del contexto
            @SuppressWarnings("unchecked")
            List<SongBean> songs = (List<SongBean>) this.oContext.getAttribute("songs");
            // si oContext.getAttribute("songs") es distinto de null
            if (songs != null) {
                return songs;
            }
        }
        // obtener songs y guardarlo en el contexto
        System.out.println("Downloading songs....");
        List<SongBean> songs = new ArrayList<>();
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
                String song = obj.getJSONObject("song").getString("title");
                String album = obj.has("album") ? obj.getJSONArray("album_id").optString(0, "") : "";
                songs.add(new SongBean(song, album));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.oContext.setAttribute("songs", songs);
        return songs;

    }

    public SongBean getSongByName(String name) {
        for (SongBean song : fetchAllSongs()) {
            if (song.getCancion().equalsIgnoreCase(name)) {
                return song;
            }
        }
        return null;
    }

    public String getAlbumBySong(String name) {
        SongBean song = getSongByName(name);
        return (song != null) ? song.getAlbum() : null;
    }

    public String getAlbumBySongName(String name) {
        for (SongBean song : fetchAllSongs()) {
            if (song.getAlbum().equalsIgnoreCase(name)) {
                return song.getCancion();
            }
        }
        return null;
    }

    public SongBean getOneRandomSong() {
        List<SongBean> oSongs = fetchAllSongs();
        int randomIndex0 = (int) (Math.random() * oSongs.size());
        SongBean selectedSong = oSongs.get(randomIndex0); 
        while (selectedSong.getAlbum().trim().isEmpty()) {
            randomIndex0 = (int) (Math.random() * oSongs.size());
            selectedSong = oSongs.get(randomIndex0);
        }
        return selectedSong;
    }

    public ArrayList<String> getRandomAlbumsForTest(SongBean oSelectedSongBean, int numAlbums) {
        if (numAlbums < 1) {
            numAlbums = 4;
        }
        List<SongBean> oSongs = fetchAllSongs();

        ArrayList<String> selectedAlbumsList = new ArrayList<>();

        selectedAlbumsList.add(oSelectedSongBean.getAlbum());
        for (int i = 0; i < numAlbums - 1; i++) {
            int randomIndex = 0;
            while (randomIndex == 0) {
                randomIndex = (int) (Math.random() * oSongs.size());
                if (oSongs.get(randomIndex).getAlbum().trim().isEmpty()) {
                    randomIndex = 0;
                } else {
                    if (selectedAlbumsList.contains(oSongs.get(randomIndex).getAlbum())) {
                        randomIndex = 0;
                    }
                }
            }
            selectedAlbumsList.add(oSongs.get(randomIndex).getAlbum());
        }

        Collections.shuffle(selectedAlbumsList);
        return selectedAlbumsList;

    }
}
