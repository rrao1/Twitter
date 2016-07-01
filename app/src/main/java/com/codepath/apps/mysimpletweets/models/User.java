package com.codepath.apps.mysimpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by ramyarao on 6/27/16.
 */

@Parcel
public class User {
    // list attributes
    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;
    private String tagline;
    private int followersCount;
    private int followingsCount;
    private boolean following;

    public int getFollowersCount() {
        return followersCount;
    }

    public String getTagline() {
        return tagline;
    }

    public int getFriendsCount() {
        return followingsCount;
    }

    public boolean getFollowing() { return following; }



    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public User() {

    }

    public static User fromJSON(JSONObject json) {
        User u = new User();
        //extract and fill the values
        try {
            u.tagline = json.getString("description");
            u.followersCount = json.getInt("followers_count");
            u.followingsCount = json.getInt("friends_count");
            u.name = json.getString("name");
            u.uid = json.getLong("id");
            u.screenName = json.getString("screen_name");
            u.profileImageUrl = json.getString("profile_image_url");
            u.following = json.getBoolean("following");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // return a user
        return u;
    }
}
