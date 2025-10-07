package net.ausiasmarch.starwars.model;

public class CharacterBean {
    private String name;
    private String url;
    private int id;
    private String clue; // <--- NUEVO ATRIBUTO para la pista

    public CharacterBean(String name, String url, int id, String clue) {
        this.name = name;
        this.url = url;
        this.id = id;
        this.clue = clue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClue() {
        return clue;
    }

    public void setClue(String clue) {
        this.clue = clue;
    }   
}