package com.example.richard.popularmoviesstg1;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.richard.popularmoviesstg1.FavoriteMovieContract;
import com.example.richard.popularmoviesstg1.FavoriteMovieHelper;

import static com.example.richard.popularmoviesstg1.FavoriteMovieContract.FavoriteMovie.TABLE_NAME;

/**
 * Created by ahmed on 2/3/2018.
 */

public class MovieContentProvider extends ContentProvider {
    private FavoriteMovieHelper myFavoriteMovieHelper;



    public final static int MOVIES = 100;
    public final static int MOVIES_WITH_ID = 101;
    public static final String AUTHORITY = "com.example.richard.popularmoviesstg1";
    public static final String FavMovies = "FavoriteMovies";

    private static final UriMatcher mMoviesUriMatcher = buildMoviesUriMatcher();

    public static UriMatcher buildMoviesUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTHORITY, FavMovies , MOVIES);

        uriMatcher.addURI(AUTHORITY,FavMovies + "/#", MOVIES_WITH_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        myFavoriteMovieHelper = new FavoriteMovieHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = myFavoriteMovieHelper.getReadableDatabase();
        int matching = mMoviesUriMatcher.match(uri);
        Cursor moviesCursor;
        switch (matching) {
            case MOVIES:
                moviesCursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case MOVIES_WITH_ID:
                String idquery = uri.getLastPathSegment();

                moviesCursor = db.query(TABLE_NAME,
                        projection,
                        FavoriteMovieContract.FavoriteMovie.COLUMN_MOVIEID + "=?",
                        new String[]{idquery},
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        moviesCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return moviesCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = myFavoriteMovieHelper.getWritableDatabase();
        int matching = mMoviesUriMatcher.match(uri);

        Uri resultUri;
        switch (matching) {
            case MOVIES:
                long id = db.insert(TABLE_NAME, null, contentValues);

                if (id > 0) {
                    resultUri = ContentUris.withAppendedId(FavoriteMovieContract.FavoriteMovie.CONTENT_URI, id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }


        getContext().getContentResolver().notifyChange(uri, null);
        return resultUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase db = myFavoriteMovieHelper.getWritableDatabase();
        int matching = mMoviesUriMatcher.match(uri);
        int favDeleted;
        switch (matching) {
            case MOVIES_WITH_ID:
                String id = uri.getLastPathSegment();


                favDeleted = db.delete(FavoriteMovieContract.FavoriteMovie.TABLE_NAME, FavoriteMovieContract.FavoriteMovie.COLUMN_MOVIEID + "=?", new String[]{id});

                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        if (favDeleted != 0) {

            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of tasks deleted
        return favDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}