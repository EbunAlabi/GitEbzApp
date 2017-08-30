package com.example.hp.parsinggitapi;


import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hp on 8/30/2017.
 */

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Profile>> {

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;
    private Button mRetry;
    private static String url ="https://api.github.com/search/users?q=language:java+location:lagos";
    ProfileAdapter profileAdapter;
    View loadingIndicator;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loadingIndicator = findViewById(R.id.loading_indicator);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        mRetry = (Button) findViewById(R.id.retry);

        mRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    mEmptyStateTextView.setVisibility(View.GONE);
                    mRetry.setVisibility(View.GONE);
                    loadingIndicator.setVisibility(View.VISIBLE);
                    LoaderManager loaderManager = getLoaderManager();
                    loaderManager.initLoader(1, null, MainActivity.this);
                }else {
                    Toast.makeText(MainActivity.this,"Please, switch on Network",Toast.LENGTH_SHORT).show();
                }
            }
        });

        ListView listview = (ListView) findViewById(R.id.list);
        listview.setEmptyView(mEmptyStateTextView);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Profile user =  profileAdapter.getItem(position);
                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                String username = user.getMUserName();
                String followers = user.getMFollowers();
                String avatarUrl= user.getMProfileResourceId();
                String bio = user.getMBio();
                String htmlurl = user.getMUserUrl();
                String following = user.getmFollowing();

                intent.putExtra("bio", bio);
                intent.putExtra("login",username );
                intent.putExtra("followers", followers);
                intent.putExtra("avatar_url",avatarUrl );
                intent.putExtra("html_url",htmlurl);
                intent.putExtra("following", following);
                Log.e("DETAIL","This "+username);
                //based on item ad info to intent
                startActivity(intent);
            }
        });



        profileAdapter = new ProfileAdapter(this, new ArrayList<Profile>());
        listview.setAdapter(profileAdapter);

        // If there is a network connection, fetch data
        if (isNetworkAvailable()) {
            mRetry.setVisibility(View.GONE);
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(1, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
            mEmptyStateTextView.setVisibility(View.VISIBLE);
            mRetry.setVisibility(View.VISIBLE);
        }

    }
    private boolean isNetworkAvailable(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }


    @Override
    public Loader<List<Profile>> onCreateLoader(int id, Bundle args) {
        return new OkHttpHandler(this,url);
    }

    @Override
    public void onLoadFinished(Loader<List<Profile>> loader, List<Profile> data) {
        // Hide loading indicator because the data has been loaded
        loadingIndicator.setVisibility(View.GONE);
        // Set empty state text to display "No profile found."

        if (data.isEmpty()){
            mEmptyStateTextView.setText(R.string.no_profiles);
            mEmptyStateTextView.setVisibility(View.VISIBLE);
            mRetry.setVisibility(View.VISIBLE);
        }else {
            mEmptyStateTextView.setVisibility(View.GONE);
            mRetry.setVisibility(View.GONE);
            profileAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Profile>> loader) {
        // Loader reset, so we can clear out our existing data.
        profileAdapter.addAll(new ArrayList<Profile>());
    }
}

