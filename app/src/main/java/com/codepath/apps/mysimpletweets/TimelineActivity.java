package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweets.fragments.HomeTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.MentionsTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.NewTweetFragment;
import com.codepath.apps.mysimpletweets.fragments.RetweetFragment;
import com.codepath.apps.mysimpletweets.fragments.SearchFragment;
import com.codepath.apps.mysimpletweets.models.Tweet;

public class TimelineActivity extends AppCompatActivity implements NewTweetFragment.NewTweetDialogListener {
    NewTweetFragment newTweetFragment;
    HomeTimelineFragment homeTimelineFragment;
    SearchFragment searchFragment;
    public long tweetId;
    public String query1;

    public int fragNumber;
    private SwipeRefreshLayout swipeContainer;
    RetweetFragment retweetFragment;

    ViewPager vpPager;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);


        //get the viewpager
        vpPager = (ViewPager) findViewById(R.id.viewpager);
        //set the viewpager adapter for the pager
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));
        //find the pager sliding tabs
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        //attach the pager tabstrip to the viewpager
        tabStrip.setViewPager(vpPager);

        // access the fragment
    }

    public void retweet(View view) {
    }


    //Return the order of the fragments in the viewpager
    public class TweetsPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 3;
        private String tabTitles[] = { "Home", "Mentions", "Search"};

        //Adapter gets the manager insert or remove fragment from activity
        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //the order and creation of fragments within the pager
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                homeTimelineFragment = new HomeTimelineFragment();
                fragNumber = 0;
                return homeTimelineFragment;
            }
            else if (position == 1) {
                fragNumber = 1;
                return new MentionsTimelineFragment();
            }
            else if (position == 2) {
                fragNumber = 3;
                searchFragment = new SearchFragment();
                return searchFragment;
            }
            else {
                return null;
            }
        }

        //return the tabl title
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        //how many fragments are there to swipe between
        @Override
        public int getCount() {
            return PAGE_COUNT;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                query1 = query;

                Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();


                searchView.clearFocus();
                vpPager.setCurrentItem(2);
                searchFragment.populateTimeline();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void onProfileView(MenuItem mi) {
        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra("bool", false);
        startActivity(i);
    }

    public void showEditDialog(MenuItem mi) {
        FragmentManager fm = getSupportFragmentManager();
        newTweetFragment = NewTweetFragment.newInstance("");
        //NewTweetFragment newTweetFragment = NewTweetFragment.newInstance("");
        newTweetFragment.show(fm, "tweet_fragment");
    }

    public void showRetweetDialog(long id) {
        Log.d("idtimeline", id +"");
        tweetId = id;
        FragmentManager fm = getSupportFragmentManager();
        RetweetFragment frag = new RetweetFragment();
        frag.show(fm, "retweet_fragment");
    }

    public void dismiss(View view) {
        newTweetFragment.dismiss();
    }

    @Override
    public void onFinishTweetDialog(Tweet tweet) {
        TwitterClient client = TwitterApplication.getRestClient();
        homeTimelineFragment.addNewTweet(tweet);

    }

}
