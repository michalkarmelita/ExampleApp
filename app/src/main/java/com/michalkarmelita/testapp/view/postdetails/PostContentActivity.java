package com.michalkarmelita.testapp.view.postdetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.michalkarmelita.testapp.App;
import com.michalkarmelita.testapp.BaseActivity;
import com.michalkarmelita.testapp.R;
import com.michalkarmelita.testapp.dagger.ActivityModule;
import com.michalkarmelita.testapp.dagger.BaseActivityComponent;
import com.michalkarmelita.testapp.picasso.RoundedTransformation;
import com.michalkarmelita.testapp.presenter.PostContentPresenter;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.functions.Action1;

public class PostContentActivity extends BaseActivity {

    public static final String POST_ID = "postId";

    public static Intent newInstance(Context context, int postId) {
        Intent intent = new Intent(context, PostContentActivity.class);
        intent.putExtra(POST_ID, postId);
        return intent;
    }

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.post_content_title)
    TextView postContentTitle;
    @InjectView(R.id.post_content_body)
    TextView postContentBody;
    @InjectView(R.id.post_content_user_name)
    TextView postContentUserName;
    @InjectView(R.id.post_content_user_avatar)
    ImageView postContentUserAvatar;
    @InjectView(R.id.post_content_comments)
    TextView postContentComments;

    @Inject
    PostContentPresenter presenter;
    @Inject
    Picasso picasso;
    @Inject
    RoundedTransformation transformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_content);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter.getTitleObservable()
                .compose(this.<String>bindToLifecycle())
                .subscribe(RxTextView.text(postContentTitle));

        presenter.getBodyObservable()
                .compose(this.<String>bindToLifecycle())
                .subscribe(RxTextView.text(postContentBody));

        presenter.getUsernameObservable()
                .compose(this.<String>bindToLifecycle())
                .subscribe(RxTextView.text(postContentUserName));

        presenter.getCommentsObservable()
                .compose(this.<String>bindToLifecycle())
                .subscribe(RxTextView.text(postContentComments));

        presenter.getUserAvatarUriObservable()
                .compose(this.<String>bindToLifecycle())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String uri) {
                        picasso.load(uri)
                                .placeholder(R.drawable.avatar)
                                .resizeDimen(R.dimen.avatar_size, R.dimen.avatar_size)
                                .transform(transformation)
                                .centerInside()
                                .into(postContentUserAvatar);
                    }
                });

    }

    @NonNull
    @Override
    public BaseActivityComponent createActivityComponent(@Nullable Bundle savedInstanceState) {
        final PostContentActivityComponent component = DaggerPostContentActivityComponent.builder()
                .appComponent(App.getAppComponent(getApplication()))
                .activityModule(new ActivityModule(this))
                .postsContentActivityModule(new PostsContentActivityModule(getIntent().getIntExtra(POST_ID, 1)))
                .build();
        component.inject(this);
        return component;
    }
}
