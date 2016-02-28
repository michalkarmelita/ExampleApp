package com.michalkarmelita.testapp.api.model;

import javax.annotation.Nonnull;

public class Company {

    @Nonnull
    final String name;
    @Nonnull
    final String catchPhrase;
    @Nonnull
    final String bs;

    public Company(@Nonnull String name, @Nonnull String catchPhrase, @Nonnull String bs) {
        this.name = name;
        this.catchPhrase = catchPhrase;
        this.bs = bs;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public String getCatchPhrase() {
        return catchPhrase;
    }

    @Nonnull
    public String getBs() {
        return bs;
    }
}
