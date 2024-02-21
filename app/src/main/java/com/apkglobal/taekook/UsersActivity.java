package com.apkglobal.taekook;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {

    RecyclerView mUserslist;
    AdapterUsers adapterUsers;
    List<Users> usersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        //Toolbar
        android.support.v7.widget.Toolbar mToolbar = findViewById(R.id.users_appBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("All Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      //  mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

       // mUsersDatabase = FirebaseDatabase.getInstance().getReference();
      //Query query = mUsersDatabase.child("Users").orderByChild("name").equalTo(Users.getName());


        //recyclerview
        mUserslist = (RecyclerView) findViewById(R.id.users_list);
        mUserslist.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mUserslist.setLayoutManager(llm);

        //init user list
        usersList = new ArrayList<>();

        //get all users
        getAllUsers();
    }


    private void getAllUsers() {

        //get current user
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //get path of database named "Users" containing users info
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        //get all data
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usersList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Users users = ds.getValue(Users.class);

                    //get all users except currently signed in user
                  if(!users.getUid().equals(firebaseUser.getUid())) {
                      usersList.add(users);
                  }

                    //adapter
                    adapterUsers = new AdapterUsers(UsersActivity.this, usersList);

                   //  set adapter to recycler view
                    mUserslist.setAdapter(adapterUsers);
                    adapterUsers.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}