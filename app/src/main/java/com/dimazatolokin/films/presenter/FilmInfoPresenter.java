package com.dimazatolokin.films.presenter;

import com.dimazatolokin.films.FilmsApp;
import com.dimazatolokin.films.model.Repository;
import com.dimazatolokin.films.model.models.Film;
import com.dimazatolokin.films.view.views.BaseView;
import com.dimazatolokin.films.view.views.FilmInfoView;

import javax.inject.Inject;

public class FilmInfoPresenter implements BasePresenter {

    @Inject
    Repository repository;

    private FilmInfoView view;
    private String viewTag;
    private Film film;

    @Override
    public void attachView(BaseView view) {
        FilmsApp.getMainComponent().inject(this);
        this.view = (FilmInfoView) view;
        this.viewTag = view.getViewTag();
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public BaseView getView() {
        return view;
    }

    @Override
    public String getTag() {
        return viewTag;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilmId(String title) {
        Film film = repository.getDaoSession().getFilmsDao().getFilm(title);
        this.film = film;
    }
}
