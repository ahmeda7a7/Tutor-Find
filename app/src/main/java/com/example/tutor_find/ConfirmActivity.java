package com.example.tutor_find;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tutor_find.Notifications.Data;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


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
    DatabaseReference requestReference;
    DatabaseReference userReference;
    DatabaseReference checkUserReference;
    DatabaseReference requestNumberReference;
    FirebaseUser firebaseUser;


    private RequestQueue requestQueue;
    private String notificationURL = "https://fcm.googleapis.com/fcm/send";
    private final String CHANNEL_ID = "tutor_requests";
    private final int NOTIFICATION_ID = 001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        getSupportActionBar().setTitle("Confirm Tuition");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        requestQueue = Volley.newRequestQueue(this);

        FirebaseMessaging.getInstance().subscribeToTopic("news");

        postGroup = findViewById(R.id.postGroup);
        postCurriculum = findViewById(R.id.postCurriculum);
        postStudyClass = findViewById(R.id.postStudyClass);
        postSubjectList = findViewById(R.id.postSubjectList);
        postSalary = findViewById(R.id.postSalary);
        postDescription = findViewById(R.id.postDescription);
        postArea = findViewById(R.id.postArea);
        postAddress = findViewById(R.id.postAddress);
        confirmButton = findViewById(R.id.confirmButton);

        postId = getIntent().getExtras().getString("postId");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Posts").child(postId);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        userReference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid()).child("requests");
        requestReference = FirebaseDatabase.getInstance().getReference().child("Posts").child(postId);

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




        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //sendNotification();


                checkUserReference = FirebaseDatabase.getInstance().getReference().child("Posts").child(postId).child(userId).child("requestStatus");

                final String[] pushNumber = {""};

                requestNumberReference = FirebaseDatabase.getInstance().getReference().child("Posts").child(postId).child("requestNumber");
                requestNumberReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String checkRequestNumber = dataSnapshot.getValue().toString();

                        int convertedNumber = Integer.valueOf(checkRequestNumber);
                        int testNumber = convertedNumber + 1;

                        pushNumber[0] = String.valueOf(testNumber);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                checkUserReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        final String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        String checkRequestStatus = dataSnapshot.getValue().toString();
                        if(checkRequestStatus.equals("false"))
                        {

                            databaseReference.child(userId).child("requestStatus").setValue(true);
                            databaseReference.child(userId).child("hasRequest").setValue(true);
                            userReference.child(postId).setValue(false);
                            requestReference.child("requests").child(currentUser).setValue(false);

                            requestNumberReference = FirebaseDatabase.getInstance().getReference().child("Posts").child(postId).child("requestNumber");
                            requestNumberReference.setValue("1");
                        }
                        else
                        {
                            if (!currentUser.equals(userId)) {
                                Log.d("decrease", "inside confirm");
                                userReference.child(postId).setValue(false);
                                requestReference.child("requests").child(currentUser).setValue(false);

                                DatabaseReference newRequestNumberReference = FirebaseDatabase.getInstance().getReference().child("Posts").child(postId).child("requestNumber");
                                newRequestNumberReference.setValue(pushNumber[0]);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Toast.makeText(ConfirmActivity.this, "Apply Successful", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(ConfirmActivity.this, MainActivity.class));
                finish();
            }
        });
    }


    private void sendNotification() {


        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);

        builder.setSmallIcon(R.drawable.ic_add);
        builder.setContentTitle("simple notification");
        builder.setContentText("this is a notification");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }

    private void createNotificationChannel() {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            CharSequence name = "tutor_requests";

            String description = "all the tutor requests notifications";

            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);

            notificationChannel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
