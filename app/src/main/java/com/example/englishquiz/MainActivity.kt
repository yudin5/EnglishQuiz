package com.example.englishquiz

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.englishquiz.R.string.*

class MainActivity : AppCompatActivity() {
    private lateinit var firstOptionButton: Button
    private lateinit var secondOptionButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView
    private lateinit var explanationTextView: TextView

    private val questionBank = listOf(
        Question(
            q_1,
            q_1_op_1,
            q_1_op_2,
            q_1_explanation,
            q_1_op_1,
        ),
        Question(
            q_2,
            q_2_op_1,
            q_2_op_2,
            q_2_explanation,
            q_2_op_1,
        )
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firstOptionButton = findViewById(R.id.first_option_button)
        secondOptionButton = findViewById(R.id.second_option_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)

        explanationTextView = findViewById(R.id.explanation_text_view)
        explanationTextView.isVisible = false

        firstOptionButton.setOnClickListener { view: View ->
            checkAnswer(questionBank[currentIndex].firstOptionResId)
        }

        secondOptionButton.setOnClickListener {
            checkAnswer(questionBank[currentIndex].secondOptionResId)
        }

        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        updateQuestion()
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].questionResId
        val firstOptionTextResId = questionBank[currentIndex].firstOptionResId
        val secondOptionTextResId = questionBank[currentIndex].secondOptionResId
        val explanationTestResId = questionBank[currentIndex].explanationResId

        questionTextView.setText(questionTextResId)

        firstOptionButton.setText(firstOptionTextResId)
        firstOptionButton.setBackgroundColor(Color.BLUE)
        firstOptionButton.isEnabled = true

        secondOptionButton.setText(secondOptionTextResId)
        secondOptionButton.setBackgroundColor(Color.BLUE)
        secondOptionButton.isEnabled = true

        explanationTextView.setText(explanationTestResId)
        explanationTextView.isVisible = false
    }

    private fun checkAnswer(userAnswer: Int) {
//        val correctAnswer = questionBank[currentIndex].rightAnswerResId

//        val messageId = when (userAnswer) {
//            correctAnswer -> R.string.correct_toast
//            else -> R.string.incorrect_toast
//        }
        val firstOptionTextResId = questionBank[currentIndex].firstOptionResId

        firstOptionButton.isEnabled = false
        secondOptionButton.isEnabled = false
        explanationTextView.isVisible = true

        if (userAnswer == questionBank[currentIndex].rightAnswerResId) {
            if (userAnswer == firstOptionTextResId) {
                firstOptionButton.setBackgroundColor(Color.GREEN)
                secondOptionButton.setTextColor(Color.WHITE)
            } else {
                secondOptionButton.setBackgroundColor(Color.GREEN)
                firstOptionButton.setTextColor(Color.WHITE)
            }
        } else {
            if (userAnswer == firstOptionTextResId) {
                firstOptionButton.setBackgroundColor(Color.RED)
                secondOptionButton.setTextColor(Color.WHITE)
            } else {
                secondOptionButton.setBackgroundColor(Color.RED)
                firstOptionButton.setTextColor(Color.WHITE)
            }
        }

//        val explResId = questionBank[currentIndex].explanationResId
//        Toast.makeText(this, explResId, Toast.LENGTH_LONG)
//            .show()
    }
}