package com.michalkarmelita.testapp.rx;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import javax.annotation.Nonnull;

import rx.functions.Action1;

public class MyViewActions {


    @Nonnull
    public static Action1<Object> showSnackbar(final @Nonnull View view,
                                               final @StringRes int text) {
        return showSnackbar(view, text, Snackbar.LENGTH_LONG);
    }

    @Nonnull
    public static Action1<Object> showSnackbar(final @Nonnull View view,
                                               final @StringRes int text,
                                               final int duration) {
        return new Action1<Object>() {
            @Override
            public void call(Object o) {
                Snackbar.make(view, text, duration).show();
            }
        };
    }

    @NonNull
    public static Action1<String> setToolbarTitle(final Toolbar toolbar) {
        return new Action1<String>() {
            @Override
            public void call(String title) {
                toolbar.setTitle(title);
            }
        };
    }

}
