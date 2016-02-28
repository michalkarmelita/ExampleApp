package com.michalkarmelita.testapp.api;

import com.michalkarmelita.testapp.api.model.Comment;
import com.michalkarmelita.testapp.api.model.Post;
import com.michalkarmelita.testapp.api.model.User;

import java.util.List;

import retrofit.http.GET;
import rx.Observable;

public interface ApiService {

    @GET("/posts")
    Observable<List<Post>> posts();

    @GET("/users")
    Observable<List<User>> users();

    @GET("/comments")
    Observable<List<Comment>> comments();

}
