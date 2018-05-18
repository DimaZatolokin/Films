package com.dimazatolokin.films;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<String> genresFromStrToList(String genreStr) {
        if (TextUtils.isEmpty(genreStr)) {
            return new ArrayList<>();
        }
        List<String> genres = new ArrayList<>();
        String[] genresArray = genreStr.split(",");
        for (String genre : genresArray) {
            genres.add(genre);
        }
        return genres;
    }

    public static String genresFromListToStr(List<String> genresList) {
        StringBuilder sb = new StringBuilder();
        for (String genre : genresList) {
            sb.append(genre).append(",");
        }
        if (sb.length() > 0) {
            return sb.toString().substring(0, sb.length() - 1);
        } else {
            return "";
        }
    }
}
