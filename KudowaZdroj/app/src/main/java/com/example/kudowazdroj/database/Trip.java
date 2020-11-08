package com.example.kudowazdroj.database;

import java.util.ArrayList;

public class Trip {

    private String  id;
    private String name;
    private int duration;
    private ArrayList<Attraction> attractions;
    private String mapLink;

    public Trip(String id, String name, int duration, ArrayList<Attraction> attractions, String mapLink){
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.attractions = attractions;
        this.mapLink = mapLink;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public ArrayList<Attraction> getAttractions() {
        return attractions;
    }

    public void setAttractions(ArrayList<Attraction> attractions) {
        this.attractions = attractions;
    }

    public String getMapLink() {
        return mapLink;
    }

    public void setMapLink(String mapLink) {
        this.mapLink = mapLink;
    }
}
