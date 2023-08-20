package com.example.englishquiz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StatisticsDbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "statistics.db";
    public static final int VERSION = 1;
    public static final String TABLE_NAME = "statistics";

    // Столбцы таблицы
    public static final String ID = "id";
    public static final String CORRECT_ANSWERS = "correct_answers"; // Счётчик правильных ответов пользователя

    // Запросы
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CORRECT_ANSWERS + " INTEGER NOT NULL DEFAULT 0);";

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private Context mContext;

    public StatisticsDbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            System.out.println("Создаю таблицу статистики");
            db.execSQL(CREATE_TABLE);
        } catch (Exception e) {
            System.out.println("Ошибка во время инициализации БД (статистика), ex = " + e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public StatisticsDb getStatistics() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " LIMIT 1", null);

        StatisticsDb statistics = new StatisticsDb();

        // Если статистика вызывается впервые, то необходимо добавить первую запись
        if (cursor.getCount() == 0) {
            ContentValues values = new ContentValues();
            values.put(CORRECT_ANSWERS, 0);
            db.insert(TABLE_NAME, null, values);
            statistics.setCorrectAnswers(0);
            return  statistics;
        }

        System.out.println("cursor.getCount() = " + cursor.getCount());
        cursor.moveToFirst();
        statistics.setCorrectAnswers(cursor.getInt(1));
        cursor.close();

        return statistics;
    }

    /**
     * Если пользователь правильно ответил, то счётчик правильных ответов увеличивается на 1.
     *
     * @param isUserAnswerCorrect правильный ли ответ дал пользователь
     * @return обновлённое значение счётчика
     */
    public int updateStats(boolean isUserAnswerCorrect) {
        StatisticsDb statistics = getStatistics();

        System.out.println("Обновляю статистику = " + statistics);
        SQLiteDatabase writableDatabase = getWritableDatabase();
        int counter = statistics.getCorrectAnswers();
        if (isUserAnswerCorrect) {
            counter++; // Увеличиваем на единицу счётчик и сохраняем в БД
            writableDatabase.execSQL("UPDATE " + TABLE_NAME +
                    " SET " + CORRECT_ANSWERS + " = " + counter +
                    " WHERE " + ID + " = 1");
        }
        return counter;
    }
}
