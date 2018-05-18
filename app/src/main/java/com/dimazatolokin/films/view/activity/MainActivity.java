package com.dimazatolokin.films.view.activity;

import android.os.Parcelable;
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
        presenter = presenterManager.getMainPresenter(this);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FilmsAdapter(this, new FilmsAdapter.OnItemClickListener() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.showFilms:
                presenter.refresh();
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
                .add(android.R.id.content, FilmInfoFragment.getInstance(film))
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
}
