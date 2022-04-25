package com.example.englishquiz;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button firstOptionButton;
    private Button secondOptionButton;
    private Button nextButton;
    private Button exitButton;
    private TextView questionTextView;
    private TextView explanationTextView;
    private TextView statisticsTextView;

    private int userCorrectAnswers = 0;
    private int questionsAsked = 0;

    private final Question[] questionBank = Questions.getQuestionBank();
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstOptionButton = findViewById(R.id.first_option_button);
        secondOptionButton = findViewById(R.id.second_option_button);
        nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.question_text_view);

        explanationTextView = findViewById(R.id.explanation_text_view);
        explanationTextView.setVisibility(View.INVISIBLE);

        statisticsTextView = findViewById(R.id.statistics_text_view);
        statisticsTextView.setVisibility(View.INVISIBLE);

        exitButton = findViewById(R.id.btn_exit);
        exitButton.setOnClickListener(v -> finish());

        firstOptionButton.setOnClickListener(
                view -> checkAnswer(questionBank[currentIndex].firstOptionResId)
        );

        secondOptionButton.setOnClickListener(
                view -> checkAnswer(questionBank[currentIndex].secondOptionResId)
        );

        nextButton.setOnClickListener(view -> {
                    currentIndex = (currentIndex + 1) % questionBank.length;
                    updateQuestion();
                }
        );

        updateQuestion();
    }

    private void updateQuestion() {
        int questionTextResId = questionBank[currentIndex].questionResId;
        int firstOptionTextResId = questionBank[currentIndex].firstOptionResId;
        int secondOptionTextResId = questionBank[currentIndex].secondOptionResId;
        int explanationTestResId = questionBank[currentIndex].explanationResId;

        questionTextView.setText(questionTextResId);

        firstOptionButton.setText(firstOptionTextResId);
        firstOptionButton.setBackgroundColor(Color.BLUE);
        firstOptionButton.setEnabled(true);

        secondOptionButton.setText(secondOptionTextResId);
        secondOptionButton.setBackgroundColor(Color.BLUE);
        secondOptionButton.setEnabled(true);

        explanationTextView.setText(explanationTestResId);
        explanationTextView.setVisibility(View.INVISIBLE);
    }

    private void checkAnswer(int userAnswer) {

        int firstOptionTextResId = questionBank[currentIndex].firstOptionResId;

        firstOptionButton.setEnabled(false);
        secondOptionButton.setEnabled(false);
        explanationTextView.setVisibility(View.VISIBLE);
        statisticsTextView.setVisibility(View.VISIBLE);

        boolean correctAnswer = userAnswer == questionBank[currentIndex].rightAnswerResId;
        if (correctAnswer) {
            userCorrectAnswers++; // увеличиваем счётчик правильных ответов
            if (userAnswer == firstOptionTextResId) {
                firstOptionButton.setBackgroundColor(Color.GREEN);
                secondOptionButton.setTextColor(Color.WHITE);
            } else {
                secondOptionButton.setBackgroundColor(Color.GREEN);
                firstOptionButton.setTextColor(Color.WHITE);
            }
        } else {
            if (userAnswer == firstOptionTextResId) {
                firstOptionButton.setBackgroundColor(Color.RED);
                secondOptionButton.setTextColor(Color.WHITE);
            } else {
                secondOptionButton.setBackgroundColor(Color.RED);
                firstOptionButton.setTextColor(Color.WHITE);
            }
        }
        questionsAsked++; // увеличиваем счётчик заданных вопросов
        statisticsTextView.setText(String.format("Количество правильных ответов: %s из %s", userCorrectAnswers, questionsAsked));

        int toastMessageId = correctAnswer ? R.string.toast_correct : R.string.toast_incorrect;
        Toast.makeText(this, toastMessageId, Toast.LENGTH_SHORT).show();
    }

}
