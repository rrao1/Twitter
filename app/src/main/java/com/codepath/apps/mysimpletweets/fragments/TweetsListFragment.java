package com.codepath.apps.mysimpletweets.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TimelineActivity;
import com.codepath.apps.mysimpletweets.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ramyarao on 6/27/16.
 */
public class TweetsListFragment extends Fragment {

    private SwipeRefreshLayout swipeContainer;


    protected TweetsArrayAdapter aTweets;
    protected ArrayList<Tweet> tweets;
    protected ListView lvTweets;
    private Context context;
    // inflation logic








    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        //connect adapter to listview
        lvTweets.setAdapter(aTweets);

        if (getClass().equals(HomeTimelineFragment.class)) {
            swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
            // Setup refresh listener which triggers new data loading
            swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    // Your code to refresh the list here.
                    // Make sure you call swipeContainer.setRefreshing(false)
                    // once the network request has completed successfully.
                    fetchTimelineAsync(0);
                }
            });
            // Configure the refreshing colors
            swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
        }
        else if (getClass().equals(MentionsTimelineFragment.class)) {
            swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
            // Setup refresh listener which triggers new data loading
            swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    // Your code to refresh the list here.
                    // Make sure you call swipeContainer.setRefreshing(false)
                    // once the network request has completed successfully.
                    refreshMentions();
                }
            });
            // Configure the refreshing colors
            swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
        }

        else if (getClass().equals(SearchFragment.class)) {
            swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
            // Setup refresh listener which triggers new data loading
            swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    // Your code to refresh the list here.
                    // Make sure you call swipeContainer.setRefreshing(false)
                    // once the network request has completed successfully.
                    searchRefresh();
                }
            });
            // Configure the refreshing colors
            swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
        }

        return v;
    }

    public void searchRefresh() {
        String query2 = ((TimelineActivity)getActivity()).query1;

        TwitterClient client = TwitterApplication.getRestClient();
        client.getSearch(query2, new JsonHttpResponseHandler() {

            //SUCCESS
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                try {
                    JSONArray array = json.getJSONArray("statuses");
                    aTweets.clear();
                    addAll(Tweet.fromJSONArray(array));
                    swipeContainer.setRefreshing(false);

                    //Log.d("arr", array.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //FAILURE
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }

    public void fetchTimelineAsync(int page) {
        TwitterClient client = TwitterApplication.getRestClient();
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            //SUCCESS
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                //deserialize json
                //create models and add them to the adapter
                //load the model data into listview
                aTweets.clear();
                addAll(Tweet.fromJSONArray(json));
                swipeContainer.setRefreshing(false);

                //Log.d("DEBUG", json.toString());
                Log.d("DEBUG", json.toString());
            }

            //FAILURE
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
                //super.onFailure(statusCode, headers, responseString, throwable);
            }

        });
    }

    public void refreshMentions() {
        TwitterClient client = TwitterApplication.getRestClient();
        client.getMentionsTimeline(new JsonHttpResponseHandler() {
            //SUCCESS
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                //deserialize json
                //create models and add them to the adapter
                //load the model data into listview
                addAll(Tweet.fromJSONArray(json));
                swipeContainer.setRefreshing(false);

                //Log.d("DEBUG", json.toString());
                Log.d("DEBUG", json.toString());
                //Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();

                //super.onSuccess(statusCode, headers, response);
            }

            //FAILURE
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
                //super.onFailure(statusCode, headers, responseString, throwable);
            }

        });

    }

    //creation life cycle event
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //create the arraylist (data source)
        tweets = new ArrayList<>();

        //construct the adapter from data source
        aTweets = new TweetsArrayAdapter(getActivity(), tweets);






    }

    public void addAll(List<Tweet> tweets) {
        aTweets.addAll(tweets);
    }

    public void addNewTweet(Tweet tweet) {
        tweets.add(0, tweet);
        aTweets.notifyDataSetChanged();

    }
}
