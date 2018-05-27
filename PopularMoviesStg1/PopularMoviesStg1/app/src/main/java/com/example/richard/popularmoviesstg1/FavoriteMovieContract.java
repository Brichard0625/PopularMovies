package com.example.richard.popularmoviesstg1;

import android.provider.BaseColumns;

public class FavoriteMovieContract {

    public static final class FavoriteMovie implements BaseColumns {


        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_MOVIEID = "movieid";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_USERRATING = "userrating" ;
        public static final String COLUMN_POSTER_PATH = "posterpath" ;
        public static final String COLUMN_MOVIE_DESCRIPTION = "overview";
        public static final String COLUMN_RELEASE_DATE = "release_date";
    }
}
