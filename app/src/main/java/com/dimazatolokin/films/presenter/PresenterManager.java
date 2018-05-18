package com.dimazatolokin.films.presenter;

import com.dimazatolokin.films.FilmsApp;
import com.dimazatolokin.films.view.views.BaseView;
import com.dimazatolokin.films.view.views.FilmInfoView;
import com.dimazatolokin.films.view.views.MainView;

import java.util.ArrayList;
import java.util.List;

public class PresenterManager {

    private List<BasePresenter> presenters = new ArrayList<>();

    public PresenterManager() {
        FilmsApp.getMainComponent().inject(this);
    }

    private BasePresenter getSamePresenter(BaseView baseView) {
        for (BasePresenter presenter : presenters) {
            if (presenter.getTag().equals(baseView.getViewTag())) {
                presenter.attachView(baseView);
                return presenter;
            }
        }
        return null;
    }

    public MainPresenter getMainPresenter(MainView mainView) {
        BasePresenter presenter = getSamePresenter(mainView);
        if (presenter != null) {
            return (MainPresenter) presenter;
        }
        MainPresenter mainPresenter = new MainPresenter();
        mainPresenter.attachView(mainView);
        presenters.add(mainPresenter);
        return mainPresenter;
    }

    public FilmInfoPresenter getFilmInfoPresenter(FilmInfoView filmInfoView) {
        BasePresenter presenter = getSamePresenter(filmInfoView);
        if (presenter != null) {
            return (FilmInfoPresenter) presenter;
        }
        FilmInfoPresenter filmInfoPresenter = new FilmInfoPresenter();
        filmInfoPresenter.attachView(filmInfoView);
        presenters.add(filmInfoPresenter);
        return filmInfoPresenter;
    }
}
