package com.example.englishquiz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.englishquiz.database.QuestionDb;
import com.example.englishquiz.database.QuestionDbHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    private Button firstOptionButton;
    private Button secondOptionButton;
    private TextView questionTextView;
    private TextView explanationTextView;

    private TextView questionAskedQuantityTextView;

    private Button nextButton;

    private int userCorrectAnswers = 0;
    private int questionsAsked = 0;
    private final List<QuestionDb> alreadyAnswered = new ArrayList<>();

    private QuestionDb[] questionBank;
    //    private Question[] questionBank = Questions.get10RandomQuestions();
    private int currentIndex = 0;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        boolean deleted = getApplicationContext().deleteDatabase(QuestionDbHelper.DB_NAME);
        System.out.println("deleted = " + deleted);

        QuestionDbHelper dbHelper = new QuestionDbHelper(this);
        int totalNumberQuestions = dbHelper.getTotalNumberQuestions();
        System.out.println("totalNumberQuestions = " + totalNumberQuestions);

        questionBank = dbHelper.get10RandomQuestions();

        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(10);
        progressBar.setProgress(0);

        firstOptionButton = findViewById(R.id.first_option_button);
        secondOptionButton = findViewById(R.id.second_option_button);
        questionTextView = findViewById(R.id.question_text_view);

        explanationTextView = findViewById(R.id.explanation_text_view);
        explanationTextView.setVisibility(View.INVISIBLE);

        questionAskedQuantityTextView = findViewById(R.id.question_asked_quantity);

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
//        int questionTextResId = questionBank[currentIndex].questionResId;
//        int firstOptionTextResId = questionBank[currentIndex].firstOptionResId;
//        int secondOptionTextResId = questionBank[currentIndex].secondOptionResId;
//        int explanationTestResId = questionBank[currentIndex].explanationResId;
        QuestionDb questionDb = questionBank[0];
        System.out.println("questionDb 0 = " + questionDb);

        String questionText = questionBank[currentIndex].getQuestion();
        String firstOptionText = questionBank[currentIndex].getFirstOption();
        String secondOptionText = questionBank[currentIndex].getSecondOption();
        String explanationText = questionBank[currentIndex].getExplanation();

        questionTextView.setText(questionText);
        int btnColorDefault = getResources().getColor(R.color.btn_color_default);

        firstOptionButton.setText(firstOptionText);
        firstOptionButton.setBackgroundColor(btnColorDefault);
        firstOptionButton.setEnabled(true);

        secondOptionButton.setText(secondOptionText);
        secondOptionButton.setBackgroundColor(btnColorDefault);
        secondOptionButton.setEnabled(true);

        explanationTextView.setText(explanationText);
        explanationTextView.setVisibility(View.INVISIBLE);
    }

    // Принимает какую кнопку нажал пользователь, 1 - первую, 2 - вторую
    private void checkAnswer(int userAnswer) {

        progressBar.setProgress(progressBar.getProgress() + 1);

        firstOptionButton.setEnabled(false);
        secondOptionButton.setEnabled(false);
        explanationTextView.setVisibility(View.VISIBLE);

        // Это просто, чтобы вытащить "1" или "2", то есть получить правильный ответ. Фейспалм.
//        int rightAnswer = Integer.parseInt(getString(questionBank[currentIndex].rightAnswer));
        int rightAnswer = questionBank[currentIndex].getRightAnswer();

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
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(300); // вибрировать при неправильном ответе
            int colorWrongAnswerBg = getResources().getColor(R.color.wrong_answer_bg);
            if (userAnswer == 1) {
                firstOptionButton.setBackgroundColor(colorWrongAnswerBg);
            } else {
                secondOptionButton.setBackgroundColor(colorWrongAnswerBg);
            }
        }
        questionsAsked++; // Увеличиваем счётчик вопросов, на которые дан ответ
        questionAskedQuantityTextView.setText(String.format("Дано ответов: %s/10", questionsAsked));
        alreadyAnswered.add(questionBank[currentIndex]); // Добавляем вопрос к уже отвеченным

        // Если мы уже задали 10 вопросов, то меняем название кнопки "Next" -> "Finish"
        if (questionsAsked > 9) {
            nextButton.setText(R.string.finish_button);
        }

        int toastMessageId = isUserAnswerCorrect ? R.string.toast_correct : R.string.toast_incorrect;
        Toast toast = Toast.makeText(this, toastMessageId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 70);
        toast.show();
    }

}
