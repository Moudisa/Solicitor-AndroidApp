package com.apkglobal.taekook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

//import android.support.annotation.NonNull;
//import android.support.design.widget.TextInputEditText;
//import android.support.design.widget.TextInputLayout;

public class StatusActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //TextViews
    private TextView mDisplayName;
    private TextView mEmail;
    private TextView mContact;
    private TextView mAddress;
    private TextView mExperience;
    private TextView mCases;
    private TextView mWorking;
    private TextView mQualification;

    //Text Inputs
    private TextInputEditText mEditName;
    private TextInputEditText mEditEmail;
    private TextInputEditText mEditContact;
    private TextInputEditText mEditAddress;
    private TextInputEditText mEditExperience;
    private TextInputEditText mEditCases;
    private TextInputEditText mEditWorking;

    private Button mSaveBtn;


    //Firebase
    private DatabaseReference mStatusDatabase;
    private FirebaseUser mCurrentUser;

    //Progress
    private ProgressDialog mProgress;

    String qualification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        //Firebase
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        mStatusDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

        //Toolbar
       // android.support.v7.widget.Toolbar mToolbar = findViewById(R.id.status_appBar);
      //  setSupportActionBar(mToolbar);
       // getSupportActionBar().setTitle("Account Status");
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //String status_value = getIntent().getStringExtra("status_value");

        String name_value = getIntent().getStringExtra("name_value");
        String email_value = getIntent().getStringExtra("email_value");
        String contact_value = getIntent().getStringExtra("contact_value");
        String address_value = getIntent().getStringExtra("address_value");
        String experience_value = getIntent().getStringExtra("experience_value");
        String cases_value = getIntent().getStringExtra("cases_value");
        String working_value = getIntent().getStringExtra("working_value");


        //init components
        mDisplayName = (TextView)findViewById(R.id.status_name);
        mEmail = (TextView)findViewById(R.id.status_email);
        mContact = (TextView)findViewById(R.id.status_contact);
        mAddress = (TextView)findViewById(R.id.status_address);
        mExperience = (TextView)findViewById(R.id.status_experience);
        mCases = (TextView)findViewById(R.id.status_cases);
        mWorking = (TextView)findViewById(R.id.status_working);
        mQualification = (TextView)findViewById(R.id.status_qualification);

        //init pt2
        mEditName = (TextInputEditText)findViewById(R.id.status_edit_name);
        mEditEmail = (TextInputEditText)findViewById(R.id.status_edit_email);
        mEditContact = (TextInputEditText)findViewById(R.id.status_edit_contact);
        mEditAddress = (TextInputEditText)findViewById(R.id.status_edit_address);
        mEditExperience = (TextInputEditText)findViewById(R.id.status_edit_experience);
        mEditCases = (TextInputEditText)findViewById(R.id.status_edit_cases);
        mEditWorking = (TextInputEditText)findViewById(R.id.status_edit_working);
       // mEditQualification = (TextInputEditText)findViewById(R.id.status_edit_qualification);

        mSaveBtn = (Button) findViewById(R.id.status_save_btn);

       // mStatus.getEditText().setText(status_value);

        mEditName.setText(name_value);
        mEditEmail.setText(email_value);
        mEditContact.setText(contact_value);
        mEditAddress.setText(address_value);
        mEditExperience.setText(experience_value);
        mEditCases.setText(cases_value);
        mEditWorking.setText(working_value);
        //mEditAddress.setText(address_value);

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Progress
                mProgress = new ProgressDialog(StatusActivity.this);
                mProgress.setTitle("Saving Changes");
                mProgress.setMessage("Please wait while we save the changes.");
                mProgress.show();


                String name = mEditName.getText().toString();
                String email = mEditEmail.getText().toString();
                String number = mEditContact.getText().toString();
                String address = mEditAddress.getText().toString();
                String experience = mEditExperience.getText().toString();
                String cases = mEditCases.getText().toString();
                String working = mEditWorking.getText().toString();

                mStatusDatabase.child("address").setValue(address).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            mProgress.dismiss();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mStatusDatabase.child("name").setValue(name).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            mProgress.dismiss();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mStatusDatabase.child("email").setValue(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            mProgress.dismiss();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mStatusDatabase.child("contact").setValue(number).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            mProgress.dismiss();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mStatusDatabase.child("qualification").setValue(qualification).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            mProgress.dismiss();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mStatusDatabase.child("experience").setValue(experience).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            mProgress.dismiss();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mStatusDatabase.child("cases").setValue(cases).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            mProgress.dismiss();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                mStatusDatabase.child("working").setValue(working).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            mProgress.dismiss();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Toast.makeText(getApplicationContext(), "Your changes were saved successfully.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(StatusActivity.this, SettingsActivity.class);
                startActivity(intent);

            }
        });

        //Spinner
        Spinner spinner = (Spinner) findViewById(R.id.status_edit_qualification);
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

}
