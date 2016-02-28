package com.michalkarmelita.testapp.view.postdetails;

import com.michalkarmelita.testapp.dagger.ActivityModule;
import com.michalkarmelita.testapp.dagger.ActivityScope;
import com.michalkarmelita.testapp.dagger.AppComponent;
import com.michalkarmelita.testapp.dagger.BaseActivityComponent;
import com.michalkarmelita.testapp.db.operations.DbOperationsModule;
import com.michalkarmelita.testapp.presenter.PostContentPresenter;

import javax.inject.Named;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = {
                ActivityModule.class,
                PostsContentActivityModule.class,
                DbOperationsModule.class
        }
)
public interface PostContentActivityComponent extends BaseActivityComponent {

    void inject(PostContentActivity postContentActivity);

    PostContentPresenter presenter();

    @Named("PostId")
    int providePostId();

}
