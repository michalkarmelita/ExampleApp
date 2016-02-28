package com.michalkarmelita.testapp.db.methods;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.common.base.Optional;
import com.google.gson.Gson;
import com.michalkarmelita.testapp.api.model.User;
import com.michalkarmelita.testapp.db.DbTableSqlBuilder;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class UsersDbMethods {

    @Nonnull
    private final SQLiteDatabase sqLiteDatabase;
    @Nonnull
    private final Gson gson;

    @Inject
    public UsersDbMethods(@Nonnull SQLiteDatabase sqLiteDatabase,
                          @Nonnull Gson gson) {
        this.sqLiteDatabase = sqLiteDatabase;
        this.gson = gson;
    }

    public boolean saveAllUsers(List<User> users){
        boolean result = false;
        for (User user : users) {
            result = saveUser(user);
        }
        return result;
    }
    public boolean saveUser(User userToSave){

        final String userId = String.valueOf(userToSave.getId());
        final ContentValues contentValues = new ContentValues();
        contentValues.put(Meta.COLUMN_USER_ID, userId);
        contentValues.put(Meta.COLUMN_NAME, userToSave.getName());
        contentValues.put(Meta.COLUMN_USER_NAME, userToSave.getUsername());
        contentValues.put(Meta.COLUMN_PHONE, userToSave.getPhone());
        contentValues.put(Meta.COLUMN_EMAIL, userToSave.getEmail());
        contentValues.put(Meta.COLUMN_WEBSITE, userToSave.getWebsite());
        contentValues.put(Meta.COLUMN_ADDRESS, convertToJson(userToSave.getAddress()));
        contentValues.put(Meta.COLUMN_COMPANY, convertToJson(userToSave.getCompany()));

        sqLiteDatabase.beginTransaction();

        int result = 0;

        try {

            if (idExists(userId)){
                result = sqLiteDatabase.update(
                        Meta.TABLE,
                        contentValues,
                        Meta.COLUMN_USER_ID + "=?",
                        new String[]{userId});
            } else {
                result = (int) sqLiteDatabase.insert(Meta.TABLE, null, contentValues);
            }

            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }

        return result > 0;

    }

    public Optional<String> queryForUserName(String userId){
        return queryForUserDetail(userId, Meta.COLUMN_USER_NAME);
    }

    public Optional<String> queryForUserEmail(String userId) {
        return queryForUserDetail(userId, Meta.COLUMN_EMAIL);
    }

    private Optional<String> queryForUserDetail(String userId, String column) {
        sqLiteDatabase.beginTransaction();
        final Cursor cursor = sqLiteDatabase.query(
                Meta.TABLE,
                new String[]{column},
                Meta.COLUMN_USER_ID + "=?",
                new String[]{userId},
                null,
                null,
                null);

        try {

            if (cursor.getCount() > 1) {
                throw new IllegalStateException("There is more than one user with ID: " + userId);
            } else if (!cursor.moveToFirst()) {
                return Optional.absent();
            } else {
                return Optional.of(cursor.getString(0));
            }

        } finally {
            cursor.close();
            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.endTransaction();
        }
    }

    private Boolean idExists(String id) {

        final Cursor cursor = sqLiteDatabase.query(
                Meta.TABLE,
                new String[]{Meta.COLUMN_USER_ID},
                Meta.COLUMN_USER_ID + "=?",
                new String[]{id},
                null,
                null,
                null);

        try {

            if (cursor.getCount() > 1) {
                throw new IllegalStateException("There is more than one user with ID: " + id);
            }

            return cursor.getCount() > 0;

        } finally {
            cursor.close();
        }
    }

    private String convertToJson(Object o) {
        return gson.toJson(o);
    }

    private <T> T getObjectFromJson(String josn, Class<T> type) {
        return gson.fromJson(josn, type);
    }

    public class Meta {
        public static final String TABLE = "users";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_USER_NAME = "user_name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_WEBSITE = "web";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_COMPANY = "company";
    }

    public static String getCreateStatement(){
        return new DbTableSqlBuilder.TableBuilder(Meta.TABLE)
                .addColumn(Meta.COLUMN_USER_ID, DbTableSqlBuilder.Type.INTEGER)
                .isPrimaryKey(true)
                .setNotNull(true)
                .buildColumn()
                .addColumn(Meta.COLUMN_NAME, DbTableSqlBuilder.Type.TEXT)
                .buildColumn()
                .addColumn(Meta.COLUMN_USER_NAME, DbTableSqlBuilder.Type.TEXT)
                .buildColumn()
                .addColumn(Meta.COLUMN_EMAIL, DbTableSqlBuilder.Type.TEXT)
                .buildColumn()
                .addColumn(Meta.COLUMN_PHONE, DbTableSqlBuilder.Type.TEXT)
                .buildColumn()
                .addColumn(Meta.COLUMN_WEBSITE, DbTableSqlBuilder.Type.TEXT)
                .buildColumn()
                .addColumn(Meta.COLUMN_ADDRESS, DbTableSqlBuilder.Type.TEXT)
                .buildColumn()
                .addColumn(Meta.COLUMN_COMPANY, DbTableSqlBuilder.Type.TEXT)
                .buildColumn()
                .build();
    }
}
