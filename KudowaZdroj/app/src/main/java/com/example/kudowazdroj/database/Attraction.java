package com.example.kudowazdroj.database;

import java.io.Serializable;
import java.util.ArrayList;

public class Attraction implements Serializable, Comparable {

    private int id;
    private String name;
    private String content;
    private int time;
    private String photo;
    private ArrayList<String> images;

    public Attraction(){
    }

    public Attraction(int id, String name, String c, int time, String photo){
        this.id = id;
        this.name = name;
        this.content = c;
        this.time = time;
        this.photo = photo;
        images = new ArrayList<String>();
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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public int compareTo(Object o) {
        int id2=((Attraction)o).getId();
        return this.id-id2;
    }
}
