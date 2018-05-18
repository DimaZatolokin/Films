package com.dimazatolokin.films.model.net;

import com.dimazatolokin.films.model.models.Film;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestApi {

    @GET("movies.json")
    Call<List<Film>> getFilms();
}
