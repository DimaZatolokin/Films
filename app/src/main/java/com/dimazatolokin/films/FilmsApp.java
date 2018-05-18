package com.dimazatolokin.films;

import android.app.Application;

import com.dimazatolokin.films.di.DaggerMainComponent;
import com.dimazatolokin.films.di.MainComponent;
import com.dimazatolokin.films.di.modules.AppModule;
import com.dimazatolokin.films.presenter.PresenterManager;

public class FilmsApp extends Application {

    private static MainComponent mainComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mainComponent = DaggerMainComponent.builder().appModule(new AppModule(this)).build();
    }

    public static MainComponent getMainComponent() {
        return mainComponent;
    }
}
