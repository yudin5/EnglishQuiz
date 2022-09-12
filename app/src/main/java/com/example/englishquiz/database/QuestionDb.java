package com.example.englishquiz.database;

public class QuestionDb {

    private String question;
    private String firstOption;
    private String secondOption;
    private int rightAnswer;
    private String explanation;

    public QuestionDb() {

    }

    public QuestionDb(String question, String firstOption, String secondOption, int rightAnswer, String explanation) {
        this.question = question;
        this.firstOption = firstOption;
        this.secondOption = secondOption;
        this.rightAnswer = rightAnswer;
        this.explanation = explanation;
    }


    public void setQuestion(String question) {
        this.question = question;
    }

    public void setFirstOption(String firstOption) {
        this.firstOption = firstOption;
    }

    public void setSecondOption(String secondOption) {
        this.secondOption = secondOption;
    }

    public void setRightAnswer(int rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getQuestion() {
        return question;
    }

    public String getFirstOption() {
        return firstOption;
    }

    public String getSecondOption() {
        return secondOption;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public String getExplanation() {
        return explanation;
    }

    @Override
    public String toString() {
        return "QuestionDb{" +
                "question='" + question + '\'' +
                ", firstOption='" + firstOption + '\'' +
                ", secondOption='" + secondOption + '\'' +
                ", rightAnswer=" + rightAnswer +
                ", explanation='" + explanation + '\'' +
                '}';
    }
}
