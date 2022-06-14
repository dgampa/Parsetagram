package com.example.instagrambydhriti;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Register parse models
        ParseObject.registerSubclass(Post.class);
        // Initialize Parse SDK
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("QEx0p60to3TtEF5AOOEIpvcfNP9FgBT1dGBrjcFE")
                .clientKey("xbZgxSU9IK2KOTQ5awjwDedDDquFNamLck3BIEDf")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
