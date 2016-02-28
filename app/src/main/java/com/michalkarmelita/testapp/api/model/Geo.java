package com.michalkarmelita.testapp.api.model;

import javax.annotation.Nonnull;

public class Geo {

    @Nonnull
    final String lat;
    @Nonnull
    final String lng;

    public Geo(@Nonnull String lat, @Nonnull String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    @Nonnull
    public String getLat() {
        return lat;
    }

    @Nonnull
    public String getLng() {
        return lng;
    }
}
