package net.ausiasmarch.kimetsu.model;

public class KimetsuBean {
    private String name;
    private String correct;

    public KimetsuBean(String name, String correct) {
        this.name = name;
        this.correct = correct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }
}