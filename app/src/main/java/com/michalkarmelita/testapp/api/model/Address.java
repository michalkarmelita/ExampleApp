package com.michalkarmelita.testapp.api.model;

import javax.annotation.Nonnull;

public class Address {

    @Nonnull
    final String street;
    @Nonnull
    final String suite;
    @Nonnull
    final String city;
    @Nonnull
    final String zipcode;
    @Nonnull
    final Geo geo;

    public Address(@Nonnull String street, @Nonnull String suite, @Nonnull String city, @Nonnull String zipcode, @Nonnull Geo geo) {
        this.street = street;
        this.suite = suite;
        this.city = city;
        this.zipcode = zipcode;
        this.geo = geo;
    }

    @Nonnull
    public String getStreet() {
        return street;
    }

    @Nonnull
    public String getSuite() {
        return suite;
    }

    @Nonnull
    public String getCity() {
        return city;
    }

    @Nonnull
    public String getZipcode() {
        return zipcode;
    }

    @Nonnull
    public Geo getGeo() {
        return geo;
    }
}
