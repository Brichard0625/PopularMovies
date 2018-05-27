package com.example.richard.popularmoviesstg1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TrailerDataAdapter extends RecyclerView.Adapter<TrailerDataAdapter.MyViewHolder> {

        private Context mContext;
        private List<TrailerData> trailerList;

        public TrailerDataAdapter(Context mContext, List<TrailerData> trailerList){
            this.mContext = mContext;
            this.trailerList = trailerList;

        }

        @Override
        public TrailerDataAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.trailers_list, viewGroup, false);
            return new MyViewHolder(view);

        }

        @Override
        public void onBindViewHolder(final TrailerDataAdapter.MyViewHolder viewHolder, int i){
            viewHolder.title.setText(trailerList.get(i).getName());

        }

        @Override
        public int getItemCount(){

            return trailerList.size();

        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView title;
            public ImageView thumbnail;

            public MyViewHolder(View view){
                super(view);
                title = (TextView) view.findViewById(R.id.title);
                thumbnail = (ImageView) view.findViewById(R.id.Thumbnail);

                view.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION){
                            TrailerData clickedDataItem = trailerList.get(pos);
                            String videoId = trailerList.get(pos).getKey();
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+videoId));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("VIDEO_ID", videoId);
                            mContext.startActivity(intent);

                            Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getName(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }
    }

