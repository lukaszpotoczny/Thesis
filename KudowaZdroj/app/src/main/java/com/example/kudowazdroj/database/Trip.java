package com.example.kudowazdroj.database;

import java.util.ArrayList;

public class Trip {

    private int id;
    private String name;
    private ArrayList<Location> locations;
    private String mapLink;

    public Trip(int id, String name, ArrayList<Location> locations, String mapLink){
        this.id = id;
        this.name = name;
        this.locations = locations;
        this.mapLink = mapLink;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }

    public String getMapLink() {
        return mapLink;
    }

    public void setMapLink(String mapLink) {
        this.mapLink = mapLink;
    }
}
