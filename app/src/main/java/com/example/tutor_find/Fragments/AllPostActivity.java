package com.example.tutor_find.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tutor_find.Adapter.PostAdapter;
import com.example.tutor_find.Model.Post;
import com.example.tutor_find.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AllPostActivity extends Fragment {


    private RecyclerView postList;
    PostAdapter adapter;


    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_all_post, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userId = firebaseUser.getUid();

        postList = view.findViewById(R.id.postList);
        postList.setHasFixedSize(true);
        postList.setLayoutManager(new LinearLayoutManager(getContext()));

        //database change

        //Query query = FirebaseDatabase.getInstance().getReference().child("Posts").orderByChild(userId).equalTo(null);

        Query query = FirebaseDatabase.getInstance().getReference().child("Posts").orderByChild(userId+"/"+userId).equalTo(null);

        //database change

        FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>().setQuery(query, Post.class).build();

        adapter = new PostAdapter(options);
        adapter.startListening();

        postList.setAdapter(adapter);

        return view;
    }

}
