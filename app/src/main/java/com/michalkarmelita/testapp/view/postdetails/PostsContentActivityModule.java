package com.michalkarmelita.testapp.view.postdetails;

import javax.annotation.Nonnull;
import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class PostsContentActivityModule {


    private final int postId;

    public PostsContentActivityModule(int postId) {
        this.postId = postId;
    }

    @Provides
    @Named("PostId")
    @Nonnull
    int providePostId(){
        return postId;
    }
}
