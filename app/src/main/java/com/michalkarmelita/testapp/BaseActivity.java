package com.michalkarmelita.testapp;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.michalkarmelita.testapp.dagger.BaseActivityComponent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;


public abstract class BaseActivity extends RxAppCompatActivity implements BaseActivityComponentProvider {


    private BaseActivityComponent activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activityComponent = createActivityComponent(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @NonNull
    public BaseActivityComponent getActivityComponent() {
        return activityComponent;
    }

}
