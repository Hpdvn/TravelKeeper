package com.example.travelkeeper.DAO;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ConnectionDB {
    ArrayList<Place> places = new ArrayList<>();

    public ConnectionDB() {
        new Thread(() -> {
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3306/travels", "root", "hugotropfort");

                String sql = "SELECT * FROM places";
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int id = Integer.parseInt(resultSet.getString("Id"));
                    String name = resultSet.getString("Name");
                    float latitude = Float.parseFloat(resultSet.getString("Latitude"));
                    float longitude = Float.parseFloat(resultSet.getString("Longitude"));
                    int rate = Integer.parseInt(resultSet.getString("Rate"));
                    String comment = resultSet.getString("Comment");
                    String photoPath = resultSet.getString("PhotoPath");

                    places.add(new Place(id, name, latitude, longitude, rate, comment, photoPath));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }
}
