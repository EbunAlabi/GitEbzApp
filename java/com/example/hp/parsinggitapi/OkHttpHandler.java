package com.example.hp.parsinggitapi;



import android.app.Activity;
import android.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
/**
 * Created by hp on 8/30/2017.
 */

public class OkHttpHandler extends AsyncTaskLoader<List<Profile>> {
    private String mUrl;
    Activity context;
    OkHttpClient client = new OkHttpClient();
    public OkHttpHandler(Activity context, String url) {
        super(context);
        this.context = context;
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Profile> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<Profile> profiles = new ArrayList<>();
        Request request = new Request.Builder().url(mUrl).build();
        Response response = null;
        Response nextResponse = null;
        try {
            response = client.newCall(request).execute();
            String responseData = response.body().string();
            JSONObject jsonObject = new JSONObject(responseData);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            for (int i = 0; i < jsonArray.length() ; i++) {
                JSONObject user = jsonArray.getJSONObject(i);
                String userUrl = user.getString("url");
                Request nextRequest = new Request.Builder().url(userUrl).build();
                nextResponse = client.newCall(nextRequest).execute();
                String nextResponseData = nextResponse.body().string();
                JSONObject nextJsonObject = new JSONObject(nextResponseData);
                String userBio = nextJsonObject.getString("bio");
                if(userBio=="null") {
                    userBio = "no bio";
                }
                String avatarUrl = nextJsonObject.getString("avatar_url");
                String name = nextJsonObject.getString("name");
                String userName = "@"+nextJsonObject.getString("login");
                String repo = nextJsonObject.getString("public_repos")+" repo";
                String followers = nextJsonObject.getString("followers");
                String  htmlurl = nextJsonObject.getString("html_url");
                String following = nextJsonObject.getString("following");

                Log.d("MYLOG",avatarUrl + userName + name + userUrl + repo+"/n");
                Profile profile = new Profile(avatarUrl, name, userName, userBio, repo, followers, htmlurl, following);
                profiles.add(profile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return profiles;
    }
}

