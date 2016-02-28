package com.michalkarmelita.testapp.db.operations;

import com.google.common.base.Optional;
import com.michalkarmelita.testapp.api.model.User;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Named;

import rx.Observable;

public interface UsersDbOperations {

    @Nonnull
    Observable<Boolean> saveUsers(List<User> users);

    @Nonnull
    Observable<Optional<String>> getUserNameForId(String userId);

    @Named
    Observable<Optional<String>> getEmailForUserId(String userId);
}
