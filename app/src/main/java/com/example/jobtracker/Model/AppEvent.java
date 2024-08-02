package com.example.jobtracker.Model;

import java.time.LocalDate;
import java.util.ArrayList;

public class AppEvent {
    private String title;
    private String description;
    private String appId;
    private LocalDate date = null; //The date the position opened

    public AppEvent() {
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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
