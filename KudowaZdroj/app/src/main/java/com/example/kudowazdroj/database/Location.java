package com.example.kudowazdroj.database;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Location implements Serializable, Comparable {

    private int id;
    private String name;
    private double lat;
    private double lng;
    private String tag;
    private String image;

    public Location(){

    }

    public Location(int id, String name, double lat, double lng, String tag, String image){
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.tag = tag;
        this.image = image;
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

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int compareTo(Object o) {
        int id2=((Location)o).getId();
        return this.id-id2;
    }
}
