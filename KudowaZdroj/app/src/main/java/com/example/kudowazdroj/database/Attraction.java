package com.example.kudowazdroj.database;

import java.io.Serializable;
import java.util.ArrayList;

public class Attraction implements Serializable {

    private int id;
    private String name;
    private String content;
    private ArrayList<String> images;

    public Attraction(int id, String name, String c){
        this.id = id;
        this.name = name;
        this.content = c;
        images = new ArrayList<String>();
    }

    public Attraction(int id, String name, String c, String photo){
        this.id = id;
        this.name = name;
        this.content = c;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
