package com.example.travelkeeper.DAO;

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
}
