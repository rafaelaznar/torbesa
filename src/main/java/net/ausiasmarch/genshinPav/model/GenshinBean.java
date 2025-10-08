package net.ausiasmarch.genshinPav.model;

public class GenshinBean {
    private String name;
    private String vision;

    public GenshinBean(String name, String vision) {
        this.name = name;
        this.vision = vision;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    @Override
    public String toString() {
    return "GenshinBean{name='" + name + "', vision='" + vision + "'}";
}
}
