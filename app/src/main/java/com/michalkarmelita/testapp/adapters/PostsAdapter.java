package com.michalkarmelita.testapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.michalkarmelita.testapp.R;
import com.michalkarmelita.testapp.api.model.Post;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.functions.Action1;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> implements Action1<List<Post>> {

    public interface PostItemClickedListener{
        void onPostClicked(int postId);
    }

    public class PostsViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.posts_title)
        TextView postsTitle;

        public PostsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        public void bind(final Post adapterItem){
            postsTitle.setText(adapterItem.getTitle());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postItemClickedListener.onPostClicked(adapterItem.getId());
                }
            });

        }

    }

    @Nonnull
    private final LayoutInflater inflater;
    final List<Post> postsList = new ArrayList<>();
    private PostItemClickedListener postItemClickedListener;

    public void setPostItemClickedListener(PostItemClickedListener listener){
        postItemClickedListener = listener;
    }

    @Override
    public PostsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.posts_list_item, parent, false);
        return new PostsViewHolder(view);
    }

    @Inject
    public PostsAdapter(@Nonnull LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public void onBindViewHolder(PostsViewHolder holder, int position) {
        holder.bind(postsList.get(position));
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    @Override
    public void call(List<Post> posts) {
        this.postsList.clear();
        this.postsList.addAll(posts);
        notifyDataSetChanged();
    }

}
