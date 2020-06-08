package com.example.tutor_find;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tutor_find.Adapter.AllRequestAdapter;
import com.example.tutor_find.Model.UserInfo;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AllRequestActivity extends AppCompatActivity {


//    TextView postGroup;
//    TextView postCurriculum;
//    TextView postStudyClass;
//    TextView postSubjectList;
//    TextView postSalary;
//    TextView postDescription;
//    TextView postArea;
//    TextView postAddress;
//
//
//    String group;
//    String curriculum;
//    String studyClass;
//    String subjectList;
//    String salary;
//    String description;
//    String area;
//    String address;
//    String userId;
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

        postId = getIntent().getExtras().getString("postId");


//        postGroup = findViewById(R.id.postGroup);
//        postCurriculum = findViewById(R.id.postCurriculum);
//        postStudyClass = findViewById(R.id.postStudyClass);
//        postSubjectList = findViewById(R.id.postSubjectList);
//        postSalary = findViewById(R.id.postSalary);
//        postDescription = findViewById(R.id.postDescription);
//        postArea = findViewById(R.id.postArea);
//        postAddress = findViewById(R.id.postAddress);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Posts").child(postId).child("decision");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                queryValue = dataSnapshot.getValue().toString();

                allRequestList = findViewById(R.id.allRequestList);

                allRequestList.setHasFixedSize(true);
                allRequestList.setLayoutManager(new LinearLayoutManager(getBaseContext()));

                if(queryValue.equals("false"))
                {
                    query = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("requests/"+postId).equalTo(false);
                }
                else
                {
                    query = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("requests/"+postId).equalTo(true);
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


//        allRequestList = findViewById(R.id.allRequestList);
//
//        allRequestList.setHasFixedSize(true);
//        allRequestList.setLayoutManager(new LinearLayoutManager(getBaseContext()));
//
//        query = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("requests/"+postId).equalTo(false);
//
//        FirebaseRecyclerOptions<UserInfo> options = new FirebaseRecyclerOptions.Builder<UserInfo>().setQuery(query, UserInfo.class).build();
//
//        adapter = new AllRequestAdapter(options,postId);
//        adapter.startListening();
//
//        allRequestList.setAdapter(adapter);



    }
}
