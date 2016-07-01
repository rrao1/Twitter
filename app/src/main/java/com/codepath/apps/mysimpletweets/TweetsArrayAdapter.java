package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.fragments.RetweetFragment;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ramyarao on 6/27/16.
 */
// Taking the Tweet objects and turning them into Views displayed in the list
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {
    Context context1;
    Tweet tweet;
    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {


        super(context, 0, tweets);
        context1 = context;
    }

    // Override and setup custom template
    //ViewHolder pattern

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. Gt the tweet
        tweet = getItem(position);
        // 2. FInd or inflate the template
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }
        // 3. Find the subviews to fill with data in the template
        final ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);

        final TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvTimeStamp = (TextView) convertView.findViewById(R.id.tvTimeStamp);
        // 4. Populate data into the subviews
        tvUserName.setText(tweet.getUser().getScreenName());

        tvBody.setText(tweet.getBody());
        String timestamp = tweet.getCreatedAt();
        String relative = Tweet.getRelativeTimeAgo(timestamp);
        tvTimeStamp.setText(Tweet.getRelativeTimeAgo(timestamp));

        ivProfileImage.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);
        ivProfileImage.setTag(tweet.getUser());

        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Tweet tweet1 = getItem(position);
                Intent i = new Intent(getContext(), UserActivity.class);
                i.putExtra("user", Parcels.wrap(ivProfileImage.getTag()));
                i.putExtra("bool", true);
                //Toast.makeText(getContext(), tweet.getUser().getName(), Toast.LENGTH_SHORT).show();
                getContext().startActivity(i);
                // your code here
            }
        });


        final ImageButton btnRetweet = (ImageButton) convertView.findViewById(R.id.btnRetweet);
        btnRetweet.setTag(tweet.getUid());
        btnRetweet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                ((TimelineActivity) context1).showRetweetDialog((long) btnRetweet.getTag());
                FragmentManager fm = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                RetweetFragment frag = new RetweetFragment();
                frag.show(fm, "retweet_fragment");
            }
        });



        final ImageButton btnFavorite = (ImageButton) convertView.findViewById(R.id.btnFavorite);
        if (!tweet.getFavorited()) {
            btnFavorite.setImageResource(R.drawable.tweet);
        }
        btnFavorite.setTag(tweet.getUid());
        tvUserName.setTag(tweet.getFavorited());
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                if ((boolean) tvUserName.getTag()) {
                    long i = ((long) btnFavorite.getTag());
                    deFavorite(i);

                    Log.d("sup", "favorited already");
                }
                else {
                    long i = ((long) btnFavorite.getTag());
                    favorite(i);
                    Log.d("sup", "not favorited");
                }
            }
        });

        // 5. Return the view to be inserted into the list
        return convertView;

        //return super.getView(position, convertView, parent);
    }

    public void favorite(long id) {
        TwitterClient client = TwitterApplication.getRestClient();
        //get the current user
        client.favorite(id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject json) {
            }
        });
    }

    public void deFavorite(long id) {
        TwitterClient client = TwitterApplication.getRestClient();
        //get the current user
        client.deFavorite(id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("fail", "success");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject json) {
                Log.d("435345345345", "id");
            }
        });
    }
}
