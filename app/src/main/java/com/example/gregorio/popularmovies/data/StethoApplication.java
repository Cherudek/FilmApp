package com.example.gregorio.popularmovies.data;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Gregorio on 09/10/2017.
 */

public class StethoApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
