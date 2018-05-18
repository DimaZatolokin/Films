package com.dimazatolokin.films.di.modules;

import android.content.Context;

import com.dimazatolokin.films.model.Repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    public static Repository getRepository(Context context) {
        return new Repository(context);
    }
}
