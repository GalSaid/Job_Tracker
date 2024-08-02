package com.example.jobtracker.Model;

import com.example.jobtracker.Utilities.Status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Application {
    private String title;
    private boolean returned;
    private Status status;
    private String userId;
    private String jobId;
    private HashMap<String, AppEvent> allEvents = new HashMap<>();

    public Application() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
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

    public void setAllEvents(HashMap<String, AppEvent> allEvents) {
        this.allEvents = allEvents;
    }
}
