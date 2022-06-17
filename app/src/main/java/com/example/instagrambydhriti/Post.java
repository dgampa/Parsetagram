package com.example.instagrambydhriti;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.parceler.Parcel;

import java.util.ArrayList;

@ParseClassName("Post")
@Parcel(analyze = Post.class)
public class Post extends ParseObject {
    // private instance variables for different fields of post
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_LIKES = "likes";
    public static final String KEY_LIKES_BY_USERS = "likesByUser";

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }
    public void setDescription(String description){
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }
    public void setImage(ParseFile imageFile){
        put(KEY_IMAGE, imageFile);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }
    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public int getLikes(){ return getInt(KEY_LIKES); }
    public void setLikes(int likes) { put(KEY_LIKES, likes); }

    public JSONArray getUsersLiked(){ return getJSONArray(KEY_LIKES_BY_USERS); }
    public void setUsersLiked(JSONArray userLiked) { put(KEY_LIKES_BY_USERS, userLiked); }
}

