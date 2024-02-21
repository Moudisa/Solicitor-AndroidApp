package com.apkglobal.taekook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    //Android Layout
    private CircleImageView mDisplayImage;
    private TextView mName;
    private TextView mQual;
    private TextView mEmail;
    private TextView mContact;
    private TextView mAddress;
    private TextView mExperience;
    private TextView mCases;
    private TextView mWorking;
    private Button mImageBtn;
    private Button mProfileBtn;
    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mDisplayImage = (CircleImageView) findViewById(R.id.settings_image);
        mName = (TextView)findViewById(R.id.settings_display_name);
        mImageBtn = (Button)findViewById(R.id.settings_img_btn);
        mQual = (TextView)findViewById(R.id.settings_qualification);
        mEmail = (TextView)findViewById(R.id.settings_email);
        mContact = (TextView)findViewById(R.id.settings_contact);
        mAddress = (TextView)findViewById(R.id.settings_address);
        mExperience = (TextView)findViewById(R.id.settings_experience);
        mCases = (TextView)findViewById(R.id.settings_cases);
        mWorking = (TextView)findViewById(R.id.settings_working);
        mProfileBtn = (Button) findViewById(R.id.settings_profile_btn);


        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();



        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    //get data
                    String name = dataSnapshot.child("name").getValue().toString();
                    String email = dataSnapshot.child("email").getValue().toString();
                    String contact = dataSnapshot.child("contact").getValue().toString();
                    String address = dataSnapshot.child("address").getValue().toString();
                    String experience = dataSnapshot.child("experience").getValue().toString();
                    String cases = dataSnapshot.child("cases").getValue().toString();
                    String working = dataSnapshot.child("working").getValue().toString();
                    String qualification = dataSnapshot.child("qualification").getValue().toString();
                    String image = dataSnapshot.child("image").getValue().toString();
                    String thumb_image = dataSnapshot.child("thumb_image").getValue().toString();


                    //set data
                    mName.setText(name);
                    mQual.setText(qualification);
                    mEmail.setText(email);
                    mContact.setText(contact);
                    mAddress.setText(address);
                    mExperience.setText(experience);
                    mCases.setText(cases);
                    mWorking.setText(working);
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name_value = mName.getText().toString();
                String qualification_value = mQual.getText().toString();
                String email_value = mEmail.getText().toString();
                String contact_value = mContact.getText().toString();
                String address_value = mAddress.getText().toString();
                String experience_value = mExperience.getText().toString();
                String cases_value = mCases.getText().toString();
                String working_value = mWorking.getText().toString();



                Intent status_intent = new Intent(SettingsActivity.this, StatusActivity.class);
               // status_intent.putExtra("status_value", status_value);
                status_intent.putExtra("name_value", name_value);
                status_intent.putExtra("qualification_value", qualification_value);
                status_intent.putExtra("email_value", email_value);
                status_intent.putExtra("contact_value", contact_value);
                status_intent.putExtra("address_value", address_value);
                status_intent.putExtra("experience_value", experience_value);
                status_intent.putExtra("cases_value", cases_value);
                status_intent.putExtra("working_value", working_value);
                startActivity(status_intent);
            }
        });
    }
}
