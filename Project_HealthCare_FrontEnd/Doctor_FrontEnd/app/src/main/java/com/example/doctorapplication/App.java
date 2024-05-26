package com.example.doctorapplication;
import android.app.Application;
import android.content.Context;

import com.example.doctorapplication.utils.PreferenceUtils;


public class App extends Application {
    private static App instance = null;

    public static Context getInstance() {
        return instance;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        getInstance(this);
        PreferenceUtils.init();
    }

    public static synchronized void getInstance(App app) {
        if (instance == null) instance = app;
    }
}
