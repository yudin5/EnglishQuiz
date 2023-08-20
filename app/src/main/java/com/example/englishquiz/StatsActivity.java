package com.example.englishquiz;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.englishquiz.database.StatisticsDbHelper;

public class StatsActivity extends AppCompatActivity {

    private TextView correctAnswersQty;
    private StatisticsDbHelper statDbHelper;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        statDbHelper = new StatisticsDbHelper(this);

        correctAnswersQty = findViewById(R.id.correct_answers_qty_text_view);
        correctAnswersQty.setText(String.valueOf(statDbHelper.getStatistics().getCorrectAnswers()));

        Button btnToMainMenu = findViewById(R.id.btn_to_main_menu);
        btnToMainMenu.setOnClickListener(v -> finish());
    }
}
