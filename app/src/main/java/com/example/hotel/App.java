package com.example.hotel;

import android.app.Application;

import com.google.firebase.firestore.FirebaseFirestore;

public class App extends Application {
    private static App sInstance;

    public static synchronized App getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
