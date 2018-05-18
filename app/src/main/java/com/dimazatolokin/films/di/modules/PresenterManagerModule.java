package com.dimazatolokin.films.di.modules;

import com.dimazatolokin.films.presenter.PresenterManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dimazatolokin on 18.05.18.
 */

@Module
public class PresenterManagerModule {

    @Singleton
    @Provides
    public static PresenterManager getPresenterManager() {
        return new PresenterManager();
    }
}
