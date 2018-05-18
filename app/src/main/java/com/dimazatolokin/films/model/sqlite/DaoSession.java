package com.dimazatolokin.films.model.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dimazatolokin.films.model.models.Film;

public class DaoSession {

    private static final String DB_NAME = "films.db";
    private static final int DB_VERSION = 1;

    private final SQLiteOpenHelper dbHelper;
    private FilmsDao filmsDao;

    public DaoSession(Context context) {
        dbHelper = new SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL(FilmsDao.obtainCreateInstancesQuery());
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };
    }

    public FilmsDao getFilmsDao() {
        if (filmsDao == null) {
            filmsDao = new FilmsDao(dbHelper.getWritableDatabase());
        }
        return filmsDao;
    }
}
