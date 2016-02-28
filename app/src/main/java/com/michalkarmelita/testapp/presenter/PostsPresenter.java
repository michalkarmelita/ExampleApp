package com.michalkarmelita.testapp.presenter;

import android.support.annotation.NonNull;

import com.appunite.rx.operators.MoreOperators;
import com.michalkarmelita.testapp.api.ApiService;
import com.michalkarmelita.testapp.api.model.Comment;
import com.michalkarmelita.testapp.api.model.Post;
import com.michalkarmelita.testapp.api.model.User;
import com.michalkarmelita.testapp.dagger.NetworkScheduler;
import com.michalkarmelita.testapp.dagger.UiScheduler;
import com.michalkarmelita.testapp.db.operations.CommentsDbOperations;
import com.michalkarmelita.testapp.db.operations.PostsDbOperations;
import com.michalkarmelita.testapp.db.operations.UsersDbOperations;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func3;
import rx.subjects.PublishSubject;

public class PostsPresenter {

    @Nonnull
    private final Observable<List<Post>> postsObservable;
    @Nonnull
    private final Observable<Boolean> updateObservable;
    @Nonnull
    private final PublishSubject<Object> refreshSubject = PublishSubject.create();

    @Inject
    public PostsPresenter(@Nonnull ApiService apiService,
                          @Nonnull @UiScheduler Scheduler uiScheduler,
                          @Nonnull @NetworkScheduler Scheduler networkScheduler,
                          @Nonnull final PostsDbOperations postsDbOperations,
                          @Nonnull final UsersDbOperations usersDbOperations,
                          @Nonnull final CommentsDbOperations commentsDbOperations) {


        postsObservable = postsDbOperations.getAllPosts()
                .compose(MoreOperators.<List<Post>>refresh(refreshSubject));

        final Observable<Boolean> postsUpdateObservable = apiService
                .posts()
                .switchMap(new Func1<List<Post>, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(List<Post> posts) {
                        return postsDbOperations.savePosts(posts);
                    }
                })
                .subscribeOn(networkScheduler)
                .observeOn(uiScheduler);

        final Observable<Boolean> usersUpdateObservable = apiService.users()
                .switchMap(new Func1<List<User>, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(List<User> users) {
                        return usersDbOperations.saveUsers(users);
                    }
                })
                .subscribeOn(networkScheduler)
                .observeOn(uiScheduler);

        final Observable<Boolean> commentsUpdateObservable = apiService.comments()
                .switchMap(new Func1<List<Comment>, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(List<Comment> comments) {
                        return commentsDbOperations.saveComments(comments);
                    }
                })
                .subscribeOn(networkScheduler)
                .observeOn(uiScheduler);

        updateObservable = Observable.zip(
                postsUpdateObservable,
                usersUpdateObservable,
                commentsUpdateObservable,
                new Func3<Boolean, Boolean, Boolean, Boolean>() {
            @Override
            public Boolean call(Boolean posts, Boolean users, Boolean comments) {
                return posts && users && comments;
            }
        })
        .doOnNext(refreshAction());



    }

    @NonNull
    private Action1<Boolean> refreshAction() {
        return new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                refreshSubject.onNext(null);
            }
        };
    }

    @Nonnull
    public Observable<List<Post>> getPostsObservable() {
        return postsObservable;
    }

    @Nonnull
    public Observable<Boolean> getUpdateSuccessObservable() {
        return updateObservable
                .filter(new Func1<Boolean, Boolean>() {
                    @Override
                    public Boolean call(Boolean success) {
                        return success;
                    }
                });
    }

    @Nonnull
    public Observable<Boolean> getUpdateFailObservable() {
        return updateObservable
                .filter(new Func1<Boolean, Boolean>() {
                    @Override
                    public Boolean call(Boolean success) {
                        return !success;
                    }
                });
    }

}
