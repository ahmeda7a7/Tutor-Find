package com.example.tutor_find;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PostEditActivity extends AppCompatActivity {

    private Spinner group;
    private Spinner curriculum;
    private Spinner studyClass;
    private Spinner area;
    private Button subjectListButton;
    private EditText salary;
    private EditText description;
    private EditText address;
    private Button updateButton;


    String postGroup;
    String postCurriculum;
    String postStudyClass;
    String postSubjectList;
    String postSalary;
    String postDescription;
    String postArea;
    String postAddress;

    private String postId;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_edit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        group = findViewById(R.id.group);
        curriculum = findViewById(R.id.curriculum);
        studyClass = findViewById(R.id.studyClass);
        subjectListButton = findViewById(R.id.subjectList);
        salary = findViewById(R.id.salary);
        description = findViewById(R.id.description);
        address = findViewById(R.id.address);
        updateButton = findViewById(R.id.submitButton);
        area = findViewById(R.id.area);

        postId=getIntent().getExtras().getString("postId");

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Posts").child(postId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postGroup  = dataSnapshot.child("group").getValue().toString();
                postCurriculum =dataSnapshot.child("curriculum").getValue().toString();
                postStudyClass = dataSnapshot.child("studyClass").getValue().toString();
                postSubjectList = dataSnapshot.child("subjectList").getValue().toString();
                postSalary = dataSnapshot.child("salary").getValue().toString();
                postDescription = dataSnapshot.child("description").getValue().toString();
                postArea = dataSnapshot.child("area").getValue().toString();
                postAddress = dataSnapshot.child("address").getValue().toString();

//                postGroup.setText("Group: " + group);
//                postCurriculum.setText("Curriculum: " + curriculum);
//                postStudyClass.setText("Class: " + studyClass);
//                postSubjectList.setText("Subjects: " + subjectList);
//                postSalary.setText("Salary: " + salary);
//                postDescription.setText("Description: " + description);
//                postArea.setText("Area: " + area);
//                postAddress.setText("Address: " + address);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
