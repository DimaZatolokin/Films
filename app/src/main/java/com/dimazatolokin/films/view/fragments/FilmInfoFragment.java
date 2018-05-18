package com.dimazatolokin.films.view.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dimazatolokin.films.FilmsApp;
import com.dimazatolokin.films.R;
import com.dimazatolokin.films.Utils;
import com.dimazatolokin.films.model.models.Film;
import com.dimazatolokin.films.presenter.FilmInfoPresenter;
import com.dimazatolokin.films.presenter.PresenterManager;
import com.dimazatolokin.films.view.views.FilmInfoView;

import javax.inject.Inject;

public class FilmInfoFragment extends Fragment implements FilmInfoView{

    @Inject
    PresenterManager presenterManager;

    private static final String KEY_FILM = "key_film";

    private ImageView imgImage;
    private TextView tvTitle;
    private TextView tvGenre;
    private TextView tvRating;
    private TextView tvReleaseYear;
    private FilmInfoPresenter presenter;

    public static FilmInfoFragment getInstance(String title) {
        FilmInfoFragment fragment = new FilmInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_FILM, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_film_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FilmsApp.getMainComponent().inject(this);
        presenter = presenterManager.getFilmInfoPresenter(this);

        imgImage = view.findViewById(R.id.imgImage);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvGenre = view.findViewById(R.id.tvGenre);
        tvRating = view.findViewById(R.id.tvRating);
        tvReleaseYear = view.findViewById(R.id.tvReleaseYear);

        String title = getArguments().getString(KEY_FILM);
        presenter.setFilmId(title);

        Film film = presenter.getFilm();
        if (film != null) {
            setViewFilds(film);
        }
    }

    private void setViewFilds(Film film) {
        Glide.with(this)
                .load(film.getImage())
                .into(imgImage);
        tvTitle.setText(film.getTitle());
        tvGenre.setText(Utils.genresFromListToStr(film.getGenres()));
        tvRating.setText(String.valueOf(film.getRating()));
        tvReleaseYear.setText(String.valueOf(film.getReleaseYear()));
    }

    @Override
    public String getViewTag() {
        return FilmInfoFragment.class.getSimpleName();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        presenter.detachView();
    }
}
