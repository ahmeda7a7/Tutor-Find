package com.example.tutor_find;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ConfirmActivity extends AppCompatActivity {

    Button confirmButton;

    TextView postGroup;
    TextView postCurriculum;
    TextView postStudyClass;
    TextView postSubjectList;
    TextView postSalary;
    TextView postDescription;
    TextView postAddress;

    String group;
    String curriculum;
    String studyClass;
    String subjectList;
    String salary;
    String description;
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
        postAddress = findViewById(R.id.postAddress);
        confirmButton = findViewById(R.id.confirmButton);

        group = getIntent().getExtras().getString("group");
        curriculum = getIntent().getExtras().getString("curriculum");
        studyClass = getIntent().getExtras().getString("studyClass");
        subjectList = getIntent().getExtras().getString("subjectList");
        salary = getIntent().getExtras().getString("salary");
        description = getIntent().getExtras().getString("description");
        address = getIntent().getExtras().getString("address");
        userId = getIntent().getExtras().getString("userId");
        postId = getIntent().getExtras().getString("postId");

        postGroup.setText(group);
        postCurriculum.setText(curriculum);
        postStudyClass.setText(studyClass);
        postSubjectList.setText(subjectList);
        postSalary.setText(salary);
        postDescription.setText(description);
        postAddress.setText(address);

//        databaseReference = FirebaseDatabase.getInstance().getReference().child("Posts").child(postId);
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
