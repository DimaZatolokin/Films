package com.dimazatolokin.films.model.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dimazatolokin.films.Utils;
import com.dimazatolokin.films.model.models.Film;

import java.util.ArrayList;
import java.util.List;

public class FilmsDao {

    private static final String TABLE_NAME = "films_table";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_RATING = "rating";
    private static final String COLUMN_RELEASE_YEAR = "releaseYear";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_IS_IN_BOOKMARK = "is_in_bookmark";
    public static final String TAG = FilmsDao.class.getSimpleName();

    private SQLiteDatabase sqLiteDatabase;

    public FilmsDao(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    static String obtainCreateInstancesQuery() {
        return "CREATE TABLE IF NOT EXISTS '" + TABLE_NAME + "' ('" + COLUMN_ID + "' INTEGER PRIMARY KEY, '"
                + COLUMN_TITLE + "' TEXT NOT NULL UNIQUE ON CONFLICT REPLACE, " + "'" + COLUMN_IMAGE
                + "' TEXT, '" + COLUMN_RATING + "' REAL, '" + COLUMN_RELEASE_YEAR + "' INTEGER, '"
                + COLUMN_GENRE + "' TEXT, '" + COLUMN_IS_IN_BOOKMARK + "' INTEGER DEFAULT 0)";
    }

    public List<Film> getAllFilms() {
        List<Film> filmList = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(TABLE_NAME, null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                final int columnIdIndex = cursor.getColumnIndex(COLUMN_ID);
                final int columnTitleIndex = cursor.getColumnIndex(COLUMN_TITLE);
                final int columnImageIndex = cursor.getColumnIndex(COLUMN_IMAGE);
                final int columnRatingIndex = cursor.getColumnIndex(COLUMN_RATING);
                final int columnReleaseYearIndex = cursor.getColumnIndex(COLUMN_RELEASE_YEAR);
                final int columnGenreIndex = cursor.getColumnIndex(COLUMN_GENRE);
                final int columnInBookmarkIndex = cursor.getColumnIndex(COLUMN_IS_IN_BOOKMARK);
                do {
                    Film film = new Film(
                            cursor,
                            columnTitleIndex,
                            columnImageIndex,
                            columnRatingIndex,
                            columnReleaseYearIndex,
                            columnGenreIndex,
                            columnInBookmarkIndex);
                    filmList.add(film);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return filmList;
    }

    public void save(Film film) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, film.getTitle());
        contentValues.put(COLUMN_IMAGE, film.getImage());
        contentValues.put(COLUMN_RATING, film.getRating());
        contentValues.put(COLUMN_RELEASE_YEAR, film.getReleaseYear());
        contentValues.put(COLUMN_GENRE, Utils.genresFromListToStr(film.getGenres()));
        contentValues.put(COLUMN_IS_IN_BOOKMARK, film.isInBookmark() ? 1 : 0);
        long id = sqLiteDatabase.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        Log.i(TAG, "save: " + id);
    }

    public void saveAll(List<Film> films) {
        for (Film film : films) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_TITLE, film.getTitle());
            contentValues.put(COLUMN_IMAGE, film.getImage());
            contentValues.put(COLUMN_RATING, film.getRating());
            contentValues.put(COLUMN_RELEASE_YEAR, film.getReleaseYear());
            contentValues.put(COLUMN_GENRE, Utils.genresFromListToStr(film.getGenres()));
            contentValues.put(COLUMN_IS_IN_BOOKMARK, film.isInBookmark() ? 1 : 0);
            long id = sqLiteDatabase.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
            Log.i(TAG, "save: " + id);
        }
    }

    public void clear() {
        sqLiteDatabase.delete(TABLE_NAME, null, null);
    }

    public Film getFilm(String title) {
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.query(TABLE_NAME, null, COLUMN_TITLE + " = ?",
                    new String[]{title}, null, null, null);
            if (cursor.moveToFirst()) {
                final int columnIdIndex = cursor.getColumnIndex(COLUMN_ID);
                final int columnTitleIndex = cursor.getColumnIndex(COLUMN_TITLE);
                final int columnImageIndex = cursor.getColumnIndex(COLUMN_IMAGE);
                final int columnRatingIndex = cursor.getColumnIndex(COLUMN_RATING);
                final int columnReleaseYearIndex = cursor.getColumnIndex(COLUMN_RELEASE_YEAR);
                final int columnGenreIndex = cursor.getColumnIndex(COLUMN_GENRE);
                final int columnInBookmarkIndex = cursor.getColumnIndex(COLUMN_IS_IN_BOOKMARK);
                Film film = new Film(
                        cursor,
                        columnTitleIndex,
                        columnImageIndex,
                        columnRatingIndex,
                        columnReleaseYearIndex,
                        columnGenreIndex,
                        columnInBookmarkIndex);
                return film;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }
}
