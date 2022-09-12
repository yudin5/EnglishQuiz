package com.example.englishquiz;

public class Question {
    public int questionResId;
    public int firstOptionResId;
    public int secondOptionResId;
    public int explanationResId;
    public int rightAnswer;

    public Question(int questionResId,
                    int firstOptionResId,
                    int secondOptionResId,
                    int explanationResId,
                    int rightAnswer) {
        this.questionResId = questionResId;
        this.firstOptionResId = firstOptionResId;
        this.secondOptionResId = secondOptionResId;
        this.explanationResId = explanationResId;
        this.rightAnswer = rightAnswer;
    }
}
