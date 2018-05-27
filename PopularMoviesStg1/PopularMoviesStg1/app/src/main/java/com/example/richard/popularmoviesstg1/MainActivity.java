package com.example.richard.popularmoviesstg1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    //Declares recyclerView, MovieAdapter.class, listOfMovies Array, swipeRefreshLayout.
    private RecyclerView recyclerView;
    private MovieAdapter mAdapter;
    private List<Movie> listOfMovies;
    private SwipeRefreshLayout swipeContainer;
    private FavoriteMovieHelper favoriteMovieHelper;
    private AppCompatActivity activity = MainActivity.this;
    public static final String LOG_TAG = MovieAdapter.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();


    }


    //Gets the instance of current activity
    public Activity getActivity() {
        Context context = this;
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    private void initViews() {

        //Creates a reference for RecyclerView, listOfMovies Array and MovieAdapter.
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        listOfMovies = new ArrayList<>();
        mAdapter = new MovieAdapter(this, listOfMovies);

        //Creates a GridLayout and displays 2 movies hortizal if in portrait mode or 3 movies horizontal in landscapemode
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }

        //Establishes the animations on items as the adapter changes.
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        favoriteMovieHelper = new FavoriteMovieHelper(activity);

        //Uses the findViewById to reference mainContent swipeContainer.
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.mainContent);

        //Changes the SwipeLoader color to orange
        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);

        //Refreshes Layout
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            //On Refresh displays Toast message "Movies have been refreshed.
            @Override
            public void onRefresh() {
                initViews();
                Toast.makeText(MainActivity.this, "Movies have been refreshed", Toast.LENGTH_SHORT).show();
            }
        });

        checkSortOrder();
    }

    private void initViews2() {

        //Creates a reference for RecyclerView, listOfMovies Array and MovieAdapter.
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        listOfMovies = new ArrayList<>();
        mAdapter = new MovieAdapter(this, listOfMovies);

        //Creates a GridLayout and displays 2 movies hortizal if in portrait mode or 3 movies horizontal in landscapemode
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }

        //Establishes the animations on items as the adapter changes.
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        favoriteMovieHelper = new FavoriteMovieHelper(activity);

        getFavoriteMovies();

        //Uses the findViewById to reference mainContent swipeContainer.
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.mainContent);

        //Changes the SwipeLoader color to orange
        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);

        //Refreshes Layout
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            //On Refresh displays Toast message "Movies have been refreshed.
            @Override
            public void onRefresh() {
                initViews2();
                Toast.makeText(MainActivity.this, "Movies have been refreshed", Toast.LENGTH_SHORT).show();
            }
        });


    }


    //This block of code loadJSON() loads data for popular movies.
    private void loadJSON() {
        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                Toast.makeText(getApplicationContext(),"Please obtains your API_KEY from theMoviedb.org ", Toast.LENGTH_SHORT).show();
                return;
            }
            Client Client = new Client();
            Service apiService =
                    Client.getClient().create(Service.class);
            Call<MovieResponse> call = apiService.getPopularMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<MovieResponse>() {

                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    List<Movie> movies = response.body().getResults();
                    recyclerView.setAdapter(new MovieAdapter(getApplicationContext(), movies));
                    recyclerView.smoothScrollToPosition(0);
                  if (swipeContainer.isRefreshing()) {
                       swipeContainer.setRefreshing(false);
                    }

                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable e) {
                    Log.d("Err", e.getMessage());
                    Toast.makeText(MainActivity.this, "Error Locating Data!", Toast.LENGTH_SHORT).show();
                }


            });

        } catch (Exception errC) {
            Log.d("ErrCatch", errC.getMessage());
            Toast.makeText(this, errC.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    //This block of code loadJSON1() loads data for Top Rated Movies
    private void loadJSON1() {
        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                Toast.makeText(getApplicationContext(),"Please obtains your API_KEY from theMoviedb.org ", Toast.LENGTH_SHORT).show();
                return;
            }
            Client Client = new Client();
            Service apiService =
                    Client.getClient().create(Service.class);
            Call<MovieResponse> call = apiService.getTopRatedMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    List<Movie> movies = response.body().getResults();
                    recyclerView.setAdapter(new MovieAdapter(getApplicationContext(), movies));
                    recyclerView.smoothScrollToPosition(0);
                    if (swipeContainer.isRefreshing()) {
                        swipeContainer.setRefreshing(false);
                    }

                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable e) {
                    Log.d("Err", e.getMessage());
                    Toast.makeText(MainActivity.this, "Error Locating Data!", Toast.LENGTH_SHORT).show();
                }


            });

        } catch (Exception errC) {
            Log.d("ErrCatch", errC.getMessage());
            Toast.makeText(this, errC.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    //Starts the settingsActivity to access top rated or most popular
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent= new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;

    }

    //Checks which option is selected when preferences have been changed.
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String Check){
        checkSortOrder();


    }

    //Checks whether preferences have been changed to Most Popular or Top Rated or Favorite Movies and displays Toast to which one is selected.
    private void checkSortOrder(){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        String sortOrder = preferences.getString(
                this.getString(R.string.prefSortOrderKey),
                this.getString(R.string.prefMostPopular));

        if (sortOrder.equals(this.getString(R.string.prefMostPopular))){
            Log.d(LOG_TAG, "Sorting by most popular");
            loadJSON();
            Toast toast = Toast.makeText(MainActivity.this, "Movies set to Most Popular", Toast.LENGTH_SHORT);
            View toastView = toast.getView();
            toastView.getBackground().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
            TextView text = toastView.findViewById(android.R.id.message);
            text.setTextColor(Color.RED);
            toast.show();


        }

        else if(sortOrder.equals(this.getString(R.string.Favorite))){
            Log.d(LOG_TAG,"Displaying Favorite Movies");
            initViews2();
            Toast toast = Toast.makeText(MainActivity.this, "Displaying Favorite Movies", Toast.LENGTH_SHORT);
            View toastView = toast.getView();
            toastView.getBackground().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
            TextView text = toastView.findViewById(android.R.id.message);
            text.setTextColor(Color.RED);
            toast.show();


        }
        else{
            Log.d(LOG_TAG, "Sorting by vote average");
            loadJSON1();
            Toast toast = Toast.makeText(MainActivity.this, "Movies set to Top Rated", Toast.LENGTH_SHORT);
            View toastView = toast.getView();
            toastView.getBackground().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
            TextView text = toastView.findViewById(android.R.id.message);
            text.setTextColor(Color.RED);
            toast.show();

        }
        }

        @Override
    public void onResume() {
            super.onResume();
            if (listOfMovies.isEmpty()) {
                checkSortOrder();
            } else {
                checkSortOrder();
            }
        }


    private void getFavoriteMovies(){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params){
                listOfMovies.clear();
                listOfMovies.addAll(favoriteMovieHelper.getAllFavorite());
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid){
                super.onPostExecute(aVoid);
                mAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

}




