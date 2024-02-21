package com.apkglobal.taekook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    //Android Layout
    private ImageView mDisplayImage;
    private TextView mName;
    private TextView mQual;
    private TextView mEmail;
    private TextView mContact;
    private TextView mAddress;
    private TextView mExperience;
    private TextView mCases;
    private TextView mWorking;
    private Button mProfileBtn;
    private DatabaseReference mUsersDatabase;
    private FirebaseUser mCurrentUser;

    //Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usersDbRef;

    String hisUid;
    String hisImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String user_id = getIntent().getStringExtra("user_id");

        mDisplayImage = (ImageView) findViewById(R.id.profile_image);
        mName = (TextView)findViewById(R.id.profile_display_name);
        mQual = (TextView)findViewById(R.id.profile_qualification);
        mEmail = (TextView)findViewById(R.id.profile_email);
        mContact = (TextView)findViewById(R.id.profile_contact);
        mAddress = (TextView)findViewById(R.id.profile_address);
        mExperience = (TextView)findViewById(R.id.profile_experience);
        mCases = (TextView)findViewById(R.id.profile_cases);
        mWorking = (TextView)findViewById(R.id.profile_working);
        mProfileBtn = (Button) findViewById(R.id.profile_message);

        Intent intent = getIntent();
        hisUid = intent.getStringExtra("hisUid");

        //firebase auth instance
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        usersDbRef = firebaseDatabase.getReference("Users");

        //search user to get that users info
        Query userQuery = usersDbRef.orderByChild("uid").equalTo(hisUid);

        //get user details
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //check until required info is received
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    //get data
                    String name = "" + ds.child("name").getValue();
                    String qualification = "" + ds.child("qualification").getValue();
                    String email = "" + ds.child("email").getValue();
                    String contact = "" + ds.child("contact").getValue();
                    String address = "" + ds.child("address").getValue();
                    String experience = "" + ds.child("experience").getValue();
                    String cases = "" + ds.child("cases").getValue();
                    String working = "" + ds.child("working").getValue();
                    hisImage = "" + ds.child("image").getValue();

                    //set data
                    mName.setText(name);
                    mQual.setText(qualification);
                    mEmail.setText(email);
                    mContact.setText(contact);
                    mAddress.setText(address);
                    mExperience.setText(experience);
                    mCases.setText(cases);
                    mWorking.setText(working);
                    try{
                        Picasso.get().load(hisImage).placeholder(R.drawable.avatar).into(mDisplayImage);
                    }
                    catch(Exception e){
                        Picasso.get().load(R.drawable.avatar).into(mDisplayImage);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hisUID = hisUid;
                Intent intent = new Intent(ProfileActivity.this, MessageActivity.class);
                intent.putExtra("hisUid", hisUID);
                startActivity(intent);
            }
        });

    }
}