package com.michalkarmelita.testapp.dagger;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.michalkarmelita.testapp.App;
import com.michalkarmelita.testapp.api.ApiService;
import com.michalkarmelita.testapp.db.DbModule;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Component;
import rx.Scheduler;

@Singleton
@Component(
        modules = {
                AppModule.class,
                BaseModule.class,
                DbModule.class
        }
)
public interface AppComponent {

    void inject(App app);

    Application provideApplication();

    ApiService getWeatherApiService();

    @UiScheduler
    Scheduler getUiScheduler();

    @NetworkScheduler
    Scheduler getNetworkScheduler();

    @ForApplication
    Context getContext();

    @ForApplication
    Resources getResources();

    Picasso getPicasso();

    Gson getGson();

    SQLiteDatabase getSqLiteDatabase();

}