package net.ausiasmarch.swift.model;

public class SongBean {
    private String cancion;
    private String album;

    public SongBean() {
    }

    public SongBean(String cancion, String album) {
        this.cancion = cancion;
        this.album = album;
    }

    public String getCancion() {
        return cancion;
    }

    public void setCancion(String cancion) {
        this.cancion = cancion;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
}
