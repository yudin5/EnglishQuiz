package com.example.englishquiz.database;

public class StatisticsDb {

    private int id;
    private int totalAnswers; // Всего задано вопросов пользователю
    private int correctAnswers; // Дано правильных ответов

    public StatisticsDb() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getTotalAnswers() {
        return totalAnswers;
    }

    public void setTotalAnswers(int totalAnswers) {
        this.totalAnswers = totalAnswers;
    }

    @Override
    public String toString() {
        return "StatisticsDb{" +
                "id=" + id +
                ", totalAnswers=" + totalAnswers +
                ", correctAnswers=" + correctAnswers +
                '}';
    }

}
