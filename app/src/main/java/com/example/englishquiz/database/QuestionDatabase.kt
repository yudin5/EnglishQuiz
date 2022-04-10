package com.example.englishquiz.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.englishquiz.QuestionDb

@Database(entities = [QuestionDb::class], version = 1)
@TypeConverters(QuestionTypeConverters::class)
abstract class QuestionDatabase : RoomDatabase() {

    abstract fun questionDao(): QuestionDao

}