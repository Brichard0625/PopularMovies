package com.example.richard.popularmoviesstg1;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerDataResponse {

    @SerializedName("id")
    private int id_trailer;
    @SerializedName("results")
    private List<TrailerData> results;

    public int getIdTrailer(){
        return id_trailer;
    }

    public void seIdTrailer(int id_trailer){
        this.id_trailer = id_trailer;
    }

    public List<TrailerData> getResults(){
        return results;
    }
}


