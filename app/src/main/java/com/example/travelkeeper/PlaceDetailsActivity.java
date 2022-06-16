package com.example.travelkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travelkeeper.DAO.Place;
import com.example.travelkeeper.DAO.PlacesDAO;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class PlaceDetailsActivity extends AppCompatActivity {
    TextView titleDetail, commentDetail, ratingDetail, latitude, longitude;
    ImageView image;
    Button buttonMap;
    Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);
        Bundle variables = getIntent().getExtras();

        int placeId = -1;

        if (variables != null) {
            placeId = variables.getInt("placeId");
        }

        if (placeId != -1) {
            try {
                place = PlacesDAO.getPlaceById(placeId);
                initViews();
            } catch (InterruptedException | URISyntaxException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void initViews() throws URISyntaxException, IOException {
        Objects.requireNonNull(getSupportActionBar()).setTitle("Travel keeper - Details");
        titleDetail = findViewById(R.id.place_name_detail);
        commentDetail = findViewById(R.id.comment_detail);
        ratingDetail = findViewById(R.id.rating_detail);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        image = findViewById(R.id.image);
        buttonMap = findViewById(R.id.map_button);
        buttonMap.setOnClickListener(this::displayMap);

        titleDetail.setText(place.name);
        commentDetail.setText(place.comment);
        ratingDetail.setText(place.rate+"");
        latitude.setText(place.latitude+"");
        longitude.setText(place.longitude+"");

        Uri uri = Uri.parse(place.photoPath);
        image.setImageURI(uri);

    }

    private void displayMap(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("latitude", place.latitude);
        intent.putExtra("longitude", place.longitude);
        intent.putExtra("name", place.name);
        startActivity(intent);
    }
}