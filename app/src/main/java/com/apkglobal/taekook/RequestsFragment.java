package com.apkglobal.taekook;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequestsFragment extends Fragment implements View.OnClickListener {


    private ImageButton mAddBtn;
    View view;

    //firebase Auth
    FirebaseAuth firebaseAuth;

    RecyclerView recyclerView;
    List<ModelPost> postList;
    AdapterPosts adapterPosts;

    public RequestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_requests, container, false);

        mAddBtn = (ImageButton) view.findViewById(R.id.add_post_btn);
        mAddBtn.setOnClickListener(this);

        //init
        firebaseAuth = FirebaseAuth.getInstance();

        //recycler view and its properties
        recyclerView = view.findViewById(R.id.postsRecyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //show newest post first, for this load from last
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        //set layout to recycler view
        recyclerView.setLayoutManager(layoutManager);

        //init post list
        postList = new ArrayList<>();

        loadPosts();

        return view;
    }

    private void loadPosts() {
        //path of all posts
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
        //get all data from this reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postList.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    ModelPost modelPost = ds.getValue(ModelPost.class);

                    postList.add(modelPost);

                    //adapter
                    adapterPosts = new AdapterPosts(getActivity(), postList);
                    //set adapter
                    recyclerView.setAdapter(adapterPosts);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //in case of error
                Toast.makeText(getActivity(), ""+ databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), AddPostActivity.class);
        startActivity(intent);
    }
}
