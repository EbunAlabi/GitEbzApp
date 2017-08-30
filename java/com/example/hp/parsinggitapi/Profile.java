package com.example.hp.parsinggitapi;

import java.util.ArrayList;

/**
 * Created by hp on 8/30/2017.
 */

public class Profile extends ArrayList {
    private String mProfileResourceId;
    private String mFullName;
    private String mUserName;
    private String mBio;
    private String mTotalRepo;
    private  String mFollowers;
    private  String mUserUrl;
    private  String mFollowing;


    public Profile(String profileResourceId,
                   String fullName, String userName,
                   String bio, String totalRepo, String followers,
                   String htmlurl, String following ){
        mProfileResourceId = profileResourceId;
        mFullName = fullName;
        mUserName = userName;
        mBio = bio;
        mTotalRepo = totalRepo;
        mFollowers = followers;
        mUserUrl = htmlurl;
        mFollowing = following;

    }

    public String getMProfileResourceId(){
        return mProfileResourceId;
    }
    public String getMFullName(){return mFullName;}
    public String getMUserName(){
        return mUserName;
    }
    public String getMBio(){
        return mBio;
    }
    public String getMTotalRepo(){
        return mTotalRepo;
    }
    public String getMUserUrl(){
        return mUserUrl;
    }
    public String getMFollowers(){return mFollowers; }
    public String getmFollowing(){return  mFollowing;}



}

