package com.dimazatolokin.films.model.models;

import android.database.Cursor;

import com.dimazatolokin.films.Utils;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Film {

    @SerializedName("title")
    private String title;
    @SerializedName("image")
    private String image;
    @SerializedName("rating")
    private double rating;
    @SerializedName("releaseYear")
    private int releaseYear;
    @SerializedName("genre")
    private List<String> genres;
    private boolean isInBookmark;

    public Film(String title, String image, double rating, int releaseYear, List<String> genres, boolean isInBookmark) {
        this.title = title;
        this.image = image;
        this.rating = rating;
        this.releaseYear = releaseYear;
        this.genres = genres;
        this.isInBookmark = isInBookmark;
    }

    public Film() {
    }

    public Film(Cursor cursor, int columnTitleIndex, int columnImageIndex, int columnRatingIndex, int columnReleaseYearIndex, int columnGenreIndex, int columnInBookmarkIndex) {
        this(cursor.getString(columnTitleIndex),
                cursor.getString(columnImageIndex),
                cursor.getDouble(columnRatingIndex),
                cursor.getInt(columnReleaseYearIndex),
                Utils.genresFromStrToList(cursor.getString(columnGenreIndex)),
                cursor.getInt(columnInBookmarkIndex) == 1);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public boolean isInBookmark() {
        return isInBookmark;
    }

    public void setInBookmark(boolean inBookmark) {
        isInBookmark = inBookmark;
    }
}
