package com.apkglobal.taekook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class PostDetailActivity extends AppCompatActivity {

    //to get detail of user and post
    String myUid, myEmail, myName, myDp, postId, hisName, hisDp;

    //Progress bar
    ProgressDialog pd;

    //views
    ImageView uPictureIv;
    TextView uNameTv, pTimeTv, pTitleTv, pDescriptionTv;
    Button shareBtn;
    LinearLayout profileLayout;
    RecyclerView recyclerView;

    List<ModelComment> commentList;
    AdapterComments adapterComments;

    //add comments views
    EditText commentEt;
    ImageButton sendBtn;
    ImageView cAvatarIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        //Toolbar
        android.support.v7.widget.Toolbar mToolbar = findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("User Query");
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
        profileLayout = findViewById(R.id.profileLayout);
        recyclerView = findViewById(R.id.recyclerView);

        commentEt = findViewById(R.id.commentEt);
        sendBtn = findViewById(R.id.sendBtn);
        cAvatarIv = findViewById(R.id.cAvatarIv);

        loadPostInfo();

        loadUserInfo();

        loadComments();

        //send comment button click
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postComment();
            }
        });

    }

    private void loadComments() {
        //layout for recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        //set layout to recycler view
        recyclerView.setLayoutManager(layoutManager);

        //init comments list
        commentList = new ArrayList<>();

        //path of the post, to get it's comments
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts").child(postId).child("Comments");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                commentList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    ModelComment modelComment = ds.getValue(ModelComment.class);

                    commentList.add(modelComment);
                    //setup adapter
                    adapterComments = new AdapterComments(getApplicationContext(), commentList);

                    //set adapter
                    recyclerView.setAdapter(adapterComments);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void postComment() {
        pd = new ProgressDialog(this);
        pd.setMessage("Adding Comment..");

        //get data from comment edit text
        String comment = commentEt.getText().toString().trim();
        //validate
        if(TextUtils.isEmpty(comment)){
            //no value is entered
            Toast.makeText(this, "Write a comment!", Toast.LENGTH_SHORT).show();
            return;
        }

        String timeStamp = String.valueOf(System.currentTimeMillis());

        //each post will have a child "Comments"
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts").child(postId).child("Comments");

        HashMap<String, Object> hashMap = new HashMap<>();
        //put info into hashmap
        hashMap.put("cId", timeStamp);
        hashMap.put("comment", comment);
        hashMap.put("timestamp", timeStamp);
        hashMap.put("uid", myUid);
        hashMap.put("email", myEmail);
        hashMap.put("dp", myDp);
        hashMap.put("name", myName);

        //put this data in db
        ref.child(timeStamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //added
                pd.dismiss();
                Toast.makeText(PostDetailActivity.this, "Comment Added", Toast.LENGTH_SHORT).show();
                commentEt.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //failed
                pd.dismiss();
                Toast.makeText(PostDetailActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void loadUserInfo() {
        //get current user info
        Query myRef = FirebaseDatabase.getInstance().getReference("Users");
        myRef.orderByChild("uid").equalTo(myUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    myName = "" + ds.child("name").getValue();
                    myDp = "" + ds.child("image").getValue();

                    //set data
                    try{
                        Picasso.get().load(R.drawable.avatar).into(cAvatarIv);
                    }
                    catch(Exception e){
                        Picasso.get().load(R.drawable.avatar).into(cAvatarIv);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void loadPostInfo() {
        //get post using id of post
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
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
                    String uEmail = "" + ds.child("email").getValue();
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