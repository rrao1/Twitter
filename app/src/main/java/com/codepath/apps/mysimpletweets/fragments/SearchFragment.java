package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.codepath.apps.mysimpletweets.TimelineActivity;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SearchFragment extends TweetsListFragment{
    private TwitterClient client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get the client
        client = TwitterApplication.getRestClient(); //singleton client

        //populateTimeline();
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        String query2 = ((TimelineActivity)getActivity()).query1;
//        int hi =1;
//    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        populateTimeline();
    }

    public void populateTimeline() {
        String query2 = ((TimelineActivity)getActivity()).query1;


        client.getSearch(query2, new JsonHttpResponseHandler() {

            //SUCCESS
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                try {
                    JSONArray array = json.getJSONArray("statuses");
                    aTweets.clear();
                    addAll(Tweet.fromJSONArray(array));

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
}