package com.example.jobtracker.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class User {

    private String name;
    private String email;
    private String phoneNumber;
    private String description;
    private String pdfCV; //the url string in firebase storage of the pdf file
    private String wordCV;//the url string in firebase storage of the word file
    private HashMap<String, Application> myApplications = new HashMap<>();
    private int totalPending = 0; //for analysis
    private int totalAccepted = 0;//for analysis
    private int totalRejected = 0;//for analysis
    private int totalInProcess = 0;//for analysis

    public User() {}

    public User(String name, String email, String phoneNumber, String description) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.description = description;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HashMap<String, Application> getMyApplications() {
        return myApplications;
    }

    public String getPdfCV() {
        return pdfCV;
    }

    public void setPdfCV(String pdfCV) {
        this.pdfCV = pdfCV;
    }

    public String getWordCV() {
        return wordCV;
    }

    public void setWordCV(String wordCV) {
        this.wordCV = wordCV;
    }

    public void setMyApplications(HashMap<String, Application> myApplications) {
        this.myApplications = myApplications;
    }

    public int getTotalPending() {
        return totalPending;
    }

    public void setTotalPending(int totalPending) {
        this.totalPending = totalPending;
    }

    public int getTotalAccepted() {
        return totalAccepted;
    }

    public void setTotalAccepted(int totalAccepted) {
        this.totalAccepted = totalAccepted;
    }

    public int getTotalRejected() {
        return totalRejected;
    }

    public void setTotalRejected(int totalRejected) {
        this.totalRejected = totalRejected;
    }

    public int getTotalInProcess() {
        return totalInProcess;
    }

    public void setTotalInProcess(int totalInProcess) {
        this.totalInProcess = totalInProcess;
    }
}
