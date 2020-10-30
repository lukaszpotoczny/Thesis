package com.example.kudowazdroj.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Ad implements Comparable{

    private String id;
    private String title;
    private String author;
    private String content;
    private String contact;
    private String date;

    public Ad(){

    }

    public Ad(String id, String title, String date, String content, String author, String contact){
        this.id = id;
        this.title = title;
        this.date = date;
        this.content = content;
        this.author = author;
        this.contact = contact;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int compareTo(Object o) {
        String stringDate2 = ((Ad) o).getDate();
        SimpleDateFormat format = new SimpleDateFormat("E MMM d HH:mm:ss z yyyy", Locale.getDefault());
        Date date1 = Calendar.getInstance().getTime();
        Date date2 = Calendar.getInstance().getTime();
        try {
            date1 = format.parse(this.date);
            date2 = format.parse(stringDate2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date2.compareTo(date1);
    }

}
