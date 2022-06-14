package com.example.travelkeeper.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

public class PlacesDAO {
    private static final String jdbcUrl = "jdbc:mysql://10.0.2.2:3306/travels?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false";
    private static final ArrayList<String> placesColumns = new ArrayList<>(
            Arrays.asList(
                    "Id",
                    "Name",
                    "Latitude",
                    "Longitude",
                    "Rate",
                    "Comment",
                    "PhotoPath"
            ));

    public PlacesDAO() {}

    public static void insertDB(String table, ArrayList<String> columns, ArrayList<String> elements) throws InterruptedException {
        String columnsString = String.join(", ", columns);
        String elementsString = String.join(", ", elements);

        String sql = "INSERT INTO `" + table + "` ("+ columnsString +")" +
                " VALUES ("+ elementsString +");";

        Thread sqlConn = new Thread(() -> {
            try {
                Connection connection = DriverManager.getConnection(jdbcUrl, "root", "hugotropfort");
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        sqlConn.start();
        sqlConn.join();
    }

    public static ArrayList<Place> getPlacesDB() throws InterruptedException {
        ArrayList<Place> places = new ArrayList<>();

        Thread sqlConn = new Thread(() -> {
            try {
                Connection connection = DriverManager.getConnection(jdbcUrl, "root", "hugotropfort");
                String sql = "SELECT * FROM places";
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("Id");
                    String name = resultSet.getString("Name");
                    double latitude = resultSet.getDouble("Latitude");
                    double longitude = resultSet.getDouble("Longitude");
                    int rate = resultSet.getInt("Rate");
                    String comment = resultSet.getString("Comment");
                    String photoPath = resultSet.getString("PhotoPath");

                    places.add(new Place(id, name, latitude, longitude, rate, comment, photoPath));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        sqlConn.start();
        sqlConn.join();
        return places;
    }

    public static Place getPlaceById(int id) throws InterruptedException {
        ArrayList<Place> places = getPlacesDB();

        for (Place place : places) {
            if(place.id == id) {
                return place;
            }
        }

        return null;
    }

    public static Place addPlaceDB(Place placeToAdd) throws InterruptedException {
        ArrayList<Place> places = getPlacesDB();

        placeToAdd.id = places.size();

        insertDB("places", placesColumns, placeToAdd.placeListAtt());
        places.add(placeToAdd);

        return placeToAdd;
    }

}
