package net.ausiasmarch.genshin.model;

public class GenshinBean {
    private String name;
    private String element;

    public GenshinBean(String name, String element) {
        this.name = name;
        this.element = element;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }
}
