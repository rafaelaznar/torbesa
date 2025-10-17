package net.ausiasmarch.codequest.model;

public class TechnologyBean {

    private String name;
    private String type;
    private String description;
    private String category;
    private String difficulty;

    public TechnologyBean(String name, String type, String description, String category, String difficulty) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.category = category;
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
