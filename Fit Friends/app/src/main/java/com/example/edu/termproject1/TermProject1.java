package com.example.edu.termproject1;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class TermProject1 extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //This is a class made to enable firebase's offline capabilities
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
