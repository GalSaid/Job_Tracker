package com.example.jobtracker.Model;

import java.util.HashMap;

public class Application {
    private String title;
    private boolean returned;
    private String status;
    private String userId;
    private String jobId;
    private String date; //The date the position opened
    private HashMap<String, AppEvent> allEvents = new HashMap<>();

    public Application() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public HashMap<String, AppEvent> getAllEvents() {
        return allEvents;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAllEvents(HashMap<String, AppEvent> allEvents) {
        this.allEvents = allEvents;
    }
}
