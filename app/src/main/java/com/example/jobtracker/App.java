package com.example.jobtracker;

import android.app.Application;

import com.example.jobtracker.Utilities.ImageLoader;
import com.example.jobtracker.Utilities.MyDbManager;
import com.example.jobtracker.Utilities.StorageManager;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoader.init(this);
        MyDbManager.init(this);
        StorageManager.init(this);
    }
}
