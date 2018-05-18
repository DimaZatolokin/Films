package com.dimazatolokin.films.presenter;

import com.dimazatolokin.films.model.models.Film;
import com.dimazatolokin.films.view.views.BaseView;
import com.dimazatolokin.films.view.views.FilmInfoView;

public class FilmInfoPresenter implements BasePresenter {

    private FilmInfoView view;
    private String viewTag;
    private Film film;

    @Override
    public void attachView(BaseView view) {
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

    public void setFilm(Film film) {
        this.film = film;
    }
}
