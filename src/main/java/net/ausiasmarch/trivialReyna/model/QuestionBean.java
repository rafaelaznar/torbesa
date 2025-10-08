package net.ausiasmarch.trivialReyna.model;

import java.util.List;

public class QuestionBean {
    private String question;
    private String correctAnswer;
    private List<String> options;

    public QuestionBean(String question, String correctAnswer, List<String> options) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.options = options;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
    
    
}
