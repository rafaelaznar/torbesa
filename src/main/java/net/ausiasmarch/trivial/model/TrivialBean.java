package net.ausiasmarch.trivial.model;

import java.util.List;

public class TrivialBean {
     String question;
     List<String> options;
     String correctAnswer;

    public TrivialBean(String question, java.util.List<String> options, String correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public java.util.List<String> getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
