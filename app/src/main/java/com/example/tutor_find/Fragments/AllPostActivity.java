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

public class AllPostActivity extends Fragment {


    private RecyclerView postList;
    PostAdapter adapter;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private DatabaseReference userReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_all_post, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Posts");

        postList = view.findViewById(R.id.postList);
        postList.setHasFixedSize(true);
        postList.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>().setQuery(databaseReference, Post.class).build();

        adapter = new PostAdapter(options);
        adapter.startListening();

        postList.setAdapter(adapter);

        return view;
    }

}
