package com.dimazatolokin.films.view.activity;

import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.dimazatolokin.films.FilmsApp;
import com.dimazatolokin.films.R;
import com.dimazatolokin.films.model.models.Film;
import com.dimazatolokin.films.presenter.MainPresenter;
import com.dimazatolokin.films.presenter.PresenterManager;
import com.dimazatolokin.films.view.FilmsAdapter;
import com.dimazatolokin.films.view.views.MainView;
import com.dimazatolokin.films.view.fragments.FilmInfoFragment;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainView {

    @Inject
    PresenterManager presenterManager;

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String POSITION = "position";
    private RecyclerView recycler;
    private FilmsAdapter adapter;
    private MainPresenter presenter;
    private SwipeRefreshLayout refreshLayout;
    private ProgressBar progressBar;
    private Parcelable savedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FilmsApp.getMainComponent().inject(this);

        recycler = findViewById(R.id.recyclerMain);
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshing(false);
        progressBar = findViewById(R.id.progressBar);
        adapter = new FilmsAdapter(this);
        presenter = presenterManager.getMainPresenter(this);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter.setClickListener(new FilmsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                presenter.itemClicked(position);
            }
        });
        recycler.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
                presenter.refresh();
            }
        });
        if (savedInstanceState != null) {
            savedPosition = savedInstanceState.getParcelable(POSITION);
        }
    }

    @Override    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.showFilms:
                adapter.clear();
                presenter.refresh();
                break;
            case R.id.byRating:
                presenter.filterItems(MainPresenter.Filter.RATING, this);
                break;
            case R.id.byReleaseYear:
                presenter.filterItems(MainPresenter.Filter.RELEASE_YEAR, this);
                break;
            case R.id.genre_action:
                presenter.filterItems(MainPresenter.Filter.GENRE_ACTION, this);
                break;
            case R.id.genre_adventure:
                presenter.filterItems(MainPresenter.Filter.GENRE_ADVENTURE, this);
                break;
            case R.id.genre_animation:
                presenter.filterItems(MainPresenter.Filter.GENRE_ANIMATION, this);
                break;
            case R.id.genre_comedy:
                presenter.filterItems(MainPresenter.Filter.GENRE_COMEDY, this);
                break;
            case R.id.genre_crime:
                presenter.filterItems(MainPresenter.Filter.GENRE_CRIME, this);
                break;
            case R.id.genre_drama:
                presenter.filterItems(MainPresenter.Filter.GENRE_DRAMA, this);
                break;
            case R.id.genre_family:
                presenter.filterItems(MainPresenter.Filter.GENRE_FAMILY, this);
                break;
            case R.id.genre_fantasy:
                presenter.filterItems(MainPresenter.Filter.GENRE_FANTASY, this);
                break;
            case R.id.genre_history:
                presenter.filterItems(MainPresenter.Filter.GENRE_HISTORY, this);
                break;
            case R.id.genre_horror:
                presenter.filterItems(MainPresenter.Filter.GENRE_HORROR, this);
                break;
            case R.id.genre_sci_fi:
                presenter.filterItems(MainPresenter.Filter.GENRE_SCI_FI, this);
                break;
            case R.id.genre_thriller:
                presenter.filterItems(MainPresenter.Filter.GENRE_THRILLER, this);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(int stringRes) {
        Snackbar.make(findViewById(android.R.id.content), stringRes, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void displayFilms(List<Film> films) {
        adapter.setItems(films);
        if (savedPosition != null) {
            recycler.getLayoutManager().onRestoreInstanceState(savedPosition);
            savedPosition = null;
        }
    }

    @Override
    public void openFilmInfo(Film film) {
        getFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(android.R.id.content, FilmInfoFragment.getInstance(film.getTitle()))
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Parcelable onSaveInstanceState = recycler.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(POSITION, onSaveInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.detachView();
    }

    @Override
    public String getViewTag() {
        return MainActivity.class.getSimpleName();
    }

    @Override
    public void onBackPressed() {
        int backStackEntryCount = getFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount > 0) {
            presenter.refresh();
        }
        super.onBackPressed();
    }
}
