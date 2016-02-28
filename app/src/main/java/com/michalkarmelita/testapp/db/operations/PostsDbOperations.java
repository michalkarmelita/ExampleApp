package com.michalkarmelita.testapp.db.operations;

import com.google.common.base.Optional;
import com.michalkarmelita.testapp.api.model.Post;

import java.util.List;

import javax.annotation.Nonnull;

import rx.Observable;

public interface PostsDbOperations {

    @Nonnull
    Observable<Boolean> savePosts(List<Post> postsResponse);

    @Nonnull
    Observable<List<Post>> getAllPosts();

    @Nonnull
    Observable<Optional<Post>> getPost(String postId);

}
