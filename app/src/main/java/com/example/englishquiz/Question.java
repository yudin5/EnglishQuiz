package com.example.englishquiz;

public class Question {
    int questionResId;
    int firstOptionResId;
    int secondOptionResId;
    int explanationResId;
    int rightAnswer;

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
