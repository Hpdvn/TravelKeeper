package com.example.travelkeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travelkeeper.DAO.Place;
import com.example.travelkeeper.DAO.PlacesDAO;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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

    private void initViews() throws URISyntaxException, IOException {
        getSupportActionBar().setTitle("Travel keeper - Details");
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