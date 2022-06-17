package com.example.instagrambydhriti.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;

import com.example.instagrambydhriti.Fragments.FeedFragment;
import com.example.instagrambydhriti.Post;
import com.example.instagrambydhriti.PostsAdapter;
import com.example.instagrambydhriti.R;
import com.example.instagrambydhriti.UserPostsAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsActivity extends FeedActivity {

    private RecyclerView rvUserPosts;
    protected UserPostsAdapter adapter;
    protected List<Post> allPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        rvUserPosts = findViewById(R.id.rvUserPosts);
            // initialize the array that will hold posts and create a PostsAdapter
            allPosts = new ArrayList<>();
            adapter = new UserPostsAdapter(this, allPosts);
            // set the adapter and layout manager
            rvUserPosts.setAdapter(adapter);
            rvUserPosts.setLayoutManager(new GridLayoutManager(this, 3));
            // query posts from Parsetagram
            queryPosts();
        }

        private void queryPosts() {
            // specify what type of data we want to query - Post.class
            ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
            // include data referred by user key
            query.include(Post.KEY_USER);
            // query.whereEqualTo(Post.KEY_USER, UserName);
            // order posts by creation date (newest first)
            query.addDescendingOrder("createdAt");
            // start an asynchronous call for posts
            query.findInBackground(new FindCallback<Post>() {
                @Override
                public void done(List<Post> posts, ParseException e) {
                    // check for errors
                    if (e != null) {
                        Log.e(TAG, "Issue with getting posts", e);
                        return;
                    }
                    // save received posts to list and notify adapter of new data
                    allPosts.addAll(posts);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }