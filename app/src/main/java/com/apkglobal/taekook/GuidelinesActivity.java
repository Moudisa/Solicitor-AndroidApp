package com.apkglobal.taekook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GuidelinesActivity extends AppCompatActivity {

    private TextView mText1;
    private TextView mText2;
    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidelines);

        mText1 = (TextView) findViewById(R.id.heading);
        mText2 = (TextView) findViewById(R.id.guidelines);

        mBtn = (Button) findViewById(R.id.postBtn);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(GuidelinesActivity.this, PostArticle.class);
                startActivity(mIntent);
            }
        });


    }
}