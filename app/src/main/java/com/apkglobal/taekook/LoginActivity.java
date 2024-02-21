package com.apkglobal.taekook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Button mLoginBtn;
    private TextInputEditText mEmail;
    private TextInputEditText mPassword;
    private FirebaseAuth mAuth;

    //Progress Dialog
    private ProgressDialog mLoginProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Toolbar
        android.support.v7.widget.Toolbar mToolbar = findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        mEmail = (TextInputEditText)findViewById(R.id.login_email);
        mPassword = (TextInputEditText)findViewById(R.id.login_password);
        mLoginBtn = (Button)findViewById(R.id.login_btn);

        mLoginProgress = new ProgressDialog(this);

        //Login Button
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
            @Override
            public void onClick(View v) {
                String email = mEmail.getEditableText().toString().trim();
                String password = mPassword.getEditableText().toString().trim();

                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password))
                {
                    //Progress Dialog
                    mLoginProgress.setTitle("Logging You In");
                    mLoginProgress.setMessage("Please Wait...");
                    mLoginProgress.show();

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        mLoginProgress.dismiss();
                                        Intent login_intent = new Intent(LoginActivity.this, MainActivity.class);

                                        //on pressing back button in main page, user doesnt go back to main page, instead exits frm app
                                        login_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(login_intent);
                                        finish();
                                    }
                                    else
                                    {
                                        mLoginProgress.hide();
                                        Toast.makeText(LoginActivity.this, "Cannot Sign In. Please check the form and try again.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else
                    if(TextUtils.isEmpty(email))
                        Toast.makeText(LoginActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                else
                        Toast.makeText(LoginActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
