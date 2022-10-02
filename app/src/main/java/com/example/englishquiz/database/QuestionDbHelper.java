package com.example.englishquiz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.englishquiz.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionDbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "questions.db";
    public static final int VERSION = 1;
    public static final String TABLE_NAME = "questions";

    // Столбцы таблицы
    public static final String ID = "id";
    public static final String QUESTION = "question";
    public static final String FIRST_OPTION = "first_option";
    public static final String SECOND_OPTION = "second_option";
    public static final String RIGHT_ANSWER = "answer";
    public static final String EXPLANATION = "explanation";
    public static final String QUESTION_ANSWERED_COUNTER = "question_answered_counter";

    // Запросы
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            QUESTION + " TEXT NOT NULL, " +
            FIRST_OPTION + " TEXT NOT NULL, " +
            SECOND_OPTION + " TEXT NOT NULL, " +
            RIGHT_ANSWER + " INTEGER NOT NULL, " +
            EXPLANATION + " TEXT NOT NULL, " +
            QUESTION_ANSWERED_COUNTER + " INTEGER NOT NULL DEFAULT 0);";

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private Context mContext;

    public QuestionDbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
            fillQuestions(db);
        } catch (Exception e) {
            System.out.println("Ошибка во время инициализации БД, ex = " + e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    // Получить вопрос по ID
    public QuestionDb getQuestionById(int questionId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ID = " + questionId, null);

        cursor.moveToFirst();

        QuestionDb result = new QuestionDb();
        result.setId(cursor.getInt(0));
        result.setQuestion(cursor.getString(1));
        result.setFirstOption(cursor.getString(2));
        result.setSecondOption(cursor.getString(3));
        result.setRightAnswer(cursor.getInt(4));
        result.setExplanation(cursor.getString(5));
        result.setQuestionAnsweredCounter(cursor.getInt(6));

        cursor.close();
        return result;
    }

    // Получить количество вопросов, хранящееся в БД
    public int getTotalNumberQuestions() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        int totalNumbers = cursor.getCount();
        cursor.close();
        return totalNumbers;
    }

    // Увеличить счётчик количества ответов на этот вопрос на 1
    public void updateQuestionCounter(QuestionDb question) {
        System.out.println("Обновляю счётчик для вопроса " + question.getId());
        SQLiteDatabase writableDatabase = getWritableDatabase();
        int id = question.getId();
        int counter = question.getQuestionAnsweredCounter();
        counter++; // Увеличиваем на единицу счётчик и сохраняем в БД
        writableDatabase.execSQL("UPDATE " + TABLE_NAME +
                " SET " + QUESTION_ANSWERED_COUNTER + " = " + counter +
                " WHERE " + ID + " = " + id);
    }

//    public QuestionDb[] getAllQuestions() {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursorQuestions = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
//
//        List<QuestionDb> questions = new ArrayList<>();
//
//        if (cursorQuestions.moveToFirst()) {
//            do {
//                questions.add(new QuestionDb(cursorQuestions.getString(1),
//                        cursorQuestions.getString(2),
//                        cursorQuestions.getString(3),
//                        cursorQuestions.getInt(4),
//                        cursorQuestions.getString(5)));
//            } while (cursorQuestions.moveToNext());
//        }
//
//        cursorQuestions.close();
//        return questions.toArray(new QuestionDb[0]);
//    }

    // Получить 10 случайных вопросов из БД
    public QuestionDb[] get10RandomQuestions() {
        // Получаем количество всех вопросов в БД
        int totalQuestions = getTotalNumberQuestions();

        // Заполняем числами от 1 до totalQuestions
        List<Integer> allQuestions = new ArrayList<>();
        for (int i = 1; i <= totalQuestions; i++) {
            allQuestions.add(i);
        }
        // Перемешиваем их
        Collections.shuffle(allQuestions);

        // Берём первые 10. Таким образом получаем случайные неповторящиеся числа из диапазона
        // от 1 до "количество всех вопросов в БД"
        List<Integer> first10 = allQuestions.subList(0, MainActivity.QUESTIONS_TO_ASK_NUMBER);
        QuestionDb[] result = new QuestionDb[MainActivity.QUESTIONS_TO_ASK_NUMBER];
        for (int i = 0; i < first10.size(); i++) {
            result[i] = getQuestionById(first10.get(i));
        }

        return result;
    }

    // Получить вообще все вопросы из БД. Нагружает оперативу!
    public List<QuestionDb> get10PseudoRandomQuestions() {
        SQLiteDatabase db = getReadableDatabase();
        List<QuestionDb> allQuestions = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        cursor.moveToFirst();
        do {
            QuestionDb question = new QuestionDb();
            question.setId(cursor.getInt(0));
            question.setQuestion(cursor.getString(1));
            question.setFirstOption(cursor.getString(2));
            question.setSecondOption(cursor.getString(3));
            question.setRightAnswer(cursor.getInt(4));
            question.setExplanation(cursor.getString(5));
            question.setQuestionAnsweredCounter(cursor.getInt(6));

            allQuestions.add(question);
        } while (cursor.moveToNext());
        cursor.close();

        // Сколько раз минимально задавались вопросы. То есть ищем минимум среди всех вопросов.
        int minAsked = allQuestions.stream()
                .map(QuestionDb::getQuestionAnsweredCounter)
                .min(Integer::compareTo)
                .orElse(0);

        // Теперь последовательно, начиная с минимума, набираем 10 вопросов.
        // Идея такая, что вопросы, которые задавались реже всего, обязательно должны быть заданы
        List<QuestionDb> result = new ArrayList<>();
        do {
            List<QuestionDb> haveToAsk = getQuestionsByAskedNumber(minAsked,
                    MainActivity.QUESTIONS_TO_ASK_NUMBER - result.size(),
                    allQuestions);
            result.addAll(haveToAsk);
            minAsked++;
//            result.add(new QuestionDb());
        } while (result.size() < MainActivity.QUESTIONS_TO_ASK_NUMBER);

        return result;
    }

    // askedNumber - сколько раз был задан вопрос, questionQty - сколько нам нужно таких вопросов, questionList - список вопросов
    private List<QuestionDb> getQuestionsByAskedNumber(int askedNumber, int questionQty, List<QuestionDb> questionList) {
        List<QuestionDb> questions = questionList.stream()
                .filter(q -> q.getQuestionAnsweredCounter() == askedNumber)
                .collect(Collectors.toList());
        // Если таких вопросов найдено больше или равно запрошенному, то перемешиваем их и отдаём случайные.
        // Отдаём именно то количество, которое было запрошено
        if (questions.size() >= questionQty) {
            Collections.shuffle(questions);
            return questions.subList(0, questionQty);
        } else {
            // Если было найдено меньше запрошенного, то отдаём все как есть
            return questions;
        }
    }

    // Заполняет БД вопросами из текстового файла raw_questions.txt
    private void fillQuestions(SQLiteDatabase db) {

        try {
            InputStream file = mContext.getAssets().open("raw_questions.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(file));
            String line;

            do {
                ContentValues values = new ContentValues();

                line = reader.readLine();
                values.put(QUESTION, line);

                line = reader.readLine();
                values.put(FIRST_OPTION, line);

                line = reader.readLine();
                values.put(SECOND_OPTION, line);

                line = reader.readLine();
                values.put(RIGHT_ANSWER, line);

                line = reader.readLine();
                values.put(EXPLANATION, line);

//                long insert = db.insert(TABLE_NAME, null, values);
                db.insert(TABLE_NAME, null, values);

                line = reader.readLine(); // если line == null, значит это конец файла
            } while (line != null);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
}
