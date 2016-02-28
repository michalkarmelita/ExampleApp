package com.michalkarmelita.testapp.db.methods;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.michalkarmelita.testapp.api.model.Post;
import com.michalkarmelita.testapp.db.DbTableSqlBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class PostsDbMethods {

    @Nonnull
    private final SQLiteDatabase sqLiteDatabase;

    @Inject
    public PostsDbMethods(@Nonnull SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    public boolean saveAllPosts(List<Post> posts){
        boolean result = false;
        for (Post post : posts) {
            result = savePost(post);
        }
        return result;
    }
    public boolean savePost(Post postToSave){

        final String postId = String.valueOf(postToSave.getId());
        final ContentValues contentValues = new ContentValues();
        contentValues.put(Meta.COLUMN_ID, postId);
        contentValues.put(Meta.COLUMN_USER_ID, postToSave.getUserId());
        contentValues.put(Meta.COLUMN_TITLE, postToSave.getTitle());
        contentValues.put(Meta.COLUMN_BODY, postToSave.getBody());

        sqLiteDatabase.beginTransaction();

        int result = 0;

        try {

            if (idExists(postId)){
                result = sqLiteDatabase.update(
                        Meta.TABLE,
                        contentValues,
                        Meta.COLUMN_ID + "=?",
                        new String[]{postId});
            } else {
                result = (int) sqLiteDatabase.insert(Meta.TABLE, null, contentValues);
            }

            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }

        return result > 0;

    }

    public List<Post> getAllPosts(){

        sqLiteDatabase.beginTransaction();

        final ArrayList<Post> posts = Lists.newArrayList();

        final Cursor cursor = sqLiteDatabase.query(
                Meta.TABLE,
                new String[]{Meta.COLUMN_USER_ID, Meta.COLUMN_ID, Meta.COLUMN_TITLE, Meta.COLUMN_BODY},
                null,
                null,
                null,
                null,
                null);

        try {

            while (cursor.moveToNext()){
                posts.add(getPostFromCursor(cursor));
            }

            return posts;

        } finally {
            cursor.close();
            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.endTransaction();
        }
    }

    public Optional<Post> getPost(String postId){

        sqLiteDatabase.beginTransaction();

        final Cursor cursor = sqLiteDatabase.query(
                Meta.TABLE,
                new String[]{Meta.COLUMN_USER_ID, Meta.COLUMN_ID, Meta.COLUMN_TITLE, Meta.COLUMN_BODY},
                Meta.COLUMN_ID + "=?",
                new String[]{postId},
                null,
                null,
                null);

        try {

            if (cursor.getCount() > 1) {
                throw new IllegalStateException("There is more than one post with ID: " + postId);
            } else if (!cursor.moveToFirst()) {
                return Optional.absent();
            } else {
                return Optional.of(getPostFromCursor(cursor));
            }

        } finally {
            cursor.close();
            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.endTransaction();
        }
    }

    @NonNull
    private Post getPostFromCursor(Cursor cursor) {
        return new Post(
            cursor.getInt(0),
            cursor.getInt(1),
            cursor.getString(2),
            cursor.getString(3)
    );
    }

    private Boolean idExists(String id) {

        final Cursor cursor = sqLiteDatabase.query(
                Meta.TABLE,
                new String[]{Meta.COLUMN_ID},
                Meta.COLUMN_ID + "=?",
                new String[]{id},
                null,
                null,
                null);

        try {

            if (cursor.getCount() > 1) {
                throw new IllegalStateException("There is more than one post with ID: " + id);
            }

            return cursor.getCount() > 0;

        } finally {
            cursor.close();
        }
    }

    public static class Meta {
        public static final String TABLE = "posts";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_BODY = "body";
    }

    @Nonnull
    public static String getCreateStatement(){
        return new DbTableSqlBuilder.TableBuilder(Meta.TABLE)
                .addColumn(Meta.COLUMN_ID, DbTableSqlBuilder.Type.INTEGER)
                .isPrimaryKey(true)
                .setNotNull(true)
                .buildColumn()
                .addColumn(Meta.COLUMN_USER_ID, DbTableSqlBuilder.Type.INTEGER)
                .buildColumn()
                .addColumn(Meta.COLUMN_TITLE, DbTableSqlBuilder.Type.TEXT)
                .buildColumn()
                .addColumn(Meta.COLUMN_BODY, DbTableSqlBuilder.Type.TEXT)
                .buildColumn()
                .build();
    }
}
