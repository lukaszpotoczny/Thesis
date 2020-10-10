package com.example.kudowazdroj.database;

public class Ad {

    private int id;
    private String title;
    private String author;
    private String content;

    public Ad(int id, String title, String author, String content){
        setId(id);
        setTitle(title);
        setAuthor(author);
        setContent(content);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
