package com.michalkarmelita.testapp.view.posts;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.michalkarmelita.testapp.App;
import com.michalkarmelita.testapp.BaseActivity;
import com.michalkarmelita.testapp.R;
import com.michalkarmelita.testapp.adapters.PostsAdapter;
import com.michalkarmelita.testapp.api.model.Post;
import com.michalkarmelita.testapp.dagger.ActivityModule;
import com.michalkarmelita.testapp.dagger.BaseActivityComponent;
import com.michalkarmelita.testapp.presenter.PostsPresenter;
import com.michalkarmelita.testapp.rx.MyViewActions;
import com.michalkarmelita.testapp.view.postdetails.PostContentActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PostsActivity extends BaseActivity implements PostsAdapter.PostItemClickedListener {

    @InjectView(R.id.root_view)
    View rootView;
    @InjectView(R.id.posts_recycler_view)
    RecyclerView recyclerView;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    PostsPresenter presenter;
    @Inject
    PostsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        presenter.getPostsObservable()
                .compose(this.<List<Post>>bindToLifecycle())
                .subscribe(adapter);

        presenter.getUpdateSuccessObservable()
                .compose(this.<Boolean>bindToLifecycle())
                .subscribe(MyViewActions.showSnackbar(rootView, R.string.posts_update_success));

        presenter.getUpdateFailObservable()
                .compose(this.<Boolean>bindToLifecycle())
                .subscribe(MyViewActions.showSnackbar(rootView, R.string.posts_update_fail));

        adapter.setPostItemClickedListener(this);

    }

    @NonNull
    @Override
    public BaseActivityComponent createActivityComponent(@Nullable Bundle savedInstanceState) {
        final PostsActivityComponent component = DaggerPostsActivityComponent
                .builder()
                .appComponent(App.getAppComponent(getApplication()))
                .activityModule(new ActivityModule(this))
                .build();

        component.inject(this);
        return component;
    }

    @Override
    public void onPostClicked(int postId) {
        startActivity(PostContentActivity.newInstance(this, postId));
    }
}
