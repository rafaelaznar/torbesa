package net.ausiasmarch.starwars.model;

public class CharacterBean {
    private String name;
    private String url;
    private int id;
    private String species;

    public CharacterBean(String name, String url, int id, String species) {
        this.name = name;
        this.url = url;
        this.id = id;
        this.species = species;
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

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }   
}