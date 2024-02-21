package com.apkglobal.taekook;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;

public class AddPostActivity extends AppCompatActivity {

    //views
    EditText titleEt;
    EditText descriptionEt;
    Button uploadBtn;

    //firebase
    FirebaseAuth firebaseAuth;
    DatabaseReference usersDbRef;

    //User info
    String name, uid, dp, email;

    //Progress Bar
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        //Toolbar
        android.support.v7.widget.Toolbar mToolbar = findViewById(R.id.addPost_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Post a Query");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Progress Dialog
        pd = new ProgressDialog(this);

        //init views
        titleEt = (EditText) findViewById(R.id.pTitleEt);
        descriptionEt = (EditText) findViewById(R.id.pDescriptionEt);
        uploadBtn = (Button) findViewById(R.id.pUploadBtn);


        firebaseAuth = FirebaseAuth.getInstance();
        //get Current User
        FirebaseUser user = firebaseAuth.getCurrentUser();
        email = user.getEmail();
        uid = user.getUid();


        //get info of current user
        usersDbRef = FirebaseDatabase.getInstance().getReference("Users");
        Query query = usersDbRef.orderByChild("email").equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    name = ""+ ds.child("name").getValue();
                    email = ""+ ds.child("email").getValue();
                    dp = ""+ ds.child("image").getValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //Upload Button Click Listener
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get data from edit texts
                String title = titleEt.getText().toString().trim();
                String description = descriptionEt.getText().toString().trim();

                if(TextUtils.isEmpty(title)){
                    Toast.makeText(AddPostActivity.this, "Enter Title", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(description)){
                    Toast.makeText(AddPostActivity.this, "Enter Description", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    uploadData(title, description);
                }
            }
        });
    }

    private void uploadData(String title, String description) {
        pd.setMessage("Posting Your Query!");
        pd.show();

        String timeStamp = String.valueOf(System.currentTimeMillis());
        String filePathAndName = "Posts/" + "post_" + timeStamp;

        HashMap<Object, String> hashMap = new HashMap<>();
        hashMap.put("pId", timeStamp );
        hashMap.put("uid", uid );
        hashMap.put("name", name);
        hashMap.put("email", email);
        hashMap.put("dp", "default");
        hashMap.put("pTime", timeStamp );
        hashMap.put("pTitle", title );
        hashMap.put("pDescription", description );

        //path to store
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
        //put data in this reference
        ref.child(timeStamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //success n adding data to database
                pd.dismiss();
                Toast.makeText(AddPostActivity.this, "Query Uploaded", Toast.LENGTH_SHORT).show();

                //reset views
                titleEt.setText("");
                descriptionEt.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //failed adding post
                pd.dismiss();
                Toast.makeText(AddPostActivity.this, "Your Query could not be uplaoded. Try again later!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}