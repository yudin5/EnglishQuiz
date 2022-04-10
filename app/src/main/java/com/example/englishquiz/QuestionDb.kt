package com.example.englishquiz

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class QuestionDb(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val question: String,
    val firstOption: String,
    val secondOption: String,
    val explanation: String,
    val rightAnswer: String
)