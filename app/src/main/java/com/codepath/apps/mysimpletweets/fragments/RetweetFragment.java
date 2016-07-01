package com.codepath.apps.mysimpletweets.fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TimelineActivity;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.UserActivity;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
// ...

public class RetweetFragment extends DialogFragment {
    int characterCount;
    User user;

    public interface NewTweetDialogListener {
        void onFinishTweetDialog(Tweet tweet);
    }

    private EditText mEditText;
    private TextView tvCharacterCount;

    public RetweetFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static NewTweetFragment newInstance(String title) {
        NewTweetFragment frag = new NewTweetFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void retweet() {
        TwitterClient client = TwitterApplication.getRestClient();
        long id = 0;
        Class hi = getActivity().getClass();
        Log.d("class", hi.toString());
        if (((getActivity()).getClass()).equals(TimelineActivity.class)) {

            id = ((TimelineActivity) getActivity()).tweetId;
            Log.d("sup", "sup");
        } else {
            id = ((UserActivity) getActivity()).tweetId;
        }
        //get the current user
        client.retweet(id + "", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                dismissAllowingStateLoss();
                dismissAllowingStateLoss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject json) {
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.retweet_fragment, container);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnRetweet = (Button) view.findViewById(R.id.btnRetweet1);
        btnRetweet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                retweet();
            }
        });

        // Get field from view

    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
//        NewTweetDialogListener listener = (NewTweetDialogListener) getActivity();
//        String tweetBody = mEditText.getText().toString();
//
//        TwitterClient client = TwitterApplication.getRestClient();
//        //get the account info
//        client.postTweet(tweetBody, (new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//
//
//            }
//        }));
//        Tweet tweet = new Tweet();
//        tweet.setBody(tweetBody);
//
//
//        tweet.setUser(user);
//        tweet.setCreatedAt("now");
//
//
//        //tweet.setUser();
//        //tweet.
//
//        listener.onFinishTweetDialog(tweet);

        super.onDismiss(dialog);
    }
}



