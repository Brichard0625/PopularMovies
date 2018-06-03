package com.example.richard.popularmoviesstg1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.MyViewHolder> {
    //declares a private context that will access the DetailActivity.
    private Context mContext;

    //Creates an ArrayList<MovieReviews> movieReviewsList.
    private List<MovieReviews> movieReviewsList;

    //refers to the current instance of  the method mContext & listOfMovies
    public MovieReviewAdapter(Context mContext, List<MovieReviews> movieReviewsList) {
        this.mContext = mContext;
        this.movieReviewsList = movieReviewsList;
    }

    //inflates the reviews_list layout and returns a new ViewHolder.
    @Override
    public MovieReviewAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.reviews_list, viewGroup, false);

        return new MyViewHolder(view);

    }

    //Creates an onBindViewHolder for ReviewersName, and ReviewersComment.
    @Override
    public void onBindViewHolder(final MovieReviewAdapter.MyViewHolder viewHolder, int i) {
        viewHolder.ReviewersName.setText(movieReviewsList.get(i).getmAuthor());
        viewHolder.ReviewersComment.setText(movieReviewsList.get(i).getmContent());

    }

    //Returns the number of movies using getItemCount.
    @Override
    public int getItemCount() {
        return movieReviewsList.size();
    }

    //Creates a ViewHolder and extends to the Recycler view. Declares the views for Reviews_list
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView ReviewersName, ReviewersComment;


        public MyViewHolder(View view) {
            super(view);
            ReviewersName = (TextView) view.findViewById(R.id.ReviewersName);
            ReviewersComment = (TextView) view.findViewById(R.id.ReviewersComment);

            int adapterPosition = getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                String reviewId = movieReviewsList.get(adapterPosition).getmID();
                Intent intent = new Intent(Intent.ACTION_VIEW + reviewId);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("author", movieReviewsList.get(adapterPosition).getmAuthor());
                intent.putExtra("content", movieReviewsList.get(adapterPosition).getmContent());
                intent.putExtra("id", movieReviewsList.get(adapterPosition).getmID());
                intent.putExtra("reviewId", reviewId);
                mContext.startActivity(intent);
                }


                }

                }





            }

