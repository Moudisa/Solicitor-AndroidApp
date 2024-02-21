package com.apkglobal.taekook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextInputEditText mDisplayName;
    private TextInputEditText mEmail;
    private TextInputEditText mPassword;
    private TextInputEditText mContact;
    private TextInputEditText mAddress;
    private TextView mQualification;
    private Button mCreateBtn;


    String qualification, experience, cases, working;

    //firebase authentication
    private FirebaseAuth mAuth;

    //Progress Dialog
    private ProgressDialog mProgressDialog;

    //firebase realtime database
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

       mAuth = FirebaseAuth.getInstance();

       //Toolbar
        android.support.v7.widget.Toolbar mToolbar = findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       mDisplayName= (TextInputEditText)findViewById(R.id.reg_display_name);
       mEmail = (TextInputEditText)findViewById(R.id.reg_email);
       mPassword = (TextInputEditText)findViewById(R.id.reg_password);
       mContact = (TextInputEditText) findViewById(R.id.reg_number);
       mAddress = (TextInputEditText) findViewById(R.id.reg_address);
       mQualification = (TextView)findViewById(R.id.reg_qualification);
       mCreateBtn = (Button)findViewById(R.id.reg_create_btn);

       mProgressDialog = new ProgressDialog(this);


        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String display_name = mDisplayName.getEditableText().toString().trim();
                String email = mEmail.getEditableText().toString().trim();
                String password = mPassword.getEditableText().toString().trim();
                String number = mContact.getEditableText().toString().trim();
                String address = mAddress.getEditableText().toString().trim();

                if(!TextUtils.isEmpty(display_name) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(password))
                {
                    //Progress Dialog
                    mProgressDialog.setTitle("Registering User");
                    mProgressDialog.setMessage("Please Wait...");
                    mProgressDialog.show();

                    register_user(display_name, email, password, number, address, qualification, experience, cases, working);
                }



            }
        });

        //Spinner
        Spinner spinner = (Spinner) findViewById(R.id.reg_edit_qualification);
        spinner.setOnItemSelectedListener(this);

        //Drop Down Elements
        ArrayList category = new ArrayList();
        category.add("Select Qualification");
        category.add("Lawyer - Practice");
        category.add("CA");
        category.add("Law Student");
        category.add("Company Secretary");
        category.add("HR");
        category.add("Finance Professional");
        category.add("Lawyer - Job");
        category.add("Law Officer");
        category.add("Business");
        category.add("Others");

        //Creating array adapter
        ArrayAdapter dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, category);

        //Drop down style will be listView with with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

        //attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


    }

    //Spinner

    //@Override
    public void onItemSelected(AdapterView parent, View view, int position, long id){
        //getting selected item
        qualification = parent.getItemAtPosition(position).toString();
    }

    public void onNothingSelected(AdapterView arg0){

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void register_user(final String display_name, final String email, String password, final String contact,
                               final String address, final String qualification, final String experience,
                               final String cases, final String working) {

                 mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if(task.isSuccessful())
                         {
                             //getting user ID from firebase
                             FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                             String uid = current_user.getUid();

                             mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                             HashMap<String, String> userMap = new HashMap<>();
                             userMap.put("name", display_name);
                             userMap.put("email", email);
                             userMap.put("contact", contact);
                             userMap.put("address", "default");
                             userMap.put("experience", "default");
                             userMap.put("cases", "default");
                             userMap.put("working", "default");
                             userMap.put("qualification", qualification);
                             userMap.put("image", "default");
                             userMap.put("thumb_image", "default");
                             userMap.put("uid", uid);

                             mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Void> task) {
                                     if(task.isSuccessful())
                                     {
                                         mProgressDialog.dismiss();
                                         Intent main_intent = new Intent(RegisterActivity.this, MainActivity.class);

                                         //on pressing back button in main page, user doesnt go back to main page, instead exits frm app
                                         main_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                         startActivity(main_intent);
                                         finish();
                                     }
                                 }
                             });


                         }
                         else
                         {
                             mProgressDialog.hide();
                             Toast.makeText(RegisterActivity.this, "Cannot Sign In. Please check the form and try again.", Toast.LENGTH_SHORT).show();
                         }
                     }
                 });
     }

}

