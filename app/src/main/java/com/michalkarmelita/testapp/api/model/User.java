package com.michalkarmelita.testapp.api.model;

import javax.annotation.Nonnull;

public class User {

    final int id;
    @Nonnull
    final String name;
    @Nonnull
    final String username;
    @Nonnull
    final String email;
    @Nonnull
    final String phone;
    @Nonnull
    final String website;
    @Nonnull
    final Address address;
    @Nonnull
    final Company company;

    public User(int id, @Nonnull String name, @Nonnull String username, @Nonnull String email, @Nonnull String phone, @Nonnull String website, @Nonnull Address address, @Nonnull Company company) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.website = website;
        this.address = address;
        this.company = company;
    }

    public int getId() {
        return id;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public String getUsername() {
        return username;
    }

    @Nonnull
    public String getEmail() {
        return email;
    }

    @Nonnull
    public String getPhone() {
        return phone;
    }

    @Nonnull
    public String getWebsite() {
        return website;
    }

    @Nonnull
    public Address getAddress() {
        return address;
    }

    @Nonnull
    public Company getCompany() {
        return company;
    }
}
