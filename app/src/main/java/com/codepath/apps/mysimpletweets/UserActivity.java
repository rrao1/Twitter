package com.codepath.apps.mysimpletweets;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.fragments.RetweetFragment;
import com.codepath.apps.mysimpletweets.fragments.UserTimelineFragment;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class UserActivity extends AppCompatActivity {
    TwitterClient client;
    public long tweetId;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        user = (User) Parcels.unwrap(getIntent().getParcelableExtra("user"));
        getSupportActionBar().setTitle("@" + user.getScreenName());

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4099FF")));

        //Button btnFollowers = (Button) findViewById(R.id.btnFollowers);
        populateProfileHeader(user);



        //get screenname from the activity that launches this
        //String screenName = getIntent().getStringExtra("screen_name");
        String screenName = user.getScreenName();
        if (savedInstanceState == null) {
            //create the user timeline fragment
            UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);
            //display user fragment within this activity (dynamically)
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, fragmentUserTimeline);
            ft.commit(); //changes the fragments
        }
    }

    private void populateProfileHeader(User user) {
        final User u = user;
        TextView tvName = (TextView) findViewById(R.id.tvFullName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        Log.d("DEBUG", user.getName());
        tvName.setText(user.getName());
        tvTagline.setText(user.getTagline());
        tvFollowers.setText(user.getFollowersCount() + " Followers");
        tvFollowing.setText(user.getFriendsCount() + " Following");
        Picasso.with(this).load(user.getProfileImageUrl()).into(ivProfileImage);



        final Button btnFollow = (Button) findViewById(R.id.btnFollow);

        if (u.getFollowing()) {
            btnFollow.setText("Unfollow");

        }

        btnFollow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
            if (!btnFollow.isSelected()) {
                    unfollow(u.getUid());
                    btnFollow.setText("Follow");
                    btnFollow.setSelected(true);

            }
            else {
                follow(u.getUid());
                btnFollow.setText("Unfollow");
                btnFollow.setSelected(false);
            }
            }
        });


    }

    public void follow(long id) {
        TwitterClient client = TwitterApplication.getRestClient();
        //get the current user
        client.createFriendship(id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject json) {
            }
        });
    }
    public void unfollow(long id) {
        TwitterClient client = TwitterApplication.getRestClient();
        //get the current user
        client.destroyFriendship(id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject json) {
            }
        });
    }

    public void showRetweetDialog(long id) {
        Log.d("idtimeline", id +"");
        tweetId = id;
        FragmentManager fm = getSupportFragmentManager();
        RetweetFragment frag = new RetweetFragment();
        frag.show(fm, "retweet_fragment");
    }

}
