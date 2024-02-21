package com.apkglobal.taekook;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class StartActivity extends AppCompatActivity {

    private Button RegBtn;
    private Button loginBtn;
    private ImageView mImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mImg = (ImageView)findViewById(R.id.start_img);
        loginBtn = (Button)findViewById(R.id.start_login_btn);
        RegBtn = (Button)findViewById(R.id.startRegBtn);
        RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg_intent = new Intent(StartActivity.this, PreRegisterActivity.class);
                startActivity(reg_intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg_intent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(reg_intent);
            }
        });
    }
}
