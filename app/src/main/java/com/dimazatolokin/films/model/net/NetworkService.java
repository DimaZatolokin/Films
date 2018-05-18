package com.dimazatolokin.films.model.net;

import android.util.Log;

import com.dimazatolokin.films.model.models.Film;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static final String TAG = NetworkService.class.getSimpleName();
    public static final String BASE_URL = "https://api.androidhive.info/json/";

    private RequestApi api;

    public NetworkService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(RequestApi.class);
    }
    
    public void getFilms(final GetFilmsCallback callback) {
        Call<List<Film>> films = api.getFilms();
        films.enqueue(new Callback<List<Film>>() {
            @Override
            public void onResponse(Call<List<Film>> call, Response<List<Film>> response) {
                Log.i(TAG, "onResponse: ");
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Film>> call, Throwable t) {
                Log.i(TAG, "onFailure: ");
                callback.onFailure(t.getMessage());
            }
        });
    }

    public interface GetFilmsCallback {

        void onSuccess(List<Film> films);

        void onFailure(String message);
    }

}
