package com.example.travelkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.travelkeeper.DAO.ConnectionDB;
import com.example.travelkeeper.DAO.Place;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ConnectionDB connectionDB;
    ArrayList<Place> places;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectionDB = new ConnectionDB();
        places = connectionDB.getPlaces();
    }
}