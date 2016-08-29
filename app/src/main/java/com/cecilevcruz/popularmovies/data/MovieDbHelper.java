package com.cecilevcruz.popularmovies.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cecilevcruz.popularmovies.data.MovieContract.MovieEntry;
import com.cecilevcruz.popularmovies.data.MovieContract.MovieReviewsEntry;
import com.cecilevcruz.popularmovies.data.MovieContract.MovieTrailerEntry;
/**
 * Created by cecicruz on 8/10/16.
 *
 * Manages a local database for movie data.
 */
public class MovieDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version, manually
    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "movie.db";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" +
                MovieEntry._ID + " INTEGER PRIMARY KEY," +

                // the ID of the location entry associated with this movie data
                MovieEntry._ID + " TEXT UNIQUE NOT NULL, " +
                MovieEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_RELEASE_DATE + "TEXT NOT NULL," +
                MovieEntry.COLUMN_VOTE_AVERAGE + "TEXT NOT NULL," +
                MovieEntry.COLUMN_VOTE_COUNT + "TEXT NOT NULL," +
                MovieEntry.COLUMN_DESCRIPTION + "TEXT NOT NULL," +
                MovieEntry.COLUMN_IMAGE_URL + "TEXT NOT NULL," +
                MovieEntry.COLUMN_POPULARITY + "TEXT NOT NULL," +
                MovieEntry.COLUMN_RUNTIME + "TEXT NOT NULL," +
                MovieEntry.COLUMN_FAVORITE + "TEXT NOT NULL," +
                " );";

        final String SQL_CREATE_MOVIEREVIEWS_TABLE = "CREATE TABLE " + MovieReviewsEntry.TABLE_NAME + " (" +

                MovieReviewsEntry.COLUMN_REVIEW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MovieReviewsEntry.COLUMN_REVIEW_KEY + " INTEGER NOT NULL, " +
                MovieReviewsEntry.COLUMN_REVIEW_ID + "TEXT NOT NULL," +
                MovieReviewsEntry.COLUMN_REVIEW_AUTHOR + "TEXT NOT NULL," +
                MovieReviewsEntry.COLUMN_REVIEW_CONTENT + "TEXT NOT NULL," +
                // Set up the movie_key column as a foreign key to movie table.
                " FOREIGN KEY (" + MovieReviewsEntry.COLUMN_REVIEW_KEY + ") REFERENCES " +
                MovieEntry.TABLE_NAME + " (" + MovieEntry._ID + "), " +

                // To assure the application have just one movie review entry per
                // movie, it's created a UNIQUE constraint with REPLACE strategy
                " UNIQUE (" + MovieReviewsEntry.COLUMN_REVIEW_KEY + ", " +
                MovieReviewsEntry.COLUMN_REVIEW_KEY + ") ON CONFLICT REPLACE);";


        final String SQL_CREATE_MOVIETRAILERS_TABLE = "CREATE TABLE " + MovieTrailerEntry.TABLE_NAME + " (" +
             MovieTrailerEntry.COLUMN_TRAILER_ID + " INTEGER PRIMARY KEY," +
             MovieTrailerEntry.COLUMN_TRAILER_KEY + "TEXT NOT NULL," +

             MovieTrailerEntry.COLUMN_TRAILER_TITLE + "TEXT NOT NULL," +
             MovieTrailerEntry.COLUMN_YOUTUBE_KEY + "TEXT NOT NULL," +
             MovieTrailerEntry.COLUMN_TRAILER_ID + "TEXT NOT NULL," +
             // Set up the movie_key column as a foreign key to movie table.
             " FOREIGN KEY (" + MovieReviewsEntry.COLUMN_REVIEW_KEY + ") REFERENCES " +
             MovieEntry.TABLE_NAME + " (" + MovieEntry._ID + "), " +

             // To assure the application have just one movie review entry per
             // movie, it's created a UNIQUE constraint with REPLACE strategy
             " UNIQUE (" + MovieTrailerEntry.COLUMN_TRAILER_KEY + ", " +
                MovieTrailerEntry.COLUMN_TRAILER_KEY + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIEREVIEWS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIETRAILERS_TABLE);

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieReviewsEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieTrailerEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}