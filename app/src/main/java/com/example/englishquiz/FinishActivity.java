package com.example.englishquiz;

import android.content.Context;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        int correctAnswersQuantity = getIntent().getIntExtra(USER_CORRECT_ANSWERS_QUANTITY, 0);

        TextView finishText = findViewById(R.id.text_finish);
        finishText.setText(String.format("Вы правильно ответили \n на %s/10 вопросов. \nНеплохо.", correctAnswersQuantity));

        Button btnToMainMenu = findViewById(R.id.btn_to_main_menu2);
        btnToMainMenu.setOnClickListener(v -> {
            Intent intent = new Intent(FinishActivity.this, StartActivity.class);
            finish();
            startActivity(intent);
        });
    }
}