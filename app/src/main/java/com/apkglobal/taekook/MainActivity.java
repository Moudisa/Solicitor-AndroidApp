package com.apkglobal.taekook;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    //declaring an instance of FirebaseAuth
    private FirebaseAuth mAuth;

    private ViewPager mViewPager;

    private TabLayout mTabLayout;
    
    private SectionPagerAdapter mSectionPagerAdapter;

    //private Button mAddBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing the FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        //Toolbar
        android.support.v7.widget.Toolbar mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("SOLICITOR");


        //Tabs
        mViewPager = (ViewPager)findViewById(R.id.main_tabPager);
        mSectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mSectionPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);


    }


    @Override
    public void onStart()
    {
        super.onStart();
        // Check if user is signed in (non-null).
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null)
        {
            sendToStart();
        }

    }

    private void sendToStart() {
        Intent startIntent = new Intent(MainActivity.this, StartActivity.class);
        startActivity(startIntent);  //user goes to startActivity if not currently signedIn
        finish();    //user wont come back to mainActivity if back btn is pressed
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.main_logout_btn)
        {
            FirebaseAuth.getInstance().signOut();
            sendToStart();
        }

        if(item.getItemId() == R.id.main_settings_btn)
        {
            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
        }

        if(item.getItemId() == R.id.main_all_btn)
        {
            Intent settingsIntent = new Intent(MainActivity.this, UsersActivity.class);
            startActivity(settingsIntent);
        }

        return true;
    }

}
