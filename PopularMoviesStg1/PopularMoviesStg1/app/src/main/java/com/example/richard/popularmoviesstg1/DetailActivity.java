package com.example.richard.popularmoviesstg1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailActivity extends AppCompatActivity {
    //Declares all the Views from the content_detail activity.
    TextView nameOfMovie, plotSynopsis, userRating, ReleaseDate;
    ImageView imageView;
    private RecyclerView recyclerView;//newly add
    private RecyclerView recyclerView1;//newly add
    private TrailerDataAdapter adapter;//newly add
    private MovieReviewAdapter adapter1;
    private List<TrailerData> trailerList;//newly add
    private List<MovieReviews> movieReviewsList;
    private FavoriteMovieHelper favoriteMovieHelper;
    private Movie favoriteMov;
    private final AppCompatActivity activity = DetailActivity.this;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitydetail);
        initViews();
        initviews2();


        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        initCollapsingToolbar();

        //Uses the findViewById to to reference the views from the layout.
        imageView = (ImageView) findViewById(R.id.thumbnail_imageheader);
        nameOfMovie = (TextView) findViewById(R.id.title);
        plotSynopsis = (TextView) findViewById(R.id.movieDetails);
        userRating = (TextView) findViewById(R.id.rating);
        ReleaseDate = (TextView) findViewById(R.id.release);


            //Gets data from MovieAdapter and applies to the content_detail activity.
            String thumbnail = getIntent().getExtras().getString("poster_path");
            String movieName = getIntent().getExtras().getString("original_title");
            String description = getIntent().getExtras().getString("overview");
            String rating = getIntent().getExtras().getString("vote_average");
            String release = getIntent().getExtras().getString("release_date");

          //Sets image URL to poster_path.
          String poster = "https://image.tmdb.org/t/p/w300" + thumbnail;




        //Applies the Glide image loader/caching for thumbnail.
            Glide.with(this)
                    .load(poster)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(imageView);

            //Sets the text to display data for movieName, description, rating, release.
            nameOfMovie.setText(movieName);
            plotSynopsis.setText(description);
            ReleaseDate.setText(release);
            userRating.setText(rating);

            //Changes the color of rating number depending on rating value
            Double ratingNumber = Double.parseDouble(userRating.getText().toString());
            if(ratingNumber >= 8){
                userRating.setTextColor(getResources().getColor(R.color.Hot_Rating));

            }
            else if((ratingNumber <= 7.9)  && (ratingNumber >= 6) ) {
                userRating.setTextColor(getResources().getColor(R.color.Warm_Rating));

            }

            else{
                userRating.setTextColor(getResources().getColor(R.color.Cold_Rating));
            }

            //Uses the findViewById to reference Favorite Button.
        MaterialFavoriteButton materialFavoriteButtonNice =
                (MaterialFavoriteButton) findViewById(R.id.fav_button);


        //Saves favorite movie once button is clicked and displays toast message.
        materialFavoriteButtonNice.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener(){
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite){
                        if (favorite){
                            SharedPreferences.Editor editor = getSharedPreferences("com.example.richard.popularmoviesstg1", MODE_PRIVATE).edit();
                            editor.putBoolean("Favorite Added", true);
                            editor.commit();
                            saveFavorite();
                            Snackbar.make(buttonView, "MOVIE HAS BEEN ADDED TO FAVORITES",
                                    Snackbar.LENGTH_SHORT).show();
                        }
                        //Deletes the saved moved and displays message.
                        else{
                            int movie_id = getIntent().getExtras().getInt("id");
                            favoriteMovieHelper = new FavoriteMovieHelper(DetailActivity.this);
                            favoriteMovieHelper.deleteFavorite(movie_id);
                            SharedPreferences.Editor editor = getSharedPreferences("com.example.richard.popularmoviesstg1", MODE_PRIVATE).edit();
                            editor.putBoolean("Favorite Removed", true);
                            editor.commit();
                            Snackbar.make(buttonView, "MOVIE HAD BEEN DELETED FROM FAVORITES",
                                    Snackbar.LENGTH_SHORT).show();
                        }

                    }
                }
        );





        }



    //Implements a collapsing appbar and sets the scrollRange
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        collapsingToolbarLayout.setTitle("");
        final AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow=false;
            int scrollRange= -1;

            @Override
            public void onOffsetChanged(AppBarLayout appbarlayout, int verticalOffset){
                if(scrollRange == -1){
                    scrollRange=appBarLayout.getTotalScrollRange();
                }
                if(scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(getString(R.string.moviePlot));
                    isShow = true;
                }else{
                    collapsingToolbarLayout.setTitle("");
                    isShow=false;
                }
            }


        });


    }
    //sets data to display all available trailers for selected movie.
    private void initViews(){
        trailerList = new ArrayList<>();
        adapter = new TrailerDataAdapter(this, trailerList);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        loadJSON();

    }
    //sets data to display all available reviews for selected movie.
    private void initviews2(){
        movieReviewsList = new ArrayList<>();
        adapter1 = new MovieReviewAdapter(this, movieReviewsList);

        recyclerView1 = (RecyclerView) findViewById(R.id.recycler_view2);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager (getApplicationContext());
        recyclerView1.setLayoutManager(mLayoutManager1);
        recyclerView1.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();

        loadJSON1();

    }

    //Loads JSON Data for movie trailers
    private void loadJSON(){
           int movie_id =getIntent().getExtras().getInt("id");
        try{
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()){
                Toast.makeText(getApplicationContext(), "Please obtain your API Key from themoviedb.org", Toast.LENGTH_SHORT).show();
                return;
            }
            Client Client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<TrailerDataResponse> call = apiService.getMovieTrailer(movie_id, BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<TrailerDataResponse>() {
                @Override
                public void onResponse(Call<TrailerDataResponse> call, Response<TrailerDataResponse> response) {
                    List<TrailerData> trailer = response.body().getResults();
                    recyclerView.setAdapter(new TrailerDataAdapter(getApplicationContext(), trailer));
                    recyclerView.smoothScrollToPosition(0);
                }

                @Override
                public void onFailure(Call<TrailerDataResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(DetailActivity.this, "Error fetching trailer data, CHECK INTERNET CONNECTION", Toast.LENGTH_SHORT).show();

                }
            });

        }catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    //Loads JSON Data for movie reviews.
    private void loadJSON1(){
        int id = getIntent().getExtras().getInt("id");
        try{
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()){
                Toast.makeText(getApplicationContext(), "Please obtain your API Key from themoviedb.org", Toast.LENGTH_SHORT).show();
                return;
            }
            Client Client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<MovieReviews> call = apiService.getReviews(id,BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<MovieReviews>() {
                @Override
                public void onResponse(Call<MovieReviews> call, Response<MovieReviews> response) {
                List<MovieReviews> movieReviewsList = response.body().getresults();
                    recyclerView1.setAdapter(new MovieReviewAdapter(getApplicationContext(), movieReviewsList));
                    recyclerView1.smoothScrollToPosition(0);

                }

                @Override
                public void onFailure(Call<MovieReviews> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(DetailActivity.this, "Error fetching trailer data, CHECK INTERNET CONNECTION" , Toast.LENGTH_SHORT).show();

                }
            });

        }catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    //Saves data needed for favorite movies.
    public void saveFavorite(){
        favoriteMovieHelper = new FavoriteMovieHelper(activity);
        favoriteMov= new Movie();

        int movie_id = getIntent().getExtras().getInt("id");
        favoriteMov.setId(movie_id);

        String rating = getIntent().getExtras().getString("vote_average");
        favoriteMov.setVoteAverage(Double.parseDouble(rating));

       String Poster = getIntent().getExtras().getString("poster_path");
       favoriteMov.setPosterPath(Poster);


        favoriteMov.setOriginalTitle(nameOfMovie.getText().toString().trim());
        favoriteMov.setReleaseDate(ReleaseDate.getText().toString().trim());
        favoriteMov.setOverview(plotSynopsis.getText().toString().trim());

        favoriteMovieHelper.addFavorite(favoriteMov);




    }


}

