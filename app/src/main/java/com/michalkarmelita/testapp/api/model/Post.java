package com.michalkarmelita.testapp.api.model;

import javax.annotation.Nonnull;

public class Post {

    private final int userId;

    private final int id;
    @Nonnull
    private final String title;
    @Nonnull
    private final String body;

    public Post(int userId, int id, @Nonnull String title, @Nonnull String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    @Nonnull
    public String getTitle() {
        return title;
    }

    @Nonnull
    public String getBody() {
        return body;
    }

}
