package com.example.tutor_find;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.os.Bundle;

import org.w3c.dom.Text;

public class TeacherRegActivity extends AppCompatActivity {

    MaterialEditText email,password,name,number,institution,year,department;
    Button btn_register;
    Button btn_area_pref;

    private String[] listItems;
    private boolean[] checkedItems;

    private String Area = "";

    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_reg);

        getSupportActionBar().setTitle("Register");

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_register = findViewById(R.id.btn_register);
        name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        institution = findViewById(R.id.institution);
        year = findViewById(R.id.year);
        department = findViewById(R.id.department);
        btn_area_pref = findViewById(R.id.btn_area_pref);

        auth = FirebaseAuth.getInstance();

        listItems = getResources().getStringArray(R.array.areas);
        checkedItems = new boolean[listItems.length];

        
        btn_area_pref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder listBuilder = new AlertDialog.Builder(TeacherRegActivity.this);
                listBuilder.setTitle("Select Preferred Areas");

                listBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
//                        if(isChecked)
//                        {
//                            if(!userArea.contains(position))
//                            {
//                                userArea.add(position);
//                            }
//                            else if(userArea.contains(position))
//                            {
//                                userArea.remove(position);
//                            }
//                        }

                    }
                });

                listBuilder.setCancelable(false);

                listBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Area="";
//                        for(int i=0; i < userArea.size(); i++)
//                        {
//                            Area = Area + listItems[userArea.get(i)];
//                            if(i != userArea.size() - 1)
//                            {
//                                Area = Area + ", ";
//                            }
//                        }

                        for(int i=0; i < checkedItems.length; i++)
                        {
                            if(checkedItems[i])
                            {
                                if(!Area.equals(""))
                                {
                                    Area = Area + ", ";
                                }
                                Area = Area + listItems[i];
                            }
                        }
                        if(!Area.isEmpty())
                        {
                            btn_area_pref.setText(Area);
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
                            Area = "";
                        }
                        btn_area_pref.setText("Select Preferred Area");
                    }
                });

                AlertDialog alertDialog = listBuilder.create();
                alertDialog.show();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                String txt_ContactNo = number.getText().toString();
                String txt_FullName = name.getText().toString();
                String txt_institution = institution.getText().toString();
                String txt_department = department.getText().toString();
                String txt_year = year.getText().toString();

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password) || TextUtils.isEmpty(txt_FullName)) {
                    Toast.makeText(TeacherRegActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else if (txt_password.length() < 6) {
                    Toast.makeText(TeacherRegActivity.this, "Password is too small", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(txt_ContactNo) || txt_ContactNo.length() < 11) {
                    Toast.makeText(TeacherRegActivity.this, "Please enter valid contact number", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(Area)){
                    Toast.makeText(TeacherRegActivity.this, "Select your preferred area", Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(txt_institution)){
                    Toast.makeText(TeacherRegActivity.this, "Select your Institution", Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(txt_department)){
                    Toast.makeText(TeacherRegActivity.this, "Select your Department or Group", Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(txt_year)){
                    Toast.makeText(TeacherRegActivity.this, "Select your Year or Class", Toast.LENGTH_SHORT).show();
                }
                else {
                    register(txt_email, txt_password, txt_ContactNo, txt_FullName, Area, txt_institution, txt_year, txt_department);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.backmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.backButton)
        {
            startActivity(new Intent(TeacherRegActivity.this, StartActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void register(final String email, final String password, final String contactno, final String fullname, final String prefArea, final String institution, final String year, final String department){

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    // email verification part
                    auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(TeacherRegActivity.this, "Registration successful. Please check your email.", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(TeacherRegActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    assert firebaseUser != null;
                    String userid = firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                    HashMap<String , String> hashMap = new HashMap<>();
                    hashMap.put("name", fullname);
                    hashMap.put("institution", institution);
                    hashMap.put("department", department);
                    hashMap.put("year", year);
                    hashMap.put("email", email);
                    hashMap.put("password", password);
                    hashMap.put("number", contactno);
                    hashMap.put("area", prefArea);
                    hashMap.put("userId",userid);
                    hashMap.put("userType", "Tutor");

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Intent intent = new Intent(TeacherRegActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
                else if(task.isCanceled()){
                    Toast.makeText(TeacherRegActivity.this, "You cannot Register with this Email or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
