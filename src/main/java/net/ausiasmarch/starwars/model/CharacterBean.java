package net.ausiasmarch.starwars.model;

public class CharacterBean {
    private String name;
    private String url;
    private int id;
    private String imageUrl;

    public CharacterBean(String name, String url, int id, String imageUrl) {
        this.name = name;
        this.url = url;
        this.id = id;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }   
}