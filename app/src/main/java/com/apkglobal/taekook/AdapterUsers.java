package com.apkglobal.taekook;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;



public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.MyHolder>{

    Context context;
    List<Users> userList;

    //constructor
    public AdapterUsers(Context context, List<Users> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //inflate layout (users_single_layout.xml)
        View view = LayoutInflater.from(context).inflate(R.layout.users_single_layout, viewGroup, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        //get data
        String userImage = userList.get(i).getImage();
        final String Username = userList.get(i).getName();
        String userQualification = userList.get(i).getQualification();
        final String hisUID = userList.get(i).getUid();



        //set data
        myHolder.mName.setText(Username);
        myHolder.mStatus.setText(userQualification);

        try{

            Picasso.get().load(userImage).placeholder(R.drawable.avatar).into(myHolder.mAvatar);
        }
        catch(Exception e){

        }

        //handle item check
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("hisUid", hisUID);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    //view holder class

    class MyHolder extends RecyclerView.ViewHolder {

        ImageView mAvatar;
        TextView mName, mStatus;

        public MyHolder(@NonNull View itemView) {

            super(itemView);

            //init views
            mAvatar = itemView.findViewById(R.id.user_single_image);
            mName = itemView.findViewById(R.id.user_single_name);
            mStatus = itemView.findViewById(R.id.user_single_qualification);
        }
    }
}
