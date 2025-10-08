package net.ausiasmarch.perro.model;

public class PerroBean {
  private String name;
    private String raza;

    public PerroBean(String name, String raza) {
        this.name = name;
        this.raza = raza;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }
    
}
