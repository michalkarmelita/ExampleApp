package com.michalkarmelita.testapp.dagger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

@Scope
//@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {
}