package net.ausiasmarch.emojiQuiz.model;

public class EmojiQuizBean {
    private int id;
    private String question;
    private String correctAnswer;
    private String option1;
    private String option2;

    public EmojiQuizBean(int id, String question, String correctAnswer, String option1, String option2) {
        this.id = id;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.option1 = option1;
        this.option2 = option2;
    }

    public EmojiQuizBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }
}
