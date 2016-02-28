package com.michalkarmelita.testapp.db.operations;

import javax.annotation.Nonnull;

import dagger.Module;
import dagger.Provides;

@Module
public class DbOperationsModule {

    @Provides
    @Nonnull
    PostsDbOperations providePostsDbOperations(PostDbOperationsImpl impl){
        return impl;
    }

    @Provides
    @Nonnull
    UsersDbOperations provideUsersDbOperations(UsersDbOperationsImpl impl){
        return impl;
    }

    @Provides
    @Nonnull
    CommentsDbOperations provideCommentsDbOperations(CommentsDbOperationsImpl impl){
        return impl;
    }
}
