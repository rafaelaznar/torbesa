package net.ausiasmarch.kimetsu.model;

public class KimetsuBean {
    private String name;
    private String quote;
    private String correct;

    public KimetsuBean() {
    }

    public KimetsuBean(String name, String quote, String correct) {
        this.name = name;
        this.quote = quote;
        this.correct = correct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getCorrect() { // ✅ nuevo getter
        return correct;
    }

    public void setCorrect(String correct) { // ✅ nuevo setter
        this.correct = correct;
    }

    @Override
    public String toString() {
        return "KimetsuBean{name='" + name + "', quote='" + quote + "', correct='" + correct + "'}";
    }
}
