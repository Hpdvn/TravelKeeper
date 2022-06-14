package com.example.travelkeeper.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelkeeper.DAO.Place;
import com.example.travelkeeper.MainActivity;
import com.example.travelkeeper.PlaceDetailsActivity;
import com.example.travelkeeper.R;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
    ArrayList<Place> places;
    Context context;

    public ListAdapter(ArrayList<Place> places, Context context) {
        this.places = places;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_card_element, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Place place = places.get(position);
        holder.fillViewsWithData(place);

        holder.relativeLayout.setOnClickListener(v -> {

           Intent intent = new Intent(context, PlaceDetailsActivity.class);
           intent.putExtra("placeId", place.id);

           context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView placeName, comment, rating;

        RelativeLayout relativeLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            placeName = itemView.findViewById(R.id.place_name);
            comment = itemView.findViewById(R.id.comment);
            rating = itemView.findViewById(R.id.rating);

            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }

        void fillViewsWithData(final Place item) {
            placeName.setText(item.name);
            comment.setText(item.comment);
            rating.setText(item.rate+"");
        }
    }
}