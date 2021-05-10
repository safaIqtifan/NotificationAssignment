package com.example.notificationassignment;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

public class RootApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AndroidNetworking.initialize(getApplicationContext());

    }
}
