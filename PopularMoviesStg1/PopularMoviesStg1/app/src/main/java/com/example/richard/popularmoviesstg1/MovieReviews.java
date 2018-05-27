package com.example.richard.popularmoviesstg1;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieReviews {
            @SerializedName("id")
            private String mID;
            @SerializedName("author")
            private String mAuthor;
            @SerializedName("content")
            private String mContent;
            @SerializedName("results")
            private List<MovieReviews> results;

            public MovieReviews(String mID, String mAuthor, String mContent){
                this.mID=mID;
                this.mContent=mContent;
                this.mAuthor=mAuthor;
            }
            public List<MovieReviews> getresults() {
            return results;
            }

            public void setResults(List<MovieReviews> results) {
                this.results = results;
            }



            public String getmID() {
                return mID;
            }

            public void setmID(String mID) {
                this.mID= mID;
            }

            public String getmAuthor() {
                return mAuthor;
            }

            public void setmAuthor(String mAuthor){
                this.mAuthor = mAuthor;
            }

            public String getmContent() {
                return mContent;
            }

            public void setmContent(String mContent){
                this.mContent= mContent;
        }

    }

