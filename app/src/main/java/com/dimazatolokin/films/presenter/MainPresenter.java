package com.dimazatolokin.films.presenter;

import android.content.Context;
import android.util.Log;

import com.dimazatolokin.films.FilmsApp;
import com.dimazatolokin.films.R;
import com.dimazatolokin.films.model.Repository;
import com.dimazatolokin.films.model.models.Film;
import com.dimazatolokin.films.model.net.NetworkService;
import com.dimazatolokin.films.view.views.BaseView;
import com.dimazatolokin.films.view.views.MainView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        if (films.size() == 0) {
            this.view.showProgress();
            repository.getNetworkService().getFilms(new GetFilmsCallbackImpl());
        } else {
            setAndDisplayItems(films);
        }
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

    private void setAndDisplayItems(List<Film> films) {
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

    public void filterItems(Filter filter, Context context) {
        switch (filter) {
            case RATING:
                Collections.sort(films, new Comparator<Film>() {
                    @Override
                    public int compare(Film o1, Film o2) {
                        return (int) (o1.getRating() * 100 - o2.getRating() * 100);
                    }
                });
                setAndDisplayItems(films);
                break;
            case RELEASE_YEAR:
                Collections.sort(films, new Comparator<Film>() {
                    @Override
                    public int compare(Film o1, Film o2) {
                        return o1.getReleaseYear() - o2.getReleaseYear();
                    }
                });
                setAndDisplayItems(films);
                break;
            case GENRE_ACTION:
                view.displayFilms(getFilmsInGenre(context.getString(R.string.genre_action)));
                break;
            case GENRE_CRIME:
                view.displayFilms(getFilmsInGenre(context.getString(R.string.genre_crime)));
                break;
            case GENRE_DRAMA:
                view.displayFilms(getFilmsInGenre(context.getString(R.string.genre_drama)));
                break;
            case GENRE_COMEDY:
                view.displayFilms(getFilmsInGenre(context.getString(R.string.genre_comedy)));
                break;
            case GENRE_FAMILY:
                view.displayFilms(getFilmsInGenre(context.getString(R.string.genre_family)));
                break;
            case GENRE_HORROR:
                view.displayFilms(getFilmsInGenre(context.getString(R.string.genre_horror)));
                break;
            case GENRE_SCI_FI:
                view.displayFilms(getFilmsInGenre(context.getString(R.string.genre_sci_fi)));
                break;
            case GENRE_FANTASY:
                view.displayFilms(getFilmsInGenre(context.getString(R.string.genre_fantasy)));
                break;
            case GENRE_HISTORY:
                view.displayFilms(getFilmsInGenre(context.getString(R.string.genre_history)));
                break;
            case GENRE_THRILLER:
                view.displayFilms(getFilmsInGenre(context.getString(R.string.genre_thriller)));
                break;
            case GENRE_ADVENTURE:
                view.displayFilms(getFilmsInGenre(context.getString(R.string.genre_adventure)));
                break;
            case GENRE_ANIMATION:
                view.displayFilms(getFilmsInGenre(context.getString(R.string.genre_animation)));
                break;
        }
    }

    private List<Film> getFilmsInGenre(String genre) {
        List<Film> filmsInGenre = new ArrayList<>();
        for (Film film : films) {
            if (film.getGenres().contains(genre)) {
                filmsInGenre.add(film);
            }
        }
        return filmsInGenre;
    }

    private class GetFilmsCallbackImpl implements NetworkService.GetFilmsCallback {

        @Override
        public void onSuccess(List<Film> films) {
            Log.d(TAG, "onSuccess: ");
            view.hideProgress();
            repository.getDaoSession().getFilmsDao().saveAll(films);
            setAndDisplayItems(films);
        }

        @Override
        public void onFailure(String message) {
            Log.d(TAG, "onFailure: ");
            view.hideProgress();
            setAndDisplayItems(null);
        }
    }

    public enum Filter {
        RATING,
        RELEASE_YEAR,
        GENRE_ACTION,
        GENRE_DRAMA,
        GENRE_SCI_FI,
        GENRE_THRILLER,
        GENRE_ADVENTURE,
        GENRE_HISTORY,
        GENRE_FANTASY,
        GENRE_ANIMATION,
        GENRE_COMEDY,
        GENRE_FAMILY,
        GENRE_HORROR,
        GENRE_CRIME
    }
}
