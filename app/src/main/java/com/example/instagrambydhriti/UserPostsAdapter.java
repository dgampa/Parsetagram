package com.example.instagrambydhriti;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.util.List;

public class UserPostsAdapter extends RecyclerView.Adapter<UserPostsAdapter.ViewHolder>{

    private Context context;
    private List<Post> posts;
    private static final String TAG = "UserPostsAdapter";

    public UserPostsAdapter(Context context, List<Post> posts){
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public UserPostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post_image, parent, false);
        return new UserPostsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserPostsAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }


    @Override
    public int getItemCount() {
        return posts.size();
    }

    // Clean all elements for the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivImageOnly;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImageOnly = itemView.findViewById(R.id.ivImageOnly);
        }

        public void bind(Post post) {
            // bind the post data to the view elements
            ParseFile image = post.getImage();
            if(image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImageOnly);
            }
        }
    }
}
