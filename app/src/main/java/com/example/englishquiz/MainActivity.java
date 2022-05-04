package com.example.englishquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button firstOptionButton;
    private Button secondOptionButton;
    private TextView questionTextView;
    private TextView explanationTextView;

    private TextView questionAskedQuantityTextView;
    private TextView rightAnswersQuantityTextView;

    private Button nextButton;

    private int userCorrectAnswers = 0;
    private int questionsAsked = 0;
    private final List<Question> alreadyAnswered = new ArrayList<>();

    private final Question[] questionBank = Questions.getQuestionBank();
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstOptionButton = findViewById(R.id.first_option_button);
        secondOptionButton = findViewById(R.id.second_option_button);
        questionTextView = findViewById(R.id.question_text_view);

        explanationTextView = findViewById(R.id.explanation_text_view);
        explanationTextView.setVisibility(View.INVISIBLE);

        questionAskedQuantityTextView = findViewById(R.id.question_asked_quantity);
        rightAnswersQuantityTextView = findViewById(R.id.right_answers_quantity);

        firstOptionButton.setOnClickListener(
                view -> checkAnswer(1)
        );

        secondOptionButton.setOnClickListener(
                view -> checkAnswer(2)
        );

        // Кнопка "Next"
        nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(view -> {
                    if (questionsAsked >= 10) {
                        Intent intent = FinishActivity.newIntent(MainActivity.this, userCorrectAnswers);
                        startActivity(intent);
                        finish();
                    } else {
                        currentIndex = (currentIndex + 1) % questionBank.length;
                        // Избегаем вопросов, на которые мы уже отвечали
                        while (alreadyAnswered.contains(questionBank[currentIndex])) {
                            currentIndex = (currentIndex + 1) % questionBank.length;
                        }
                        updateQuestion();
                    }
                }
        );

        // Кнопка "Exit" -> выход в меню
        Button exitButton = findViewById(R.id.btn_exit);
        exitButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, StartActivity.class);
            startActivity(intent);
            finish();
        });

        updateQuestion();
    }

    private void updateQuestion() {
        int questionTextResId = questionBank[currentIndex].questionResId;
        int firstOptionTextResId = questionBank[currentIndex].firstOptionResId;
        int secondOptionTextResId = questionBank[currentIndex].secondOptionResId;
        int explanationTestResId = questionBank[currentIndex].explanationResId;

        questionTextView.setText(questionTextResId);
        int btnColorDefault = getResources().getColor(R.color.btn_color_default);

        firstOptionButton.setText(firstOptionTextResId);
        firstOptionButton.setBackgroundColor(btnColorDefault);
        firstOptionButton.setEnabled(true);

        secondOptionButton.setText(secondOptionTextResId);
        secondOptionButton.setBackgroundColor(btnColorDefault);
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
            int colorRightAnswerBg = getResources().getColor(R.color.right_answer_bg);
            userCorrectAnswers++; // увеличиваем счётчик правильных ответов
            if (userAnswer == 1) {
                firstOptionButton.setBackgroundColor(colorRightAnswerBg);
            } else {
                secondOptionButton.setBackgroundColor(colorRightAnswerBg);
            }
        } else {
            int colorWrongAnswerBg = getResources().getColor(R.color.wrong_answer_bg);
            if (userAnswer == 1) {
                firstOptionButton.setBackgroundColor(colorWrongAnswerBg);
            } else {
                secondOptionButton.setBackgroundColor(colorWrongAnswerBg);
            }
        }
        questionsAsked++; // Увеличиваем счётчик вопросов, на которые дан ответ
        questionAskedQuantityTextView.setText(String.format("Дано ответов: %s", questionsAsked));
        rightAnswersQuantityTextView.setText(String.format("Правильных ответов: %s", userCorrectAnswers));
        alreadyAnswered.add(questionBank[currentIndex]); // Добавляем вопрос к уже отвеченным

        // Если мы уже задали 10 вопросов, то меняем название кнопки "Next" -> "Finish"
        if (questionsAsked > 9) {
            nextButton.setText(R.string.finish_button);
        }

        int toastMessageId = isUserAnswerCorrect ? R.string.toast_correct : R.string.toast_incorrect;
        Toast.makeText(this, toastMessageId, Toast.LENGTH_SHORT).show();
    }

}
