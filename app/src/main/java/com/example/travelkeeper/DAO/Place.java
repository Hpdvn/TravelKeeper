package com.example.travelkeeper.DAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Place {
    public int id;
    public String name;
    public float latitude;
    public float longitude;
    public int rate;
    public String comment;
    public String photoPath;

    public Place(int id, String name, float latitude, float longitude, int rate, String comment, String photoPath) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rate = rate;
        this.comment = comment;
        this.photoPath = photoPath;
    }

    public Place(String name, float latitude, float longitude, int rate, String comment, String photoPath) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rate = rate;
        this.comment = comment;
        this.photoPath = photoPath;
    }

    public ArrayList<String> placeListAtt() {
        return new ArrayList<String>(
                Arrays.asList(
                        this.id+"",
                        this.name,
                        this.latitude+"",
                        this.longitude+"",
                        this.rate+"",
                        this.comment,
                        this.photoPath
                ));
    }
}
