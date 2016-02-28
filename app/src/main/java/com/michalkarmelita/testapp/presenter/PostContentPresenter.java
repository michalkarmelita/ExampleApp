package com.michalkarmelita.testapp.presenter;

import android.content.res.Resources;
import android.support.annotation.NonNull;

import com.google.common.base.Optional;
import com.michalkarmelita.testapp.R;
import com.michalkarmelita.testapp.api.ApiConstants;
import com.michalkarmelita.testapp.api.model.Post;
import com.michalkarmelita.testapp.dagger.ForApplication;
import com.michalkarmelita.testapp.db.operations.CommentsDbOperations;
import com.michalkarmelita.testapp.db.operations.PostsDbOperations;
import com.michalkarmelita.testapp.db.operations.UsersDbOperations;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.functions.Func1;

public class PostContentPresenter {

    @Nonnull
    public final Observable<String> titleObservable;
    @Nonnull
    public final Observable<String> bodyObservable;
    @Nonnull
    public final Observable<String> usernameObservable;
    @Nonnull
    public final Observable<String> commentsObservable;
    @Nonnull
    private final Observable<String> userAvatarUriObservable;

    @Inject
    public PostContentPresenter(@Nonnull final PostsDbOperations postsDbOperations,
                                @Nonnull final UsersDbOperations usersDbOperations,
                                @Nonnull final CommentsDbOperations commentsDbOperations,
                                @Nonnull @ForApplication final Resources resources,
                                @Named("PostId")int postId) {


        final Observable<Post> postObservable = postsDbOperations.getPost(String.valueOf(postId))
                .filter(new Func1<Optional<Post>, Boolean>() {
                    @Override
                    public Boolean call(Optional<Post> postOptional) {
                        return postOptional.isPresent();
                    }
                })
                .map(new Func1<Optional<Post>, Post>() {
                    @Override
                    public Post call(Optional<Post> postOptional) {
                        return postOptional.get();
                    }
                });

        titleObservable = postObservable.map(new Func1<Post, String>() {
            @Override
            public String call(Post post) {
                return post.getTitle();
            }
        });

        bodyObservable = postObservable.map(new Func1<Post, String>() {
            @Override
            public String call(Post post) {
                return post.getBody();
            }
        });

        usernameObservable = postObservable
                .map(getPostOwnerId())
                .switchMap(new Func1<String, Observable<Optional<String>>>() {
                    @Override
                    public Observable<Optional<String>> call(String userId) {
                        return usersDbOperations.getUserNameForId(userId);
                    }
                })
                .map(getValueFromOptional(resources));

        userAvatarUriObservable = postObservable
                .map(getPostOwnerId())
                .switchMap(new Func1<String, Observable<Optional<String>>>() {
                    @Override
                    public Observable<Optional<String>> call(String userId) {
                        return usersDbOperations.getEmailForUserId(userId);
                    }
                })
                .map(getValueFromOptional(resources))
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String email) {
                        return String.format(ApiConstants.API_AVATAR_FORMAT_ENDPOINT, email);
                    }
                });

        commentsObservable = commentsDbOperations.getCommentsCountForPost(String.valueOf(postId))
            .map(new Func1<Integer, String>() {
                @Override
                public String call(Integer integer) {
                    return integer.toString();
                }
            });
    }

    @NonNull
    private Func1<Optional<String>, String> getValueFromOptional(@Nonnull @ForApplication final Resources resources) {
        return new Func1<Optional<String>, String>() {
            @Override
            public String call(Optional<String> stringOptional) {
                if (stringOptional.isPresent()){
                    return stringOptional.get();
                } else {
                    return resources.getString(R.string.unknown_user);
                }
            }
        };
    }

    @NonNull
    private Func1<Post, String> getPostOwnerId() {
        return new Func1<Post, String>() {
            @Override
            public String call(Post post) {
                return String.valueOf(post.getUserId());
            }
        };
    }

    @Nonnull
    public Observable<String> getTitleObservable() {
        return titleObservable;
    }

    @Nonnull
    public Observable<String> getBodyObservable() {
        return bodyObservable;
    }

    @Nonnull
    public Observable<String> getUsernameObservable() {
        return usernameObservable;
    }

    @Nonnull
    public Observable<String> getCommentsObservable() {
        return commentsObservable;
    }

    @Nonnull
    public Observable<String> getUserAvatarUriObservable() {
        return userAvatarUriObservable;
    }
}
