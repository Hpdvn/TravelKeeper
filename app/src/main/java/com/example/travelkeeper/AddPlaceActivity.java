package com.example.travelkeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelkeeper.DAO.Place;
import com.example.travelkeeper.DAO.PlacesDAO;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class AddPlaceActivity extends AppCompatActivity {
    TextView placeName, comment, rating, imagePath;
    Button chooseImage, submit, allowLocation;
    FusedLocationProviderClient client;

    Double lat;
    Double lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);
        getSupportActionBar().setTitle("Travel keeper - New");
        initViews();

        client = LocationServices.getFusedLocationProviderClient(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length >= 2 &&
                        (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                                grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                    getCurrentLocation();
                    allowLocation.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(this, "Please allow location to put your place on the map!",
                            Toast.LENGTH_LONG).show();
                    allowLocation.setVisibility(View.VISIBLE);
                }
                return;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }

    public void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Safe belt if location permission not granted
                return;
            }
            client.getLastLocation().addOnCompleteListener(
                    task -> {
                        Location location = task.getResult();
                        if (location != null) {
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                        }
                    });
        } else {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    private void initViews() {
        placeName = findViewById(R.id.place_name_add);
        comment = findViewById(R.id.comment_add);
        rating = findViewById(R.id.rating_add);
        imagePath = findViewById(R.id.image_path);

        chooseImage = findViewById(R.id.select_image_add);
        submit = findViewById(R.id.submit_add);
        allowLocation = findViewById(R.id.allow_location_add);
        
        chooseImage.setOnClickListener(this::imageChooser);
        submit.setOnClickListener(this::addPlace);
        allowLocation.setOnClickListener(this::allowLocationPermission);
    }

    private void addPlace(View view) {
        if (placeName.getText().equals("") ||
                comment.getText().equals("") ||
                rating.getText().equals("") ||
                imagePath.getText().toString().equals("No image selected")) {
            Toast.makeText(this, "Required field(s) missing.",
                    Toast.LENGTH_LONG).show();
        } else {
            Place placeToAdd = new Place(
                    placeName.getText().toString(),
                    lat,
                    lon,
                    Integer.parseInt(rating.getText().toString()),
                    comment.getText().toString(),
                    imagePath.getText().toString()
            );

            try {
                PlacesDAO.addPlaceDB(placeToAdd);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void imageChooser(View view) {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_OPEN_DOCUMENT);
        i.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);

        startActivityForResult(Intent.createChooser(i, "Select Picture"), 200);
    }

    private void allowLocationPermission(View view) {
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
    }

    @SuppressLint("WrongConstant")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 200) {
                // Get the url of the image from data
                final int takeFlags = data.getFlags() & Intent.FLAG_GRANT_READ_URI_PERMISSION;
                ContentResolver resolver = this.getContentResolver();
                resolver.takePersistableUriPermission(Uri.parse(data.getDataString()), takeFlags);

                String selectedImageUri = data.getDataString();
                if (selectedImageUri != null) {
                    imagePath.setText(selectedImageUri);
                }
            }
        }
    }
}