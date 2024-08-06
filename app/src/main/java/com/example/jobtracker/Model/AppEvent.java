package com.example.jobtracker.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class AppEvent {
    private String id;
    private String title;
    private String description;
    private String date; //The date of the event

    public AppEvent() {
    }

    public AppEvent(String date, String description, String title) {
        this.date = date;
        this.description = description;
        this.title = title;
        this.id = UUID.randomUUID().toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
