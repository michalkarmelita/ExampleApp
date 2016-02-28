package com.michalkarmelita.testapp.db.methods;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.michalkarmelita.testapp.api.model.Comment;
import com.michalkarmelita.testapp.db.DbTableSqlBuilder;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class CommentsDbMethods {

    @Nonnull
    private final SQLiteDatabase sqLiteDatabase;

    @Inject
    public CommentsDbMethods(@Nonnull SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    public boolean saveAllComments(List<Comment> comments){
        boolean result = false;
        for (Comment comment : comments) {
            result = saveComment(comment);
        }
        return result;
    }
    public boolean saveComment(Comment commentToSave){

        final String commentId = String.valueOf(commentToSave.getId());
        final ContentValues contentValues = new ContentValues();
        contentValues.put(Meta.COLUMN_ID, commentId);
        contentValues.put(Meta.COLUMN_POST_ID, commentToSave.getPostId());
        contentValues.put(Meta.COLUMN_NAME, commentToSave.getName());
        contentValues.put(Meta.COLUMN_EMAIL, commentToSave.getEmail());
        contentValues.put(Meta.COLUMN_BODY, commentToSave.getBody());

        sqLiteDatabase.beginTransaction();

        int result = 0;

        try {

            if (idExists(commentId)){
                result = sqLiteDatabase.update(
                        Meta.TABLE,
                        contentValues,
                        Meta.COLUMN_ID + "=?",
                        new String[]{commentId});
            } else {
                result = (int) sqLiteDatabase.insert(Meta.TABLE, null, contentValues);
            }

            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }

        return result > 0;

    }

    public int queryForCommentCount(String postId){
        final Cursor cursor = sqLiteDatabase.query(
                Meta.TABLE,
                new String[]{Meta.COLUMN_POST_ID},
                Meta.COLUMN_POST_ID + "=?",
                new String[]{postId},
                null,
                null,
                null);

        try {

            return cursor.getCount();

        } finally {
            cursor.close();
        }
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
                throw new IllegalStateException("There is more than one comment with ID: " + id);
            }

            return cursor.getCount() > 0;

        } finally {
            cursor.close();
        }
    }

    public class Meta {
        public static final String TABLE = "comments";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_POST_ID = "post_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_BODY = "body";
    }

    public static String getCreateStatement(){
        return new DbTableSqlBuilder.TableBuilder(Meta.TABLE)
                .addColumn(Meta.COLUMN_ID, DbTableSqlBuilder.Type.INTEGER)
                .isPrimaryKey(true)
                .setNotNull(true)
                .buildColumn()
                .addColumn(Meta.COLUMN_POST_ID, DbTableSqlBuilder.Type.INTEGER)
                .setNotNull(true)
                .buildColumn()
                .addColumn(Meta.COLUMN_NAME, DbTableSqlBuilder.Type.TEXT)
                .buildColumn()
                .addColumn(Meta.COLUMN_EMAIL, DbTableSqlBuilder.Type.TEXT)
                .buildColumn()
                .addColumn(Meta.COLUMN_BODY, DbTableSqlBuilder.Type.TEXT)
                .buildColumn()
                .build();
    }
}
