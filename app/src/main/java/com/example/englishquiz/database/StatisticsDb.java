package com.example.englishquiz.database;

public class StatisticsDb {


    private int id;
    private int correctAnswers;

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

    @Override
    public String toString() {
        return "StatisticsDb{" +
                "id=" + id +
                ", correctAnswers=" + correctAnswers +
                '}';
    }

}
