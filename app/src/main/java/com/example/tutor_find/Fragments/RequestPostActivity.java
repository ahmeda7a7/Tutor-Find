package com.example.tutor_find.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tutor_find.Adapter.RequestPostAdapter;
import com.example.tutor_find.Model.Post;
import com.example.tutor_find.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class RequestPostActivity extends Fragment {

    private RecyclerView requestPostList;
    RequestPostAdapter adapter;

    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    String userId;
    String postId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_request_post, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Posts");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        userId = firebaseUser.getUid();


        requestPostList = view.findViewById(R.id.requestPostList);
        requestPostList.setHasFixedSize(true);
        requestPostList.setLayoutManager(new LinearLayoutManager(getContext()));

        //database change

        //Query query = FirebaseDatabase.getInstance().getReference().child("Posts").orderByChild(userId).equalTo(true);

        Query query = FirebaseDatabase.getInstance().getReference().child("Posts").orderByChild(userId+"/requestStatus").equalTo(true);

        //database change

        FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>().setQuery(query, Post.class).build();

        adapter = new RequestPostAdapter(options);
        adapter.startListening();
        requestPostList.setAdapter(adapter);

        return view;
    }

}
