package net.ausiasmarch.capitals.models;

import java.util.List;

public class Country {
    private String name;
    private List<String> borders;
    private String cioc;
    private String capital;

    public Country(String name, List<String> borders, String cioc, String capital) {
        this.name = name;
        this.borders = borders;
        this.cioc = cioc;
        this.capital = capital;
    }

    public String getName() {
        return name;
    }

    public List<String> getBorders() {
        return borders;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBorders(List<String> borders) {
        this.borders = borders;
    }

    public String getCioc() {
        return cioc;
    }

    public void setCioc(String cioc) {
        this.cioc = cioc;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }
}
