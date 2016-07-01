package com.codepath.apps.mysimpletweets.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
// ...

public class NewTweetFragment extends DialogFragment {
    int characterCount;
    User user;

    public interface NewTweetDialogListener {
        void onFinishTweetDialog(Tweet tweet);
    }

    private EditText mEditText;
    private TextView tvCharacterCount;

    public NewTweetFragment() {
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
        TwitterClient client = TwitterApplication.getRestClient();
        //get the current user
        client.getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //Toast.makeText(getApplicationContext(), "sup", Toast.LENGTH_SHORT).show();

                user = User.fromJSON(response);
                //my current user account's info
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("435345345345", "fail");
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tweet_fragment, container);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        mEditText = (EditText) view.findViewById(R.id.txt_your_name);
        tvCharacterCount = (TextView) view.findViewById(R.id.tvCharacterCount);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        mEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Toast.makeText(getContext(), "before", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvCharacterCount.setText(
                        (140 - mEditText.getText().toString().length()) + "");
                Toast.makeText(getContext(), "during", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void afterTextChanged(Editable s) {
                Toast.makeText(getContext(), "after", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        NewTweetDialogListener listener = (NewTweetDialogListener) getActivity();
        String tweetBody = mEditText.getText().toString();

        TwitterClient client = TwitterApplication.getRestClient();
        //get the account info
        client.postTweet(tweetBody, (new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {


            }
        }));
        Tweet tweet = new Tweet();
        tweet.setBody(tweetBody);


        tweet.setUser(user);
        tweet.setCreatedAt("now");



        //tweet.setUser();
        //tweet.

        listener.onFinishTweetDialog(tweet);

        super.onDismiss(dialog);
    }



}