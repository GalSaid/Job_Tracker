package com.example.jobtracker.Model;

import java.time.LocalDate;

public class Job{
    private String name;
    private String img_company;
    private String email;
    private boolean isActive;
    private String description;
    private String location;
    private LocalDate date = null; //The date the position opened

    public Job(){
    }

    public String getName() {
        return name;
    }

    public String getImg_company() {
        return img_company;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Job setName(String name) {
        this.name = name;
        return this;
    }

    public Job setImg_company(String img_company) {
        this.img_company = img_company;
        return this;
    }

    public Job setEmail(String email) {
        this.email = email;
        return this;
    }

    public Job setDescription(String description) {
        this.description = description;
        return this;
    }

    public Job setLocation(String location) {
        this.location = location;
        return this;
    }

    public Job setDate(LocalDate date) {
        this.date = date;
        return this;
    }
}
