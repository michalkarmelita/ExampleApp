package com.michalkarmelita.testapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.michalkarmelita.testapp.dagger.BaseActivityComponent;

public interface BaseActivityComponentProvider {

    @NonNull
    BaseActivityComponent createActivityComponent(@Nullable Bundle savedInstanceState);

}
