package com.example.tutor_find.Fragments;


import android.app.DownloadManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tutor_find.Adapter.AllRequestAdapter;
import com.example.tutor_find.Adapter.AppliedPostAdapter;
import com.example.tutor_find.Model.Post;
import com.example.tutor_find.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.concurrent.RecursiveAction;

public class AppliedPostActivity extends Fragment {

    private RecyclerView appliedPostList;
    AppliedPostAdapter adapter;
    Query query;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_applied_post, container, false);

        appliedPostList = view.findViewById(R.id.appliedPostList);
        appliedPostList.setHasFixedSize(true);
        appliedPostList.setLayoutManager(new LinearLayoutManager(getContext()));



        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        query = FirebaseDatabase.getInstance().getReference().child("Posts").orderByChild("requests/"+currentUserId).equalTo(false);

        FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>().setQuery(query, Post.class).build();

        adapter = new AppliedPostAdapter(options);
        adapter.startListening();
        appliedPostList.setAdapter(adapter);

        return view;
    }

}
