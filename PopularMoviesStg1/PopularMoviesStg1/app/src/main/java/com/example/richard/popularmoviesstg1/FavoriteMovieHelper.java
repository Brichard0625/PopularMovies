package com.example.richard.popularmoviesstg1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.List;

public class FavoriteMovieHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favorites.db";

    private static final int DATABASE_VERSION = 2;


    SQLiteOpenHelper dbhandler;
    SQLiteDatabase db;


    public FavoriteMovieHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() {
        db = dbhandler.getWritableDatabase();
    }

    public void close() {
        dbhandler.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_FAVORITES_TABLE = "CREATE TABLE " + FavoriteMovieContract.FavoriteMovie.TABLE_NAME + " (" +
                FavoriteMovieContract.FavoriteMovie._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavoriteMovieContract.FavoriteMovie.COLUMN_MOVIEID + " INTEGER NOT NULL, " +
                FavoriteMovieContract.FavoriteMovie.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                FavoriteMovieContract.FavoriteMovie.COLUMN_TITLE + " TEXT NOT NULL, " +
                FavoriteMovieContract.FavoriteMovie.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                FavoriteMovieContract.FavoriteMovie.COLUMN_USERRATING + " REAL NOT NULL, " +
                FavoriteMovieContract.FavoriteMovie.COLUMN_MOVIE_DESCRIPTION + " TEXT NOT NULL " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavoriteMovieContract.FavoriteMovie.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public void addFavorite(Movie movie){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FavoriteMovieContract.FavoriteMovie.COLUMN_MOVIEID, movie.getId());
        values.put(FavoriteMovieContract.FavoriteMovie.COLUMN_TITLE, movie.getOriginalTitle());
        values.put(FavoriteMovieContract.FavoriteMovie.COLUMN_USERRATING, movie.getVoteAverage());
        values.put(FavoriteMovieContract.FavoriteMovie.COLUMN_POSTER_PATH, movie.getPosterPath());
        values.put(FavoriteMovieContract.FavoriteMovie.COLUMN_MOVIE_DESCRIPTION, movie.getOverview());
       values.put(FavoriteMovieContract.FavoriteMovie.COLUMN_RELEASE_DATE, movie.getReleaseDate());



        db.insert(FavoriteMovieContract.FavoriteMovie.TABLE_NAME, null, values);
        db.close();
    }

    public void deleteFavorite(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FavoriteMovieContract.FavoriteMovie.TABLE_NAME, FavoriteMovieContract.FavoriteMovie.COLUMN_MOVIEID+ "=" + id, null);
    }

    public List<Movie> getAllFavorite(){
        String[] columns = {
                FavoriteMovieContract.FavoriteMovie._ID,
                FavoriteMovieContract.FavoriteMovie.COLUMN_MOVIEID,
                FavoriteMovieContract.FavoriteMovie.COLUMN_TITLE,
                FavoriteMovieContract.FavoriteMovie.COLUMN_USERRATING,
                FavoriteMovieContract.FavoriteMovie.COLUMN_POSTER_PATH,
                FavoriteMovieContract.FavoriteMovie.COLUMN_MOVIE_DESCRIPTION,
                FavoriteMovieContract.FavoriteMovie.COLUMN_RELEASE_DATE,


        };
        String sortOrder =
                FavoriteMovieContract.FavoriteMovie._ID + " ASC";
        List<Movie> favoriteList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(FavoriteMovieContract.FavoriteMovie.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()){
            do {
                Movie movie = new Movie();
                movie.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FavoriteMovieContract.FavoriteMovie.COLUMN_MOVIEID))));
                movie.setOriginalTitle(cursor.getString(cursor.getColumnIndex(FavoriteMovieContract.FavoriteMovie.COLUMN_TITLE)));
                movie.setVoteAverage(Double.parseDouble(cursor.getString(cursor.getColumnIndex(FavoriteMovieContract.FavoriteMovie.COLUMN_USERRATING))));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndex(FavoriteMovieContract.FavoriteMovie.COLUMN_POSTER_PATH )));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(FavoriteMovieContract.FavoriteMovie.COLUMN_MOVIE_DESCRIPTION)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(FavoriteMovieContract.FavoriteMovie.COLUMN_RELEASE_DATE)));

                favoriteList.add(movie);

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return favoriteList;
    }

}