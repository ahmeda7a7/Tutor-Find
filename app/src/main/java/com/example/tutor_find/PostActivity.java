package com.example.tutor_find;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity {

    private Spinner group;
    private Spinner curriculum;
    private Spinner studyClass;
    private Button subjectListButton;
    private EditText salary;
    private EditText description;
    private EditText address;
    private Button submitButton;

    private String groupTypeValue;
    private String curriculumTypeValue;
    private String studyClassTypeValue;

    private String[] listItems;
    private boolean[] checkedItems;
    private ArrayList<Integer> userItems = new ArrayList<>();
    private List<String> subjectItems;

    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        group = findViewById(R.id.group);
        curriculum = findViewById(R.id.curriculum);
        studyClass = findViewById(R.id.studyClass);
        subjectListButton = findViewById(R.id.subjectList);
        salary = findViewById(R.id.salary);
        description = findViewById(R.id.description);
        address = findViewById(R.id.address);
        submitButton = findViewById(R.id.submitButton);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Posts");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        setGroupSpinner();
        setCurriculumSpinner();
        setStudyClassSpinner();

        subjectItems = new ArrayList<>();
        listItems = getResources().getStringArray(R.array.subjects);
        checkedItems = new boolean[listItems.length];

        subjectListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder listBuilder = new AlertDialog.Builder(PostActivity.this);
                listBuilder.setTitle("Choose Subjects");

                listBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if(isChecked)
                        {
                            if(!userItems.contains(position))
                            {
                                userItems.add(position);
                            }
                            else if(userItems.contains(position))
                            {
                                userItems.remove(position);
                            }
                        }
                    }
                });

                listBuilder.setCancelable(false);

                listBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        String item = "";
//                        for(int i=0; i < userItems.size(); i++)
//                        {
//                            item = item +listItems[userItems.get(i)];
//                            if(i != userItems.size() - 1)
//                            {
//                                item = item + ", ";
//                            }
//                        }

                        for(int i=0; i < userItems.size(); i++)
                        {
                            subjectItems.add(listItems[userItems.get(i)]);
                        }
                    }
                });

                listBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                listBuilder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < checkedItems.length; i++)
                        {
                            checkedItems[i] = false;
                            userItems.clear();
                        }
                    }
                });

                AlertDialog alertDialog = listBuilder.create();
                alertDialog.show();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        });
    }

    private void startPosting() {

        String salaryText = salary.getText().toString().trim();
        String addressText = address.getText().toString().trim();

        if(TextUtils.isEmpty(groupTypeValue))
        {
            Toast.makeText(PostActivity.this, "Select your group", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(curriculumTypeValue))
        {
            Toast.makeText(PostActivity.this, "Select your curriculum", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(studyClassTypeValue))
        {
            Toast.makeText(PostActivity.this, "Select your class", Toast.LENGTH_SHORT).show();
        }
        else if(subjectItems.isEmpty())
        {
            Toast.makeText(PostActivity.this, "Select your subject", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(salaryText))
        {
            Toast.makeText(PostActivity.this, "Complete Salary", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(addressText))
        {
            Toast.makeText(PostActivity.this, "Provide your address", Toast.LENGTH_SHORT).show();
        }
        else
        {
            DatabaseReference newPost = databaseReference.push();
            String userId = firebaseUser.getUid();

            newPost.child("userId").setValue(userId);
            newPost.child("group").setValue(groupTypeValue);
            newPost.child("curriculum").setValue(curriculumTypeValue);
            newPost.child("studyClass").setValue(studyClassTypeValue);
            newPost.child("subjectList").setValue(subjectItems);
            newPost.child("salary").setValue(salaryText);
            newPost.child("address").setValue(addressText);
        }
    }

    private void setStudyClassSpinner() {
        List<String> categories = new ArrayList<>();
        categories.add(0,"Class");
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
        categories.add("others");

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
        categories.add(0,"Curriculum");
        categories.add("Bangla Medium");
        categories.add("English Medium");
        categories.add("English Version");

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
        categories.add(0,"Group");
        categories.add("Science");
        categories.add("Arts");
        categories.add("Commerce");

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
