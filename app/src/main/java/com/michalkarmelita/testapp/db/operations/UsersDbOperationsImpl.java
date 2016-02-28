package com.michalkarmelita.testapp.db.operations;

import com.google.common.base.Optional;
import com.michalkarmelita.testapp.api.model.User;
import com.michalkarmelita.testapp.db.methods.UsersDbMethods;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func0;

public class UsersDbOperationsImpl implements UsersDbOperations{

    @Nonnull
    private final UsersDbMethods dbMethods;

    @Inject
    public UsersDbOperationsImpl(@Nonnull UsersDbMethods usersDbMethods) {
        this.dbMethods = usersDbMethods;
    }

    @Nonnull
    @Override
    public Observable<Boolean> saveUsers(final List<User> users) {
        return Observable.defer(new Func0<Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call() {
                return Observable.just(dbMethods.saveAllUsers(users));
            }
        });
    }

    @Nonnull
    @Override
    public Observable<Optional<String>> getUserNameForId(final String userId) {
        return Observable.defer(new Func0<Observable<Optional<String>>>() {
            @Override
            public Observable<Optional<String>> call() {
                return Observable.just(dbMethods.queryForUserName(userId));
            }
        });
    }

    @Override
    public Observable<Optional<String>> getEmailForUserId(final String userId) {
        return Observable.defer(new Func0<Observable<Optional<String>>>() {
            @Override
            public Observable<Optional<String>> call() {
                return Observable.just(dbMethods.queryForUserEmail(userId));
            }
        });
    }
}
