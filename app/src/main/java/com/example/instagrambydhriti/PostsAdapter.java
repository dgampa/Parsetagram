package com.example.instagrambydhriti;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagrambydhriti.Activities.MainActivity;
import com.example.instagrambydhriti.Activities.PostDetailsActivity;
import com.example.instagrambydhriti.Activities.UserDetailsActivity;
import com.example.instagrambydhriti.Fragments.ProfileFragment;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;
    private static final String TAG = "PostsAdapter";

    public PostsAdapter(Context context, List<Post> posts){
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }


    @Override
    public int getItemCount() {
        return posts.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUsername;
        private ImageView ivImage;
        private TextView tvDescription;
        private TextView tvDate;
        private TextView tvUsername2;
        private ImageButton ibLike;
        // implemented for timestamp feature
        private static final int SECOND_MILLIS = 1000;
        private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvUsername2 = itemView.findViewById(R.id.tvUsername2);
            ibLike = itemView.findViewById(R.id.ibLike);
        }

        public void bind(Post post) {
            // bind the post data to the view elements
            tvUsername.setText(post.getUser().getUsername());
            tvDescription.setText(post.getDescription());
            tvDate.setText(getRelativeTimeAgo(post.getCreatedAt().toString()));
            tvUsername2.setText(post.getUser().getUsername());
            ibLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    post.setLikes(post.getLikes()+1);
                    JSONArray usersLiked = post.getUsersLiked();
                    int position = -1;
                    for(int i = 0; i<usersLiked.length(); i++){
                        try {
                            if(usersLiked.get(i).toString().equals(ParseUser.getCurrentUser().getObjectId())) {
                                position = i;
                                break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if(position > 0){
                        Glide.with(context).load(R.drawable.ufi_heart).into(ibLike);
                        usersLiked.remove(position);
                    }
                    else{
                        usersLiked.put(ParseUser.getCurrentUser().getObjectId());
                        Glide.with(context).load(R.drawable.ufi_heart_active).into(ibLike);
                    }
                    post.setUsersLiked(usersLiked);
                    post.saveInBackground();
                }
            });
            ParseFile image = post.getImage();
            if(image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }
            ivImage.setOnClickListener(new View.OnClickListener() {
                // implemented for post details click
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, UserDetailsActivity.class);
                    intent.putExtra(Post.class.getSimpleName(), Parcels.wrap(post));
                    context.startActivity(intent);
                }
            });
        }

        // get a Timestamp function
        public String getRelativeTimeAgo(String parseData) {
            String instagramFormat =  "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
            SimpleDateFormat sf = new SimpleDateFormat(instagramFormat, Locale.ENGLISH);
            sf.setLenient(true);

            try {
                long time = sf.parse(parseData).getTime();
                long now = System.currentTimeMillis();

                final long diff = now - time;
                if (diff < MINUTE_MILLIS) {
                    return "just now";
                } else if (diff < 2 * MINUTE_MILLIS) {
                    return "1 minute ago";
                } else if (diff < 50 * MINUTE_MILLIS) {
                    return diff / MINUTE_MILLIS + " minutes ago";
                } else if (diff < 90 * MINUTE_MILLIS) {
                    return "1 hour ago";
                } else if (diff < 24 * HOUR_MILLIS) {
                    return diff / HOUR_MILLIS + " hours ago";
                } else if (diff < 48 * HOUR_MILLIS) {
                    return "1 day ago";
                } else {
                    return diff / DAY_MILLIS + " days ago";
                }
            } catch (ParseException e) {
                Log.i(TAG, "getRelativeTimeAgo failed");
                e.printStackTrace();
            }

            return "";
        }
    }
}

