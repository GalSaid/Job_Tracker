package com.example.jobtracker.Interfaces;


import com.example.jobtracker.Model.AppEvent;
import com.example.jobtracker.Model.Application;

public interface ApplicationCallback {
    void addEvent(Application app, int position, AppEvent even);
    void updateReturnStatus(boolean isChecked,Application app,int position);

}
