package com.example.kudowazdroj.database;

import java.util.ArrayList;

public class Attraction {

    private int id;
    private String name;
    private ArrayList<String> images;

    public Attraction(int id, String name){
        this.id = id;
        this.name = name;
        images = new ArrayList<String>();
    }

    public Attraction(int id, String name, String photo){
        this.id = id;
        this.name = name;
        images = new ArrayList<String>();
        images.add(photo);
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

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }
}
