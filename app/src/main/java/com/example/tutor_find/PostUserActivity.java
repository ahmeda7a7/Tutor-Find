package com.example.tutor_find;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class PostUserActivity extends AppCompatActivity {

    String userId;

    TextView userName;
    TextView userInstitution;
    TextView userDepartment;
    TextView userStudyYear;
    TextView userEmail;
    TextView userNumber;

    DatabaseReference userReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_user);

        getSupportActionBar().setTitle("Contact Page");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userName = findViewById(R.id.userName);
        userInstitution = findViewById(R.id.userInstitution);
        userDepartment = findViewById(R.id.userDepartment);
        userStudyYear = findViewById(R.id.userStudyYear);
        userEmail = findViewById(R.id.userEmail);
        userNumber = findViewById(R.id.userNumber);

        userId = getIntent().getExtras().getString("userId");

        userReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String institution = dataSnapshot.child("institution").getValue().toString();
                String department = dataSnapshot.child("department").getValue().toString();;
                String year = dataSnapshot.child("year").getValue().toString();;
                String email = dataSnapshot.child("email").getValue().toString();;
                String number = dataSnapshot.child("number").getValue().toString();;

                userName.setText("Name: " + name);
                userInstitution.setText("Institution: " + institution);
                userDepartment.setText("Department: " + department);
                userStudyYear.setText("Year: " + year);
                userEmail.setText("Email: " + email);
                userNumber.setText("Number: " + number);

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
            startActivity(new Intent(PostUserActivity.this, MainActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
