package com.michalkarmelita.testapp.db.operations;

import com.michalkarmelita.testapp.api.model.Comment;
import com.michalkarmelita.testapp.db.methods.CommentsDbMethods;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func0;

public class CommentsDbOperationsImpl implements CommentsDbOperations {

    @Nonnull
    private final CommentsDbMethods dbMethods;

    @Inject
    public CommentsDbOperationsImpl(@Nonnull CommentsDbMethods commentsDbMethods) {
        this.dbMethods = commentsDbMethods;
    }

    @Nonnull
    @Override
    public Observable<Boolean> saveComments(final List<Comment> comments) {
        return Observable.defer(new Func0<Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call() {
                return Observable.just(dbMethods.saveAllComments(comments));
            }
        });
    }

    @Nonnull
    @Override
    public Observable<Integer> getCommentsCountForPost(final String postId) {
        return Observable.defer(new Func0<Observable<Integer>>() {
            @Override
            public Observable<Integer> call() {
                return Observable.just(dbMethods.queryForCommentCount(postId));
            }
        });
    }
}
