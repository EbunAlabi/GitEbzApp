package com.example.hp.parsinggitapi;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hp on 8/30/2017.
 */

public class ProfileAdapter extends ArrayAdapter<Profile> {
    Activity context;
    public ProfileAdapter(Activity context, ArrayList<Profile> profile) {
        super(context, 0,profile);
        this.context = context;
    }

    private class ViewHolder{
        TextView fullName;
        TextView userName;
        TextView followers;
        CircleImageView profileIcon;


    }


    @Nullable
    @Override
    public Profile getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Profile currentProfile = getItem(position);





        View itemView = convertView;
        if(itemView == null){
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
            holder = new ViewHolder();
            holder.profileIcon = (CircleImageView) itemView.findViewById(R.id.profile_icon);
            holder.fullName = (TextView) itemView.findViewById(R.id.txt_full_name);
            holder.userName = (TextView) itemView.findViewById(R.id.txt_user_name);

            holder.followers = (TextView) itemView.findViewById(R.id.txt_followers);
            itemView.setTag(holder);
        }else {
            holder = (ViewHolder) itemView.getTag();
        }

        Picasso.with(context).load(currentProfile
                .getMProfileResourceId())
                .resize(100,100)
                .placeholder(R.drawable.avatar)
                .into(holder.profileIcon);
        holder.fullName.setText(currentProfile.getMFullName());
        holder.userName.setText(currentProfile.getMUserName());
        holder.followers.setText(currentProfile.getMFollowers() + " followers");






        return itemView;
    }


}

