package com.michalkarmelita.testapp.dagger;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.appunite.rx.android.BuildConfig;
import com.google.gson.Gson;
import com.michalkarmelita.testapp.App;
import com.michalkarmelita.testapp.api.ApiConstants;
import com.michalkarmelita.testapp.api.ApiService;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

@Module
public final class AppModule {

    private static final String TAG = AppModule.class.getCanonicalName();

    private final App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return app;
    }

    @Provides
    @Singleton
    @ForApplication
    public Context activityContext() {
        return app.getApplicationContext();
    }

    @Provides
    @Singleton
    @ForApplication
    public Resources provideResources() {
        return app.getResources();
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(@ForApplication Context context) {
        return new OkHttpClient();
    }

    @Provides
    @Singleton
    Picasso providePicasso(@ForApplication Context context, OkHttpClient okHttpClient) {
        return new Picasso.Builder(context)
                .downloader(new OkHttpDownloader(okHttpClient))
                .build();
    }

    @Provides
    @Singleton
    RestAdapter provideRestAdapterBuilder(Gson gson,
                                          OkHttpClient client) {
        return new RestAdapter.Builder()
                .setClient(new OkClient(client))
                .setLogLevel(
                        BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .setConverter(new GsonConverter(gson))
                .setEndpoint(ApiConstants.API_ENDPOINT)
                .build();
    }

    @Provides
    @Singleton
    public ApiService provideApiService(final RestAdapter adapter) {
        return adapter.create(ApiService.class);
    }

}
