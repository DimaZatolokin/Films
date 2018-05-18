package com.dimazatolokin.films.di;

import android.app.Activity;

import com.dimazatolokin.films.di.modules.AppModule;
import com.dimazatolokin.films.di.modules.PresenterManagerModule;
import com.dimazatolokin.films.di.modules.RepositoryModule;
import com.dimazatolokin.films.model.Repository;
import com.dimazatolokin.films.presenter.MainPresenter;
import com.dimazatolokin.films.presenter.PresenterManager;
import com.dimazatolokin.films.view.activity.MainActivity;
import com.dimazatolokin.films.view.fragments.FilmInfoFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, RepositoryModule.class, PresenterManagerModule.class})
public interface MainComponent {

    void inject(Repository repository);
    void inject(MainPresenter mainPresenter);
    void inject(PresenterManager presenterManager);
    void inject(MainActivity mainActivity);
    void inject(FilmInfoFragment filmInfoFragment);

}
