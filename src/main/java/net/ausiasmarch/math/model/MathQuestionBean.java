package net.ausiasmarch.math.model;

public class MathQuestionBean {
    private String expression;
    private double answer;

    public MathQuestionBean() {}

    public MathQuestionBean(String expression, double answer) {
        this.expression = expression;
        this.answer = answer;
    }

    public String getExpression() { return expression; }
    public void setExpression(String expression) { this.expression = expression; }

    public double getAnswer() { return answer; }
    public void setAnswer(double answer) { this.answer = answer; }
}
