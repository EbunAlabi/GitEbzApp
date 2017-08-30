package com.example.hp.parsinggitapi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by hp on 8/30/2017.
 */

public class DetailActivity extends AppCompatActivity {

    TextView Link, Username, Followers, Bio, Following;
    Toolbar mActionBarToolbar;
    ImageView imageView;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = (ImageView) findViewById(R.id.user_image_header);
        Username = (TextView) findViewById(R.id.username);
        Link = (TextView) findViewById(R.id.link);
        Followers= (TextView) findViewById(R.id.followers_view);
        Bio = (TextView) findViewById(R.id.userbio);
        Following = (TextView) findViewById(R.id.following_view);

        //initializing the button view and finding its view
        final Button moreButton = (Button) findViewById(R.id.more_button);

        String bio = getIntent().getExtras().getString("bio");
        String followers = getIntent().getExtras().getString("followers");
        String username = getIntent().getExtras().getString("login");
        String avatarUrl = getIntent().getExtras().getString("avatar_url");
        final String htmlurl = getIntent().getExtras().getString("html_url");
        String following = getIntent().getExtras().getString("following");

        Log.e("DETAIL","This "+avatarUrl);

        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send an implicit intent to open url in browser
                openLink(htmlurl);
            }
        });

        //making link clickable
        Link.setText(htmlurl);
        Linkify.addLinks(Link, Linkify.WEB_URLS);

        //setting text to the corresponding views

        Username.setText(username);
        Followers.setText(followers);
        Bio.setText(bio);
        Following.setText(following);

        Picasso.with(this)
                .load(avatarUrl)
                .resize(300,300)
                .transform(new CircleTransform())
                .placeholder(R.drawable.avatar)
                .into(imageView);

        /*
        Glide.with(this)
                .load(avatarUrl)
                .placeholder(R.drawable.avatar)
                .into(imageView);
        */

        getSupportActionBar().setTitle("User Details");

    }
    //the share implementation
    private Intent createShareForecastIntent(){
        String username = getIntent().getExtras().getString("login");
        String link = getIntent().getExtras().getString("html_url");
        String avatarUrl = getIntent().getExtras().getString("avatar_url");
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText("Check out this awesome developer @" +username + ", " + link)
                .getIntent();

        return shareIntent;



    }

// inflate menu which, the detail.xml that houses the share icon





    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        menuItem.setIntent(createShareForecastIntent());
        return true;
    }


    private void openLink(String htmlurl) {
        Uri userUri = Uri.parse(htmlurl);
        Intent intent = new Intent(Intent.ACTION_VIEW, userUri);
        startActivity(intent);
    }


}
