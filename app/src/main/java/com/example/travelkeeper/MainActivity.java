package com.example.travelkeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.travelkeeper.DAO.Place;
import com.example.travelkeeper.DAO.PlacesDAO;
import com.example.travelkeeper.utils.ListAdapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Place> places;
    Button addPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Travel keeper");

        try {
            initPlacesListRecyclerView();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        addPlace = findViewById(R.id.add_place);

        addPlace.setOnClickListener(this::addPlace);
    }

    private void initPlacesListRecyclerView() throws InterruptedException {
        places = PlacesDAO.getPlacesDB();
        ListAdapter listAdapter = new ListAdapter(places, this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }

    private void addPlace(View view) {
        Intent intent = new Intent(this, AddPlaceActivity.class);
        startActivity(intent);
    }
}