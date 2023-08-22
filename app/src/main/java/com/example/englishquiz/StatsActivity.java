package com.example.englishquiz;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.englishquiz.database.QuestionDbHelper;
import com.example.englishquiz.database.StatisticsDbHelper;

public class StatsActivity extends AppCompatActivity {

    private TextView statsHeader;
    private TextView questionAskedAllTime;
    private TextView correctAnswersQty;
    private TextView allTimeDescription;
    private TextView rightQuestionsDescription;

    private StatisticsDbHelper statDbHelper;
    private QuestionDbHelper questionDbHelper;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        statDbHelper = new StatisticsDbHelper(this);
        questionDbHelper = new QuestionDbHelper(this);

        statsHeader = findViewById(R.id.stats_header);
        statsHeader.setText("Ваша статистика:");

        allTimeDescription = findViewById(R.id.all_time_description);
        allTimeDescription.setText("Всего задано вопросов:");

        rightQuestionsDescription = findViewById(R.id.right_questions_description);
        rightQuestionsDescription.setText("Дано правильных ответов:");

        questionAskedAllTime = findViewById(R.id.question_asked_all_time);
        questionAskedAllTime.setText(String.valueOf(questionDbHelper.getQuestionsAskedAllTime()));

        correctAnswersQty = findViewById(R.id.correct_answers_qty_text_view);
        correctAnswersQty.setText(String.valueOf(statDbHelper.getStatistics().getCorrectAnswers()));

        Button btnToMainMenu = findViewById(R.id.btn_to_main_menu);
        btnToMainMenu.setOnClickListener(v -> finish());
    }
}
