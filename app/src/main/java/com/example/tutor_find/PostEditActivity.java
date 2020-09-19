package com.example.tutor_find;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.tutor_find.Fragments.MyPostActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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

    private String groupTypeValue;
    private String curriculumTypeValue;
    private String studyClassTypeValue;
    private String areaTypeValue;

    private String[] listItems;
    private boolean[] checkedItems;
    private String subjectItems="";

    private String postId;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        getSupportActionBar().setTitle("Edit Post");
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

        salary.setText(getIntent().getExtras().getString("salary"));
        description.setText(getIntent().getExtras().getString("description"));
        address.setText(getIntent().getExtras().getString("address"));

        System.out.println(getIntent().getExtras().getString("subjectList"));

        subjectListButton.setText(getIntent().getExtras().getString("subjectList"));

        setGroupSpinner();
        setCurriculumSpinner();
        setStudyClassSpinner();
        setAreaSpinner();

        listItems = getResources().getStringArray(R.array.subjects);
        checkedItems = new boolean[listItems.length];

//        databaseReference= FirebaseDatabase.getInstance().getReference().child("Posts").child(postId);
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                DatabaseReference temporary = FirebaseDatabase.getInstance().getReference().child("Posts").child(postId);
//
//                String salaryText = salary.getText().toString().trim();
//                String addressText = address.getText().toString().trim();
//                String descriptionText = description.getText().toString().trim();
//
//
//                temporary.child("group").setValue(groupTypeValue);
//                temporary.child("curriculum").setValue(curriculumTypeValue);
//                temporary.child("studyClass").setValue(studyClassTypeValue);
//                temporary.child("subjectList").setValue(subjectItems);
//                temporary.child("salary").setValue(salaryText);
//                temporary.child("description").setValue(descriptionText);
//                temporary.child("area").setValue(areaTypeValue);
//                temporary.child("address").setValue(addressText);
//                temporary.child("postId").setValue(postId);

//                postGroup  = dataSnapshot.child("group").getValue().toString();
//                postCurriculum =dataSnapshot.child("curriculum").getValue().toString();
//                postStudyClass = dataSnapshot.child("studyClass").getValue().toString();
//                postSubjectList = dataSnapshot.child("subjectList").getValue().toString();
//                postSalary = dataSnapshot.child("salary").getValue().toString();
//                postDescription = dataSnapshot.child("description").getValue().toString();
//                postArea = dataSnapshot.child("area").getValue().toString();
//                postAddress = dataSnapshot.child("address").getValue().toString();

//                postGroup.setText("Group: " + group);
//                postCurriculum.setText("Curriculum: " + curriculum);
//                postStudyClass.setText("Class: " + studyClass);
//                postSubjectList.setText("Subjects: " + subjectList);
//                postSalary.setText("Salary: " + salary);
//                postDescription.setText("Description: " + description);
//                postArea.setText("Area: " + area);
//                postAddress.setText("Address: " + address);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

//        subjectListButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final AlertDialog.Builder listBuilder = new AlertDialog.Builder(PostEditActivity.this);
//                listBuilder.setTitle("Choose Subjects");
//
//                listBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
//
//                    }
//                });
//
//                listBuilder.setCancelable(false);
//
//                listBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        subjectItems="";
//                        for(int i=0; i < checkedItems.length; i++)
//                        {
//                            if(checkedItems[i])
//                            {
//                                if(!subjectItems.equals(""))
//                                {
//                                    subjectItems = subjectItems + ",";
//                                }
//                                subjectItems = subjectItems + listItems[i];
//                            }
//                        }
//                        if (!subjectItems.isEmpty())
//                        {
//                            subjectListButton.setText(subjectItems);
//                        }
//                    }
//                });
//
//                listBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//
//                listBuilder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        for (int i = 0; i < checkedItems.length; i++)
//                        {
//                            checkedItems[i] = false;
//                            subjectItems = "";
//                        }
//                        subjectListButton.setText("Choose Subjects");
//                    }
//                });
//
//                AlertDialog alertDialog = listBuilder.create();
//                alertDialog.show();
//            }
//        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference= FirebaseDatabase.getInstance().getReference().child("Posts").child(postId);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        DatabaseReference temporary = FirebaseDatabase.getInstance().getReference().child("Posts").child(postId);

                        String salaryText = salary.getText().toString().trim();
                        String addressText = address.getText().toString().trim();
                        String descriptionText = description.getText().toString().trim();

                        temporary.child("group").setValue(groupTypeValue);
                        temporary.child("curriculum").setValue(curriculumTypeValue);
                        temporary.child("studyClass").setValue(studyClassTypeValue);
                        temporary.child("subjectList").setValue(subjectItems);
                        temporary.child("salary").setValue(salaryText);
                        temporary.child("description").setValue(descriptionText);
                        temporary.child("area").setValue(areaTypeValue);
                        temporary.child("address").setValue(addressText);
                        temporary.child("postId").setValue(postId);

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                startActivity(new Intent(PostEditActivity.this, MyPostActivity.class));
                finish();
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
            startActivity(new Intent(PostEditActivity.this, MyPostActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setAreaSpinner() {

        List<String> categories = new ArrayList<>();
        categories.add(0,"Select Area:");
        categories.add("Airport");
        categories.add("Badda");
        categories.add("Bashundhara R/A");
        categories.add("Dhaka Cantonment");
        categories.add("Dhanmondi");
        categories.add("Gulshan");
        categories.add("Khilgaon");
        categories.add("Mirpur");
        categories.add("Mohakhali");
        categories.add("Mohammadpur");
        categories.add("Motijheel");
        categories.add("New Market");
        categories.add("Old Dhaka");
        categories.add("Pallabi");
        categories.add("Rampura");
        categories.add("Shahbagh");
        categories.add("Uttara");

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        area.setAdapter(dataAdapter);

        area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Area"))
                {
                    areaTypeValue = ("");
                }
                else
                {
                    String item = parent.getItemAtPosition(position).toString();
                    areaTypeValue = item;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void setStudyClassSpinner() {
        List<String> categories = new ArrayList<>();
        categories.add(0,"Select Class:");
        categories.add("Nursery");
        categories.add("KG");
        categories.add("1");
        categories.add("2");
        categories.add("3");
        categories.add("4");
        categories.add("5");
        categories.add("6");
        categories.add("7");
        categories.add("8");
        categories.add("9");
        categories.add("10");
        categories.add("11");
        categories.add("12");
        categories.add("Others");

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        studyClass.setAdapter(dataAdapter);

        studyClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Class"))
                {
                    studyClassTypeValue = ("");
                }
                else
                {
                    String item = parent.getItemAtPosition(position).toString();
                    studyClassTypeValue = item;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setCurriculumSpinner() {
        List<String> categories = new ArrayList<>();
        categories.add(0,"Select Curriculum:");
        categories.add("Bangla Medium");
        categories.add("English Medium");
        categories.add("English Version");
        categories.add("Others");

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        curriculum.setAdapter(dataAdapter);

        curriculum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Curriculum"))
                {
                    curriculumTypeValue = ("");
                }
                else
                {
                    String item = parent.getItemAtPosition(position).toString();
                    curriculumTypeValue = item;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void setGroupSpinner() {
        List<String> categories = new ArrayList<>();
        categories.add(0,"Select Group:");
        categories.add("Science");
        categories.add("Arts");
        categories.add("Commerce");
        categories.add("Others");

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        group.setAdapter(dataAdapter);

        group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Group"))
                {
                    groupTypeValue = ("");
                }
                else
                {
                    String item = parent.getItemAtPosition(position).toString();
                    groupTypeValue = item;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}
