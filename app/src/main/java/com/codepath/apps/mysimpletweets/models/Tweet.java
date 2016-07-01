package com.codepath.apps.mysimpletweets.models;

import com.codepath.apps.mysimpletweets.TimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

// Parse the JSON + store the data
// Encapsulate state logic or display logic
public class Tweet {
    // list out the attributes
    private String body;

    private boolean favorited;

    public void setBody(String body) {
        this.body = body;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private long uid; // unique id for the tweet
    private String createdAt;
    private User user; // store embedded user object
//    private String timeStamp;
//
//    public String getTimeStamp() {
//        return timeStamp;
//    }

    public String getCreatedAt() {
        return createdAt;
    }

    public boolean getFavorited() { return favorited; }


    public User getUser() {
        return user;
    }

    public long getUid() {
        return uid;
    }

    public String getBody() {
        return body;
    }

    //Tweet.fromJSONArray([ {},{}]=> List<Tweet>
    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        //iterate the json array and create tweets
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
                if (tweet != null) {
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        //return the finished list
        return tweets;
    }




    //deserialize the JSON and build Tweet objects

    // Tweet.fromJSON({ ... }) => <Tweet>
    public static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        // Extract the values from the json, store them
        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
            tweet.favorited = jsonObject.getBoolean("favorited");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // tweet.user = ...
        // Return the tweet object
        return tweet;
    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public static String getRelativeTimeAgo(String rawJsonDate) {
        String formattedTime = TimeFormatter.getTimeDifference(rawJsonDate);
        return formattedTime;

    }

}
