package com.example.tutor_find;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tutor_find.Adapter.AllRequestAdapter;
import com.example.tutor_find.Model.UserInfo;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AllRequestActivity extends AppCompatActivity {

    String userId;
    String postId;


    DatabaseReference databaseReference;
    DatabaseReference userReference;

    private RecyclerView allRequestList;
    AllRequestAdapter adapter;
    Query query;
    String queryValue="false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_request);

        getSupportActionBar().setTitle("Tutor Info");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        postId = getIntent().getExtras().getString("postId");

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        allRequestList = findViewById(R.id.allRequestList);

        allRequestList.setHasFixedSize(true);
        allRequestList.setLayoutManager(new LinearLayoutManager(getBaseContext()));


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Posts").child(postId).child(userId).child("acceptStatus");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String checkAcceptStatus = dataSnapshot.getValue().toString();

                if (checkAcceptStatus.equals("true"))
                {
                    query = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("requests/"+postId).equalTo(true);
                }
                else
                {
                    query = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("requests/"+postId).equalTo(false);
                }


                FirebaseRecyclerOptions<UserInfo> options = new FirebaseRecyclerOptions.Builder<UserInfo>().setQuery(query, UserInfo.class).build();

                adapter = new AllRequestAdapter(options,postId);
                adapter.startListening();

                allRequestList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.backmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.backButton)
        {
            startActivity(new Intent(AllRequestActivity.this, MainActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
