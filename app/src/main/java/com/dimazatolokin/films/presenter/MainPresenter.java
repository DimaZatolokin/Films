package com.dimazatolokin.films.presenter;

import android.util.Log;

import com.dimazatolokin.films.FilmsApp;
import com.dimazatolokin.films.model.Repository;
import com.dimazatolokin.films.model.models.Film;
import com.dimazatolokin.films.model.net.NetworkService;
import com.dimazatolokin.films.view.views.BaseView;
import com.dimazatolokin.films.view.views.MainView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

public class MainPresenter implements BasePresenter {

    @Inject
    Repository repository;

    private static final String TAG = MainPresenter.class.getSimpleName();
    private MainView view;
    private String viewTag;
    private List<Film> films = new ArrayList<>();

    public MainPresenter() {
        FilmsApp.getMainComponent().inject(this);
    }

    @Override
    public void attachView(BaseView view) {
        this.view = (MainView) view;
        viewTag = view.getViewTag();
        this.view.showProgress();
        repository.getNetworkService().getFilms(new GetFilmsCallbackImpl());
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public BaseView getView() {
        return view;
    }

    @Override
    public String getTag() {
        return viewTag;
    }

    public void refresh() {
        view.showProgress();
        repository.getNetworkService().getFilms(new GetFilmsCallbackImpl());
    }

    private void displayItems(List<Film> films) {
        List<Film> items;
        if (films == null || films.size() == 0) {
            items = getFilmsFromStorage();
        } else {
            items = films;
        }
        Set<String> bookmarksTitles = repository.getDaoSession().getFilmsDao().getBookmarksTitles();
        for (Film film : items) {
            film.setInBookmark(bookmarksTitles.contains(film.getTitle()));
        }
        view.displayFilms(items);
        this.films = items;
    }

    private List<Film> getFilmsFromStorage() {
        return repository.getDaoSession().getFilmsDao().getAllFilms();
    }

    public void itemClicked(int position) {
        view.openFilmInfo(films.get(position));
    }

    private class GetFilmsCallbackImpl implements NetworkService.GetFilmsCallback {

        @Override
        public void onSuccess(List<Film> films) {
            Log.i(TAG, "onSuccess: ");
            view.hideProgress();
            repository.getDaoSession().getFilmsDao().saveAll(films);
            displayItems(films);
        }

        @Override
        public void onFailure(String message) {
            Log.i(TAG, "onFailure: ");
            view.hideProgress();
            displayItems(null);
        }
    }
}
