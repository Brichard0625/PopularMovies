package com.example.richard.popularmoviesstg1;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {
    //declares a private context that will access the DetailActivity.
    private Context mContext;

    //Creates an ArrayList<Movie> listOfMovies.
    private List<Movie> listOfMovies;

    //refers to the current instance of  the method mContext & listOfMovies
    public MovieAdapter(Context mContext, List<Movie> listOfMovies) {
        this.mContext = mContext;
        this.listOfMovies = listOfMovies;
    }

    //inflates the movie_card layout and returns a new ViewHolder.
    @Override
    public MovieAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movie_card, viewGroup, false);

        return new MyViewHolder(view);

    }
    //Creates an onBindViewHolder for Title, userRating, and movie thumbnail
    @Override
    public void onBindViewHolder(final MovieAdapter.MyViewHolder viewHolder, int i) {
        viewHolder.title.setText(listOfMovies.get(i).getOriginalTitle());
        String vote = Double.toString(listOfMovies.get(i).getVoteAverage());
        viewHolder.userrating.setText(vote);

        //applies the Glide image loader/caching for listOfMovies with their image.
        Glide.with(mContext)
                .load(listOfMovies.get(i).getPosterPath())
                .placeholder(R.drawable.ic_launcher_background)
                .into(viewHolder.thumbnail);

    }

    //Returns the number of movies using getItemCount.
    @Override
    public int getItemCount() {
        return listOfMovies.size();
    }

    //Creates a ViewHolder and extends to the Recycler view. Declares the views for movie_card
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, userrating;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            userrating = (TextView) view.findViewById(R.id.userrating);
            thumbnail = (ImageView) view.findViewById(R.id.Thumbnail);



            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        Movie clickedDataItem = listOfMovies.get(adapterPosition);
                        Intent intent = new Intent(mContext, DetailActivity.class);
                        intent.putExtra("original_title", listOfMovies.get(adapterPosition).getOriginalTitle());
                        intent.putExtra("poster_path", listOfMovies.get(adapterPosition).getPosterPath());
                        intent.putExtra("overview", listOfMovies.get(adapterPosition).getOverview());
                        intent.putExtra("vote_average", Double.toString(listOfMovies.get(adapterPosition).getVoteAverage()));
                        intent.putExtra("release_date", listOfMovies.get(adapterPosition).getReleaseDate());
                        intent.putExtra("id", listOfMovies.get(adapterPosition).getId());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        //Creates a toast message of which movie you've selected and starts DetailActivity.class
                        mContext.startActivity(intent);
                        Toast.makeText(v.getContext(), "You've selected" + clickedDataItem.getOriginalTitle(), Toast.LENGTH_SHORT).show();

                    }
                }


            });


        }
    }

}
