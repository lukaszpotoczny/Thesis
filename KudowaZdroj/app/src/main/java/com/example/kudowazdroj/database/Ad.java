package com.example.kudowazdroj.database;

public class Ad {

    private int id;
    private String name;

    public Ad(int id, String name){
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
