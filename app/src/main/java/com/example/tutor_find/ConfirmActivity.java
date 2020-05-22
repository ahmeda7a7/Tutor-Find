package com.example.tutor_find;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ConfirmActivity extends AppCompatActivity {

    Button confirmButton;

    TextView postGroup;
    TextView postCurriculum;
    TextView postStudyClass;
    TextView postSubjectList;
    TextView postSalary;
    TextView postDescription;
    TextView postArea;
    TextView postAddress;


    String group;
    String curriculum;
    String studyClass;
    String subjectList;
    String salary;
    String description;
    String area;
    String address;
    String userId;
    String postId;

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference userReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        getSupportActionBar().setTitle("Confirm Tuition");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        postGroup = findViewById(R.id.postGroup);
        postCurriculum = findViewById(R.id.postCurriculum);
        postStudyClass = findViewById(R.id.postStudyClass);
        postSubjectList = findViewById(R.id.postSubjectList);
        postSalary = findViewById(R.id.postSalary);
        postDescription = findViewById(R.id.postDescription);
        postArea = findViewById(R.id.postArea);
        postAddress = findViewById(R.id.postAddress);
        confirmButton = findViewById(R.id.confirmButton);

//        group = getIntent().getExtras().getString("group");
//        curriculum = getIntent().getExtras().getString("curriculum");
//        studyClass = getIntent().getExtras().getString("studyClass");
//        subjectList = getIntent().getExtras().getString("subjectList");
//        salary = getIntent().getExtras().getString("salary");
//        description = getIntent().getExtras().getString("description");
//        area = getIntent().getExtras().getString("area");
//        address = getIntent().getExtras().getString("address");
//        userId = getIntent().getExtras().getString("userId");
        postId = getIntent().getExtras().getString("postId");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Posts").child(postId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                     group  = dataSnapshot.child("group").getValue().toString();
                     curriculum =dataSnapshot.child("curriculum").getValue().toString();
                     studyClass = dataSnapshot.child("studyClass").getValue().toString();
                     subjectList = dataSnapshot.child("subjectList").getValue().toString();
                     salary = dataSnapshot.child("salary").getValue().toString();
                     description = dataSnapshot.child("description").getValue().toString();
                     area = dataSnapshot.child("area").getValue().toString();
                     address = dataSnapshot.child("address").getValue().toString();
                     userId = dataSnapshot.child("userId").getValue().toString();

                    postGroup.setText("Group: " + group);
                    postCurriculum.setText("Curriculum: " + curriculum);
                    postStudyClass.setText("Class: " + studyClass);
                    postSubjectList.setText("Subjects: " + subjectList);
                    postSalary.setText("Salary: " + salary);
                    postDescription.setText("Description: " + description);
                    postArea.setText("Area: " + area);
                    postAddress.setText("Address: " + address);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        userReference = FirebaseDatabase.getInstance().getReference().child("Users");

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ConfirmActivity.this, "Request Send", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ConfirmActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
