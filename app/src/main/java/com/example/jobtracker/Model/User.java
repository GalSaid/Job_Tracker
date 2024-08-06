package com.example.jobtracker.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class User {

    private String name;
    private String email;
    private String phoneNumber;
    private String description;
    private String pdfCV;
    private String wordCV;
    private HashMap<String, Application> myApplications = new HashMap<>();

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
}
