package com.example.richard.popularmoviesstg1;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    //Create a Base_URL of your API. This URL will be used for all movie requests.
    public static final String BASE_URL="http://api.themoviedb.org/3/";
    public static Retrofit retrofit=null;

    //Uses the GsonConverterFactory to convert JSON data from the BASE_URL
    public static Retrofit getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
