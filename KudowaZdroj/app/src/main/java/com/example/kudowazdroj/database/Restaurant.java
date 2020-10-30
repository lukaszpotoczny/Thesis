package com.example.kudowazdroj.database;


public class Restaurant implements Comparable{

    private int id;
    private String name;
    private String address;
    private String rating;
    private String phone;
    private String website;
    private String content;
    private String image;
    private String key;

    public Restaurant(){

    }

    public Restaurant(int id, String n, String a, String r, String p, String w, String c, String i){
        this.id = id;
        this.name = n;
        this.address = a;
        this.rating = r;
        this.phone = p;
        this.website = w;
        this.content = c;
        this.image = i;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String photo) {
        this.image = photo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int compareTo(Object o) {
        int id2=((Restaurant)o).getId();
        return this.id-id2;
    }
}
