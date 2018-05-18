package com.dimazatolokin.films.model.models;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.dimazatolokin.films.Utils;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Film implements Parcelable {

    @SerializedName("title")
    private String title;
    @SerializedName("image")
    private String image;
    @SerializedName("rating")
    private double rating;
    @SerializedName("releaseYear")
    private int releaseYear;
    @SerializedName("genre")
    List<String> genres;

    public Film(String title, String image, double rating, int releaseYear, List<String> genres) {
        this.title = title;
        this.image = image;
        this.rating = rating;
        this.releaseYear = releaseYear;
        this.genres = genres;
    }

    public Film() {
    }

    public Film(Cursor cursor, int columnTitleIndex, int columnImageIndex, int columnRatingIndex, int columnReleaseYearIndex, int columnGenreIndex) {
        this(cursor.getString(columnTitleIndex),
                cursor.getString(columnImageIndex),
                cursor.getDouble(columnRatingIndex),
                cursor.getInt(columnReleaseYearIndex),
                Utils.genresFromStrToList(cursor.getString(columnGenreIndex)));
    }

    protected Film(Parcel in) {
        title = in.readString();
        image = in.readString();
        rating = in.readDouble();
        releaseYear = in.readInt();
        genres = in.createStringArrayList();
    }

    public static final Creator<Film> CREATOR = new Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getTitle());
        dest.writeString(getImage());
        dest.writeDouble(getRating());
        dest.writeInt(getReleaseYear());
        dest.writeList(getGenres());
    }
}
