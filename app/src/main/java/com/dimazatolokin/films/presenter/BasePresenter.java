package com.dimazatolokin.films.presenter;

import com.dimazatolokin.films.view.views.BaseView;
import com.dimazatolokin.films.view.views.MainView;

public interface BasePresenter {

    void attachView(BaseView view);

    void detachView();

    BaseView getView();

    String getTag();
}
