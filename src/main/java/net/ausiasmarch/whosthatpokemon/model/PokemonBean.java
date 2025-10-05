package net.ausiasmarch.whosthatpokemon.model;

//bean que representa el nombre y sprite del pokemon
public class PokemonBean {
    private String name;
    private String spriteUrl;

    //constructor vacio
    public PokemonBean() {
    }
    //constructor con parámetros
    public PokemonBean(String name, String spriteUrl) {
        this.name = name;
        this.spriteUrl = spriteUrl;
    }
    //getters y setters
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
