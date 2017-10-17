package com.example.gregorio.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.gregorio.popularmovies.data.FilmContract.favouriteFilmEntry;

/**
 * Created by Gregorio on 05/10/2017.
 */

public class FilmDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = FilmDbHelper.class.getSimpleName();


    public static final String DATABASE_NAME = "favouriteFilms.db";

    private static final int DATABASE_VERSION = 1;


    //Database Constructor
    public FilmDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

            /*
         * This String will contain a simple SQL statement that will create a table that will
         * cache our weather data.
         */
        final String SQL_CREATE_FAVOURITE_FILMS_TABLE =
                "CREATE TABLE " + favouriteFilmEntry.TABLE_NAME + " (" +
                        favouriteFilmEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        favouriteFilmEntry.COLUMN_FILM_ID + " INTEGER NOT NULL, " +
                        favouriteFilmEntry.COLUMN_TITLE + " STRING NOT NULL, " +
                        favouriteFilmEntry.COLUMN_OVERVIEW + " STRING NOT NULL, " +
                        favouriteFilmEntry.COLUMN_RELEASE_DATE + " STRING NOT NULL, " +
                        favouriteFilmEntry.COLUMN_POSTER_PATH + " STRING NOT NULL, " +
                        favouriteFilmEntry.COLUMN_VOTE_AVERAGE + " STRING NOT NULL);";

        db.execSQL(SQL_CREATE_FAVOURITE_FILMS_TABLE);

        Log.i(LOG_TAG, "Database name is :" + SQL_CREATE_FAVOURITE_FILMS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
