package com.example.tutor_find.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tutor_find.Adapter.MyPostAdapter;
import com.example.tutor_find.Model.Post;
import com.example.tutor_find.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MyPostActivity extends Fragment {

    private RecyclerView myPostList;
    MyPostAdapter adapter;
    String userId;

    private DatabaseReference databaseReference;
    FirebaseUser firebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_post_activity, container, false);

//        databaseReference = FirebaseDatabase.getInstance().getReference().child("Posts");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userId = firebaseUser.getUid();


        myPostList = view.findViewById(R.id.myPostList);
        myPostList.setHasFixedSize(true);
        myPostList.setLayoutManager(new LinearLayoutManager(getContext()));

        Query query = FirebaseDatabase.getInstance().getReference().child("Posts").orderByChild("userId").equalTo(userId);

        FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>().setQuery(query,Post.class).build();

        adapter = new MyPostAdapter(options);
        adapter.startListening();

        myPostList.setAdapter(adapter);

        return view;

    }
}
