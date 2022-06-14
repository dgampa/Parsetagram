package com.example.instagrambydhriti.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagrambydhriti.Post;
import com.example.instagrambydhriti.R;
import com.parse.ParseFile;

import org.parceler.Parcels;

public class PostDetailsActivity extends AppCompatActivity {

    private TextView tvUsername;
    private ImageView ivImage;
    private TextView tvDescription;
    // instance variable used to store unwrapped parcel
    Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        tvUsername = findViewById(R.id.tvUsername);
        ivImage = findViewById(R.id.ivImage);
        tvDescription = findViewById(R.id.tvDescription);
        // implementing a post details activity
        post = (Post) Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));
        // set post data
        tvUsername.setText(post.getUser().getUsername());
        tvDescription.setText(post.getDescription());
        // loading ParseFile image
        ParseFile image = post.getImage();
        if(image != null){
            Glide.with(this).load(image.getUrl()).into(ivImage);
        }
    }
}