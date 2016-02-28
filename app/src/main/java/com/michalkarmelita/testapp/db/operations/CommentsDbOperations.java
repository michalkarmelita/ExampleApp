package com.michalkarmelita.testapp.db.operations;

import com.michalkarmelita.testapp.api.model.Comment;

import java.util.List;

import javax.annotation.Nonnull;

import rx.Observable;

public interface CommentsDbOperations {

    @Nonnull
    Observable<Boolean> saveComments(List<Comment> comments);

    @Nonnull
    Observable<Integer> getCommentsCountForPost(String postId);

}
