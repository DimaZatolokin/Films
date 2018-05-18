package com.dimazatolokin.films.model;

import android.content.Context;

import com.dimazatolokin.films.FilmsApp;
import com.dimazatolokin.films.model.net.NetworkService;
import com.dimazatolokin.films.model.sqlite.DaoSession;

import dagger.Module;
import dagger.Provides;

public class Repository {

    private DaoSession daoSession;
    private NetworkService networkService;

    public Repository(Context context) {
        FilmsApp.getMainComponent().inject(this);
        daoSession = new DaoSession(context);
        networkService = new NetworkService();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public NetworkService getNetworkService() {
        return networkService;
    }
}
