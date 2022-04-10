package com.example.englishquiz.database

import androidx.room.Dao
import androidx.room.Query
import com.example.englishquiz.QuestionDb
import java.util.*

@Dao
interface QuestionDao {
    @Query("SELECT * FROM questiondb")
    fun getQuestions(): List<QuestionDb>

    @Query("SELECT * FROM questiondb WHERE id=(:id)")
    fun getQuestion(id: UUID): QuestionDb?
}