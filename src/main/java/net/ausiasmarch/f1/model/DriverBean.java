package net.ausiasmarch.f1.model;

public class DriverBean {
    private String name;
    private String team;

    public DriverBean() {
    }

    public DriverBean(String name, String team) {
        this.name = name;
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}
