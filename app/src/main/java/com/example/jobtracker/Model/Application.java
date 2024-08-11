package com.example.jobtracker.Model;


import android.content.res.Resources;

import com.example.jobtracker.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Application {
    private boolean returned; //if they got back to me after sending a CV
    private String status;
    private String userId;
    private String jobId;
    private String date; //the date I applied for the job
    private HashMap<String, AppEvent> allEvents = new HashMap<>();

    public Application() {
    }

    public Application(String userId, String jobId, String status) {
        this.userId = userId;
        this.jobId = jobId;
        this.date = new SimpleDateFormat("dd/MM/yyyy", Locale.UK).format(new Date());
        this.returned = false;
        this.status = status;
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
