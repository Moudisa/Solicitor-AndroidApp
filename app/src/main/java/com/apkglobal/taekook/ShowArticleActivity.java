package com.apkglobal.taekook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ShowArticleActivity extends AppCompatActivity {

    //to get detail of user and post
    String myUid, myEmail, myName, myDp, postId, hisName, hisDp;
    String hisUid;

    //views
    ImageView uPictureIv;
    TextView uNameTv, pTimeTv, pTitleTv, pDescriptionTv;
    Button shareBtn;

    List<Articles> articlesList;
    AdapterArticles adapterArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_article);

        //Toolbar
        android.support.v7.widget.Toolbar mToolbar = findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Article");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //firebase current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        myUid = user.getUid();
        myEmail = user.getEmail();

        //get id of post using intent
        Intent intent = getIntent();
        postId = intent.getStringExtra("postId");

        //init views
        uPictureIv = findViewById(R.id.uPictureIv);
        uNameTv = findViewById(R.id.uNameTv);
        pTimeTv = findViewById(R.id.pTimeTv);
        pTitleTv = findViewById(R.id.pTitleTv);
        pDescriptionTv = findViewById(R.id.pDescriptionTv);
        shareBtn = findViewById(R.id.shareBtn);

        loadArticleInfo();
    }

    private void loadArticleInfo() {
        //get article using id of article
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Articles");
        Query query = ref.orderByChild("pId").equalTo(postId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //keep checking posts until required post is found
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    //get data
                    String pTitle = "" + ds.child("pTitle").getValue();
                    String pDescr = "" + ds.child("pDescription").getValue();
                    String pTimeStamp = "" + ds.child("pTime").getValue();
                    hisDp = "" + ds.child("dp").getValue();
                    String uid = "" + ds.child("uid").getValue();
                    //String uEmail = "" + ds.child("email").getValue();
                    hisName = "" + ds.child("name").getValue();

                    //convert timestamp to proper time
                    Calendar calendar = Calendar.getInstance(Locale.getDefault());
                    calendar.setTimeInMillis(Long.parseLong(pTimeStamp));
                    String pTime = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();

                    //set data
                    pTitleTv.setText(pTitle);
                    pDescriptionTv.setText(pDescr);
                    pTimeTv.setText(pTime);

                    uNameTv.setText(hisName);

                    //image of user who posted
                    try{
                        Picasso.get().load(hisDp).placeholder(R.drawable.avatar).into(uPictureIv);
                    }
                    catch(Exception e){
                        Picasso.get().load(R.drawable.avatar).into(uPictureIv);
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}