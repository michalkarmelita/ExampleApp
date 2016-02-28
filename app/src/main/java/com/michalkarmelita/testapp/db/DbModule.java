package com.michalkarmelita.testapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.michalkarmelita.testapp.dagger.ForApplication;
import com.michalkarmelita.testapp.db.helper.DbOpenHelper;

import javax.annotation.Nonnull;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

    @Provides
    @Nonnull
    @Singleton
    public SQLiteDatabase provideSqLiteDb(@Nonnull @ForApplication Context context){
        return new DbOpenHelper(context)
                .getWritableDatabase();
    }

}
