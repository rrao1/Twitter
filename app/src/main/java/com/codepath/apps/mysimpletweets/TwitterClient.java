package com.codepath.apps.mysimpletweets;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "Ud7LEsqCUCcSJnjB62SR3oSzb";       // Change this
	public static final String REST_CONSUMER_SECRET = "Tc1QfnsGqNOyavBO8csV38rsxjfAeUABx70LbQKDdEBKMs75pK"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://twitterapprr"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here


	//METHOD == NEDPOINT

	//HomeTimeline - Gets us the home timeline
    public void getHomeTimeline(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", 25);
        params.put("since_id", 1);
        //execute the request
        getClient().get(apiUrl, params, handler);
    }

	public void getMentionsTimeline(JsonHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", 25);
		//execute the request
		getClient().get(apiUrl, params, handler);
	}

	public void getUserTimeline(String screenName, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", 25);
		params.put("screen_name", screenName);
		//execute the request
		getClient().get(apiUrl, params, handler);
	}

	public void getUserInfo(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("account/verify_credentials.json");
		getClient().get(apiUrl, null, handler);

	}

	//Post Tweet
    public void postTweet(String status, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", status);
        //execute the request
        getClient().post(apiUrl, params, handler);
    }

	public void getSearch(String query, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("search/tweets.json");
		RequestParams params = new RequestParams();
		params.put("q", query);
		//execute the request
		getClient().get(apiUrl, params, handler);
	}

	public void retweet(String id, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/retweet/" + id + ".json");
		RequestParams params = new RequestParams();
		params.put("id", id);
		//params.put("q", query);
		//execute the request
		getClient().post(apiUrl, params, handler);
	}

	public void favorite(long id, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("favorites/create.json");
		RequestParams params = new RequestParams();
		params.put("id", id);
		getClient().post(apiUrl, params, handler);
	}

	public void deFavorite(long id, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("favorites/destroy.json");
		RequestParams params = new RequestParams();
		params.put("id", id);
		getClient().post(apiUrl, params, handler);
	}

	public void createFriendship(long id, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("friendships/create.json");
		RequestParams params = new RequestParams();
		params.put("user_id", id);
		getClient().post(apiUrl, params, handler);
	}

	public void destroyFriendship(long id, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("friendships/destroy.json");
		RequestParams params = new RequestParams();
		params.put("user_id", id);
		getClient().post(apiUrl, params, handler);
	}

	public void directMessages(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("direct_messages.json");
		RequestParams params = new RequestParams();
		params.put("count", 20);
		getClient().get(apiUrl, params, handler);
	}

	public void getFollowers(long id, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("friends/list.json");
		RequestParams params = new RequestParams();
		params.put("user_id", id);
		getClient().get(apiUrl, params, handler);
	}




//    GET statuses/home_timeline.json
//            count=25
//    since_id=1

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}