package com.example.desafio_android_alberto_junior;

import android.app.Application;

import com.example.desafio_android_alberto_junior.di.AppComponent;
import com.example.desafio_android_alberto_junior.di.DaggerAppComponent;

public class MainApplication extends Application {
    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder().build();
    }

    public static AppComponent getComponent() {
        return component;
    }
}
