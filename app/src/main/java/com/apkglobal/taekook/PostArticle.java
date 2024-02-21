package com.apkglobal.taekook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PostArticle extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //UI
    private TextView mTitle;
    private TextView mCategory;
    private TextView mDesc;
    private Button mPost;
    private Button mReset;
    private EditText etTitle;
    private EditText etArticle;

    String pTitle;
    String pCategory;
    String pArticle;
    Spinner spinner;
    String name;
    String uid, email;

    //Firebase
    private FirebaseAuth firebaseAuth;
    private DatabaseReference usersDbRef;
    private FirebaseDatabase firebaseDatabase;

    //Progress Bar
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_article);

        //Toolbar
        android.support.v7.widget.Toolbar mToolbar = findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create Article");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Progress Dialog
        pd = new ProgressDialog(this);

        //UI
        mTitle = (TextView) findViewById(R.id.article_title);
        mCategory = (TextView) findViewById(R.id.article_category);
        mDesc = (TextView) findViewById(R.id.article_desc);
        mPost = (Button) findViewById(R.id.post_btn);
        mReset = (Button) findViewById(R.id.reset_btn);
        etTitle = (EditText) findViewById(R.id.edit_title);
        etArticle = (EditText) findViewById(R.id.edit_article);

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
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etTitle.setText("");
                etArticle.setText("");
                spinner.setSelection(0);
            }
        });

        mPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //getdata
                pTitle = etTitle.getText().toString().trim();
                pArticle = etArticle.getText().toString().trim();


                if(!TextUtils.isEmpty(pTitle) || !TextUtils.isEmpty(pArticle))
                {

                    post_article(pTitle, pCategory, pArticle);
                }
                else{
                    Toast.makeText(PostArticle.this, "Cannot post!", Toast.LENGTH_SHORT).show();
                }
            }
        });



        //Spinner
        spinner = (Spinner) findViewById(R.id.edit_category);
        spinner.setOnItemSelectedListener(this);

            //Drop Down Elements
             ArrayList cat = new ArrayList();
             cat.add("Select Category");
             cat.add("Corporate Law");
             cat.add("Civil Law");
             cat.add("Constitutional Law");
             cat.add("Criminal Law");
             cat.add("Family Law");
             cat.add("Labour & Service Law");
             cat.add("Legal Documents");
             cat.add("Intellectual Property Rights");
             cat.add("Property Law");
             cat.add("Taxation");
             cat.add("Others");

             //Creating array adapter
        ArrayAdapter dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, cat);

        //Drop down style will be listView with with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

        //attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);



    }

    private void post_article(final String topic, final String category, final String article) {
        pd.setMessage("Posting Your Article!");
        pd.show();

        //String timeStamp = String.valueOf(System.currentTimeMillis());
        String timeStamp = String.valueOf(System.currentTimeMillis());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("pId", timeStamp );
        hashMap.put("name", name);
        hashMap.put("pTitle", topic);
        hashMap.put("pCategory", category);
        hashMap.put("pDescription", article);
        hashMap.put("uid", uid);
        hashMap.put("email", email);
        hashMap.put("pTime", timeStamp);
       //path to store
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Articles");
        //put data in this reference
        ref.child(timeStamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //success n adding data to database
                pd.dismiss();
                Toast.makeText(PostArticle.this, "Article Published", Toast.LENGTH_SHORT).show();

                //reset
                etTitle.setText("");
                etArticle.setText("");
                spinner.setSelection(0);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //failed adding post
                pd.dismiss();
                Toast.makeText(PostArticle.this, "Your Article could not be published. Try again later!", Toast.LENGTH_SHORT).show();
            }
        });



    }

    //@Override
    public void onItemSelected(AdapterView parent, View view, int position, long id){
        //getting selected item
        pCategory = parent.getItemAtPosition(position).toString();
    }

    public void onNothingSelected(AdapterView arg0){

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}