package com.example.jobtracker;

import android.app.Application;

import com.example.jobtracker.Utilities.ImageLoader;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoader.init(this);
    }
}
