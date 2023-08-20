package com.example.englishquiz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FinishActivity extends AppCompatActivity {

    public static String USER_CORRECT_ANSWERS_QUANTITY = "correctAnswersQuantity";

    public static Intent newIntent(Context packageContext, int correctAnswersQuantity) {
        return new Intent(packageContext, FinishActivity.class)
                .putExtra(USER_CORRECT_ANSWERS_QUANTITY, correctAnswersQuantity);
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        int correctAnswers = getIntent().getIntExtra(USER_CORRECT_ANSWERS_QUANTITY, 0);
        String howBadResult;
        if (0 <= correctAnswers && correctAnswers <= 3) {
            howBadResult = "Что ж, давайте признаем - оценка так себе. Нужно больше практиковаться и результат станет лучше. Practice makes perfect!";
        } else if (4 <= correctAnswers && correctAnswers <= 6) {
            howBadResult = "Неплохо. Но могло быть и лучше. Нужно просто регулярно повторять пройденное.";
        } else if (7 <= correctAnswers && correctAnswers <= 9) {
            howBadResult = "Результат очень хороший. Но ещё есть куда стремиться. Регулярные тренировки - наше всё.";
        } else {
            howBadResult = "Отлично, так держать! Сможете сыграть 10 игр подряд с таким результатом?";
        }

        TextView finishText = findViewById(R.id.text_finish);

        finishText.setText(String.format("Вы правильно ответили \n на %s/10 вопросов. \n%s.", correctAnswers, howBadResult));

        Button btnToMainMenu = findViewById(R.id.btn_to_main_menu2);
        btnToMainMenu.setOnClickListener(v -> {
            Intent intent = new Intent(FinishActivity.this, StartActivity.class);
            finish();
            startActivity(intent);
        });
    }
}