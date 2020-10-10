package com.example.kudowazdroj.database;

public class News {

    private int id;
    private String name;

    public News(int id, String name){
        setId(id);
        setName(name);
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


}
