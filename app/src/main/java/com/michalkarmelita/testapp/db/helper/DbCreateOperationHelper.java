package com.michalkarmelita.testapp.db.helper;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.michalkarmelita.testapp.db.methods.CommentsDbMethods;
import com.michalkarmelita.testapp.db.methods.PostsDbMethods;
import com.michalkarmelita.testapp.db.methods.UsersDbMethods;

import javax.annotation.Nonnull;

public class DbCreateOperationHelper {

    public static void createDb(@Nonnull SQLiteDatabase db) {
        db.execSQL(PostsDbMethods.getCreateStatement());
        db.execSQL(UsersDbMethods.getCreateStatement());
        db.execSQL(CommentsDbMethods.getCreateStatement());
    }

    private static void dropDb(@NonNull SQLiteDatabase db) {
        dropTable(db, PostsDbMethods.Meta.TABLE);
        dropTable(db, UsersDbMethods.Meta.TABLE);
        dropTable(db, CommentsDbMethods.Meta.TABLE);
    }

    public static void recreateDb(@NonNull SQLiteDatabase db) {
        dropDb(db);
        createDb(db);
    }

    private static void dropTable(SQLiteDatabase db, String tableName) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
    }
}
