package com.michalkarmelita.testapp;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.michalkarmelita.testapp.dagger.AppComponent;
import com.michalkarmelita.testapp.dagger.AppModule;
import com.michalkarmelita.testapp.dagger.BaseModule;
import com.michalkarmelita.testapp.dagger.DaggerAppComponent;


public class App extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());

        setupGraph();
    }

    private void setupGraph() {
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .baseModule(new BaseModule())
                .build();
        component.inject(this);
    }

    public static AppComponent getAppComponent(Application app) {
        return ((App) app).component;
    }

}
