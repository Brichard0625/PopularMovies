package com.example.richard.popularmoviesstg1;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
//Gets most popular movies and top rated movies.
public interface Service {
    @GET("movie/popular")
    Call<MovieResponse>getPopularMovies(@Query("api_key")String apiKey);

    @GET("movie/top_rated")
    Call<MovieResponse>getTopRatedMovies(@Query("api_key")String apiKey);
    //newly added
    @GET("movie/{movie_id}/videos")
    Call<TrailerDataResponse> getMovieTrailer(@Path("movie_id") int id, @Query("api_key") String apiKey);
    //newly added
    @GET("movie/{id}/reviews")
    Call<MovieReviews> getReviews(@Path("id") int id, @Query("api_key") String apiKey);
}

