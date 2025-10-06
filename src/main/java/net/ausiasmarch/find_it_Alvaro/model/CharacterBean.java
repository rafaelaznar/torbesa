package net.ausiasmarch.find_it_Alvaro.model;

public class CharacterBean {
    private String id;
    private String name;
    private int rarity;
    private String element;

    public CharacterBean(String id, String name, int rarity, String element) {
        this.id = id;
        this.name = name;
        this.rarity = rarity;
        this.element = element;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }
}
