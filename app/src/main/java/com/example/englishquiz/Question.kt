package com.example.englishquiz

import androidx.annotation.StringRes

data class Question(
    @StringRes val questionResId: Int,
    @StringRes val firstOptionResId: Int,
    @StringRes val secondOptionResId: Int,
    @StringRes val explanationResId: Int,
    val rightAnswerResId: Int
)