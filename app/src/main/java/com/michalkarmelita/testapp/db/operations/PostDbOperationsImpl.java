package com.michalkarmelita.testapp.db.operations;

import com.google.common.base.Optional;
import com.michalkarmelita.testapp.api.model.Post;
import com.michalkarmelita.testapp.db.methods.PostsDbMethods;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func0;

public class PostDbOperationsImpl implements PostsDbOperations {

    @Nonnull
    PostsDbMethods postsDbMethods;

    @Inject
    public PostDbOperationsImpl(@Nonnull PostsDbMethods postsDbMethods) {
        this.postsDbMethods = postsDbMethods;
    }

    @Nonnull
    @Override
    public Observable<Boolean> savePosts(final List<Post> postsResponse) {
        return Observable.defer(new Func0<Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call() {
                return Observable.just(postsDbMethods.saveAllPosts(postsResponse));
            }
        });
    }

    @Nonnull
    @Override
    public Observable<List<Post>> getAllPosts() {
        return Observable.defer(new Func0<Observable<List<Post>>>() {
            @Override
            public Observable<List<Post>> call() {
                return Observable.just(postsDbMethods.getAllPosts());
            }
        });
    }

    @Nonnull
    @Override
    public Observable<Optional<Post>> getPost(final String postId) {
        return Observable.defer(new Func0<Observable<Optional<Post>>>() {
            @Override
            public Observable<Optional<Post>> call() {
                return Observable.just(postsDbMethods.getPost(postId));
            }
        });
    }
}
