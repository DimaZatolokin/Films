package com.dimazatolokin.films.view.views;

import com.dimazatolokin.films.model.models.Film;

import java.util.List;

public interface MainView extends LoadingView, MessageView, BaseView {

    void displayFilms(List<Film> films);

    void openFilmInfo(Film film);
}
