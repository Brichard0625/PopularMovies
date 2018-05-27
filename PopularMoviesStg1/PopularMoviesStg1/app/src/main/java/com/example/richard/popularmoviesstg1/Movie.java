package com.example.richard.popularmoviesstg1;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Movie{
        @SerializedName("poster_path")
        private String posterPath;
        @SerializedName("overview")
        private String overview;
        @SerializedName("release_date")
        private String releaseDate;
        @SerializedName("original_title")
        private String originalTitle;
        @SerializedName("title")
        private String title;
        @SerializedName("vote_average")
        private Double voteAverage;
        @SerializedName("id")
        private Integer id;

        public Movie(String posterPath, String overview, String releaseDate,
                     String originalTitle, String title,
                      Double voteAverage, int id) {
            this.posterPath = posterPath;
            this.overview = overview;
            this.releaseDate = releaseDate;
            this.originalTitle = originalTitle;
            this.title = title;
            this.voteAverage = voteAverage;
            this.id = id;
        }
        public Movie(){

    }

        public String getPosterPath() {
            return"https://image.tmdb.org/t/p/w500"+ posterPath;
        }

        public void setPosterPath(String posterPath) {
            this.posterPath = posterPath;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public String getOriginalTitle() {
            return originalTitle;
        }

        public void setOriginalTitle(String originalTitle) {
            this.originalTitle = originalTitle;
        }

        public String getTitle() {
            return title;
        }
        public Integer getId() {
        return id;
       }

       public void setId(Integer id) {
        this.id = id;
       }

        public void setTitle(String title) {
            this.title = title;
        }

        public Double getVoteAverage() {
            return voteAverage;
        }

        public void setVoteAverage(Double voteAverage) {
            this.voteAverage = voteAverage;
        }
}


