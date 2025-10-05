package net.ausiasmarch.whosthatpokemon.model;

public class PokemonBean {
    private String name;
    private String spriteUrl;

    public PokemonBean() {
    }

    public PokemonBean(String name, String spriteUrl) {
        this.name = name;
        this.spriteUrl = spriteUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpriteUrl() {
        return spriteUrl;
    }

    public void setSpriteUrl(String spriteUrl) {
        this.spriteUrl = spriteUrl;
    }

    @Override
    public String toString() {
        return "PokemonBean{name='" + name + "', spriteUrl='" + spriteUrl + "'}";
    }
}
