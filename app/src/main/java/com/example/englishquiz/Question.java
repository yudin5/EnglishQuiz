package com.example.englishquiz;

public class Question {
    int questionResId;
    int firstOptionResId;
    int secondOptionResId;
    int explanationResId;
    int rightAnswerResId;

    public Question(int questionResId,
                    int firstOptionResId,
                    int secondOptionResId,
                    int explanationResId,
                    int rightAnswerResId) {
        this.questionResId = questionResId;
        this.firstOptionResId = firstOptionResId;
        this.secondOptionResId = secondOptionResId;
        this.explanationResId = explanationResId;
        this.rightAnswerResId = rightAnswerResId;
    }
}
