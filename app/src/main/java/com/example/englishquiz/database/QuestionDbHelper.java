package com.example.englishquiz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionDbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "questions.db";
    public static final int VERSION = 1;
    public static final String TABLE_NAME = "questions";

    public static final String ID = "id";
    public static final String QUESTION = "question";
    public static final String FIRST_OPTION = "first_option";
    public static final String SECOND_OPTION = "second_option";
    public static final String RIGHT_ANSWER = "answer";
    public static final String EXPLANATION = "explanation";
    public static final String QUESTION_ANSWERED_COUNTER = "question_answered_counter";

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
            System.out.println("=================== Создаю новую БД");
            db.execSQL(CREATE_TABLE);
            fillQuestions(db);
        } catch (Exception e) {
            System.out.println("Ошибка во время инициализации БД, ex = " + e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("===================== Дропаю таблицу!!!");
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
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        int totalNumbers = cursor.getCount();
        cursor.close();
        return totalNumbers;
    }

    // Увеличить счётчик количества ответов на этот вопрос на 1
    public void updateQuestionCounter(QuestionDb question) {
        System.out.println("Обновляю счётчик для вопроса " + question.getId());
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
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
        List<Integer> first10 = allQuestions.subList(0, 10);
        QuestionDb[] result = new QuestionDb[10];
        for (int i = 0; i < first10.size(); i++) {
            result[i] = getQuestionById(first10.get(i));
        }

        return result;
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

                long insert = db.insert(TABLE_NAME, null, values);
                System.out.println("insert = " + insert);

                line = reader.readLine(); // если line == null, значит это конец файла
            } while (line != null);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
}
