package com.michalkarmelita.testapp.view.posts;

import com.michalkarmelita.testapp.dagger.ActivityModule;
import com.michalkarmelita.testapp.dagger.ActivityScope;
import com.michalkarmelita.testapp.dagger.AppComponent;
import com.michalkarmelita.testapp.dagger.BaseActivityComponent;
import com.michalkarmelita.testapp.db.operations.DbOperationsModule;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = {
                ActivityModule.class,
                DbOperationsModule.class
        }
)
public interface PostsActivityComponent extends BaseActivityComponent {

        void inject(PostsActivity postsActivity);

}
