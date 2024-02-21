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
import com.google.firebase.auth.FirebaseUser;
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


public class ArticlesFragment extends Fragment implements View.OnClickListener{

    private ImageButton mAddBtn;
    View view;

    //firebase Auth
    FirebaseAuth firebaseAuth;

    RecyclerView recyclerView;
    List<Articles> articlesList;
    AdapterArticles adapterArticles;

    public ArticlesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_articles, container, false);
        mAddBtn = (ImageButton) view.findViewById(R.id.add_article_btn);

        mAddBtn.setOnClickListener(this);

        //init
        firebaseAuth = FirebaseAuth.getInstance();


        //recycler view and its properties
        recyclerView = view.findViewById(R.id.articlesRecyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //show newest post first, for this load from last
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        //set layout to recycler view
        recyclerView.setLayoutManager(layoutManager);

        //init post list
        articlesList = new ArrayList<>();

        loadPosts();

        return view;

    }

    private void loadPosts() {

        //path of all posts
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Articles");
        //get all data from this reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                articlesList.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Articles articles = ds.getValue(Articles.class);

                    articlesList.add(articles);

                    //adapter
                    adapterArticles = new AdapterArticles(getActivity(), articlesList);
                    //set adapter
                    recyclerView.setAdapter(adapterArticles);
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
        Intent articleIntent = new Intent(getActivity(), GuidelinesActivity.class);
                startActivity(articleIntent);

    }
}
