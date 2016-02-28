package com.michalkarmelita.testapp.api.model;

import javax.annotation.Nonnull;

public class Comment {

    final int postId;
    final int id;
    @Nonnull
    final String name;
    @Nonnull
    final String email;
    @Nonnull
    final String body;

    public Comment(int postId, int id, @Nonnull String name, @Nonnull String email, @Nonnull String body) {
        this.postId = postId;
        this.id = id;
        this.name = name;
        this.email = email;
        this.body = body;
    }

    public int getPostId() {
        return postId;
    }

    public int getId() {
        return id;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public String getEmail() {
        return email;
    }

    @Nonnull
    public String getBody() {
        return body;
    }
}
