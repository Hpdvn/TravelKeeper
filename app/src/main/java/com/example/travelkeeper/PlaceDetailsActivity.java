package com.example.travelkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travelkeeper.DAO.Place;
import com.example.travelkeeper.DAO.PlacesDAO;

import java.io.File;

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

        String placeId = null;

        if (variables != null) {
            placeId = variables.getString("placeId");
        }

        if (placeId != null) {
            try {
                place = PlacesDAO.getPlaceById(Integer.parseInt(placeId));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        initViews();
    }

    private void initViews() {
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

        File imgFile = new  File(place.photoPath);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            image.setImageBitmap(myBitmap);
        }
    }

    private void displayMap(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("latitude", place.latitude);
        intent.putExtra("longitude", place.longitude);
        startActivity(intent);
    }
}