package com.dimazatolokin.films.view.views;

import android.support.annotation.StringRes;

public interface MessageView {

    void showMessage(String message);

    void showMessage(@StringRes int stringRes);
}
