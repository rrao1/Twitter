package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.models.User;

import java.util.ArrayList;

/**
 * Created by ramyarao on 6/27/16.
 */
public class FollowersActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeContainer;


    protected TweetsArrayAdapter aFollowers;
    protected ArrayList<User> followers;
    protected ListView lvTweets;
    private Context context;
    // inflation logic


//    public void fetchTimelineAsync(int page) {
//        TwitterClient client = TwitterApplication.getRestClient();
//        client.getFollowers(id, new JsonHttpResponseHandler() {
//            //SUCCESS
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
//                //deserialize json
//                //create models and add them to the adapter
//                //load the model data into listview
//                aFollowers.clear();
//                addAll(Tweet.fromJSONArray(json));
//                swipeContainer.setRefreshing(false);
//
//                //Log.d("DEBUG", json.toString());
//                Log.d("DEBUG", json.toString());
//                //Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
//
//                //super.onSuccess(statusCode, headers, response);
//            }
//
//            //FAILURE
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                Log.d("DEBUG", errorResponse.toString());
//                //super.onFailure(statusCode, headers, responseString, throwable);
//            }
//
//        });
//    }
//
//    //creation life cycle event
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //create the arraylist (data source)
//        followers = new ArrayList<>();
//
//        //construct the adapter from data source
//        aFollowers = new TweetsArrayAdapter(this, followers);
//
//        lvTweets = (ListView) findViewById(R.id.lvTweets);
//        //connect adapter to listview
//        lvTweets.setAdapter(aFollowers);
//
//        if (getClass().equals(HomeTimelineFragment.class)) {
//
//            swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
//            // Setup refresh listener which triggers new data loading
//            swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                @Override
//                public void onRefresh() {
//                    // Your code to refresh the list here.
//                    // Make sure you call swipeContainer.setRefreshing(false)
//                    // once the network request has completed successfully.
//
//                    fetchTimelineAsync(0);
//
//                }
//            });
//            // Configure the refreshing colors
//            swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
//                    android.R.color.holo_green_light,
//                    android.R.color.holo_orange_light,
//                    android.R.color.holo_red_light);
//        }
//        else if (getClass().equals(MentionsTimelineFragment.class)) {
//
//        }
//
//
//
//
//    }
//
//    public void addAll(List<Tweet> tweets) {
//        aFollowers.addAll(tweets);
//    }
//
//    public void addNewUser(User user) {
//        followers.add(0, user);
//        aFollowers.notifyDataSetChanged();
//
//    }
}
