package com.example.partyquick;

public class Party {

    private String title;
    private String location;
    private Date date;

    public Party() {
        title = "";
        location = "";
        date = new Date();
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public Date getDate() {
        return date;
    }

    public Party setTitle(String title) {
        this.title = title;
        return this;
    }

    public Party setLocation(String location) {
        this.location = location;
        return this;
    }

    public Party setDate(Date date) {
        this.date = new Date(date);
        return this;
    }
}
