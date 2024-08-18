package com.example.jobtracker.Interfaces;


import com.example.jobtracker.Model.AppEvent;


public interface EventCallback {
    void editEvent(AppEvent event, int position);
}
