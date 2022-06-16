package com.example.travelkeeper.DAO;

import java.util.ArrayList;
import java.util.Arrays;

public class Place {
    public int id;
    public String name;
    public double latitude;
    public double longitude;
    public int rate;
    public String comment;
    public String photoPath;

    public Place(int id, String name, double latitude, double longitude, int rate, String comment, String photoPath) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rate = rate;
        this.comment = comment;
        this.photoPath = photoPath;
    }

    public Place(String name, double latitude, double longitude, int rate, String comment, String photoPath) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rate = rate;
        this.comment = comment;
        this.photoPath = photoPath;
    }

    public ArrayList<String> placeListAtt() {
        return new ArrayList<>(
                Arrays.asList(
                        this.id + "",
                        "'" + this.name + "'",
                        this.latitude + "",
                        this.longitude + "",
                        this.rate + "",
                        "'" + this.comment + "'",
                        "'" + this.photoPath + "'"
                ));
    }
}
