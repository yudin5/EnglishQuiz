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

    private TextView questionAskedQuantity;
    private TextView rightAnswersQuantity;

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

        questionAskedQuantity = findViewById(R.id.question_asked_quantity);
        rightAnswersQuantity = findViewById(R.id.right_answers_quantity);

        exitButton = findViewById(R.id.btn_exit);
        exitButton.setBackgroundColor(Color.BLUE);
        exitButton.setOnClickListener(v -> finish());

        firstOptionButton.setOnClickListener(
                view -> checkAnswer(1)
        );

        secondOptionButton.setOnClickListener(
                view -> checkAnswer(2)
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

    // Принимает какую кнопку нажал пользователь, 1 - первую, 2 - вторую
    private void checkAnswer(int userAnswer) {

        firstOptionButton.setEnabled(false);
        secondOptionButton.setEnabled(false);
        explanationTextView.setVisibility(View.VISIBLE);

        // Это просто, чтобы вытащить "1" или "2", то есть получить правильный ответ. Фейспалм.
        int rightAnswer = Integer.parseInt(getString(questionBank[currentIndex].rightAnswer));

        boolean isUserAnswerCorrect = (userAnswer == rightAnswer);

        if (isUserAnswerCorrect) {
            userCorrectAnswers++; // увеличиваем счётчик правильных ответов
            if (userAnswer == 1) {
                firstOptionButton.setBackgroundColor(Color.GREEN);
                secondOptionButton.setTextColor(Color.WHITE);
            } else {
                secondOptionButton.setBackgroundColor(Color.GREEN);
                firstOptionButton.setTextColor(Color.WHITE);
            }
        } else {
            if (userAnswer == 1) {
                firstOptionButton.setBackgroundColor(Color.RED);
                secondOptionButton.setTextColor(Color.WHITE);
            } else {
                secondOptionButton.setBackgroundColor(Color.RED);
                firstOptionButton.setTextColor(Color.WHITE);
            }
        }
        questionsAsked++; // увеличиваем счётчик заданных вопросов
        questionAskedQuantity.setText(String.format("Задано вопросов: %s", questionsAsked));
        rightAnswersQuantity.setText(String.format("Правильных ответов: %s", userCorrectAnswers));

        int toastMessageId = isUserAnswerCorrect ? R.string.toast_correct : R.string.toast_incorrect;
        Toast.makeText(this, toastMessageId, Toast.LENGTH_SHORT).show();
    }

}
