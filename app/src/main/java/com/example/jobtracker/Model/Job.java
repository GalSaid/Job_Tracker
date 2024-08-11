package com.example.jobtracker.Model;

public class Job{
    private String id;
    private String name;
    private String companyImg="";
    private String companyName;
    private String email;
    private boolean isActive;
    private boolean isFullTime;
    private String description;
    private String aboutAS;//About the company
    private String requirements;
    private String location;
    private String date; //The date the position opened

    public Job(){
    }

    public Job(String name, String companyImg, String companyName, String email, boolean isActive, boolean isFullTime, String description, String aboutAS, String requirements, String location, String date) {
        this.name = name;
        this.companyImg = companyImg;
        this.companyName = companyName;
        this.email = email;
        this.isActive = isActive;
        this.isFullTime = isFullTime;
        this.description = description;
        this.aboutAS = aboutAS;
        this.requirements = requirements;
        this.location = location;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getCompanyImg() {
        return companyImg;
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

    public String getDate() {
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

    public Job setCompanyImg(String companyImg) {
        this.companyImg = companyImg;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public boolean isFullTime() {
        return isFullTime;
    }

    public void setFullTime(boolean fullTime) {
        isFullTime = fullTime;
    }

    public String getAboutAS() {
        return aboutAS;
    }

    public void setAboutAS(String aboutAS) {
        this.aboutAS = aboutAS;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public Job setDate(String date) {
        this.date = date;
        return this;
    }

    public String getId() {
        return id;
    }

    public Job setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", companyImg='" + companyImg + '\'' +
                ", companyName='" + companyName + '\'' +
                ", email='" + email + '\'' +
                ", isActive=" + isActive +
                ", isFullTime=" + isFullTime +
                ", description='" + description + '\'' +
                ", aboutAS='" + aboutAS + '\'' +
                ", requirements='" + requirements + '\'' +
                ", location='" + location + '\'' +
                ", date=" + date +
                '}';
    }
}
