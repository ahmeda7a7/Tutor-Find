package com.example.tutor_find.Adapter;

import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutor_find.Model.UserInfo;
import com.example.tutor_find.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class AllRequestAdapter extends FirebaseRecyclerAdapter<UserInfo, AllRequestAdapter.AllRequestPostViewHolder> {

    DatabaseReference databaseReference;
    DatabaseReference userReference;
    DatabaseReference otherUserReference;
    DatabaseReference removeUserReference;
    DatabaseReference anotherDatabaseReference;
    DatabaseReference tempdatabaseReference;

    String postId;
    String userId="";
    //final String[] checkRequestNumber = {""};

    public AllRequestAdapter(@NonNull FirebaseRecyclerOptions<UserInfo> options, String postId) {
        super(options);
        this.postId=postId;
    }

    @Override
    protected void onBindViewHolder(@NonNull final AllRequestPostViewHolder holder, int position, @NonNull final UserInfo model) {

        holder.userName.setText("Name: "+model.getName());
        holder.userInstitution.setText("Institution: "+model.getInstitution());
        holder.userDepartment.setText("Department: "+model.getDepartment());
        holder.userStudyYear.setText("Year: "+model.getYear());
        holder.userEmail.setText("Email: "+model.getEmail());
        holder.userNumber.setText("Mobile No: "+model.getNumber());

        userId = model.getUserId();

        userReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String check = dataSnapshot.child("requests").child(postId).getValue().toString();
                if(check.equals("true"))
                {
                    holder.userStatus.setText("Accepted");
                    holder.userStatus.setTextColor(Color.parseColor("#008577"));
                    holder.acceptButton.setVisibility(View.GONE);
                    holder.rejectButton.setVisibility(View.GONE);
                }
                else
                {
                    holder.userStatus.setText("pending");
                    holder.userStatus.setTextColor(Color.parseColor("#D81B60"));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder acceptConfirmation;
                acceptConfirmation = new AlertDialog.Builder(holder.acceptButton.getContext());
                acceptConfirmation.setTitle("Select Tutor");
                acceptConfirmation.setMessage("Click OK to select this tutor");

                acceptConfirmation.setCancelable(false);

                acceptConfirmation.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.acceptButton.getContext(), "Tutor Selected", Toast.LENGTH_SHORT).show();

                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Posts").child(postId);

                        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        databaseReference.child(currentUserId).child("acceptStatus").setValue(true);

                        otherUserReference = FirebaseDatabase.getInstance().getReference().child("Users").child(model.getUserId()).child("requests").child(postId);
                        otherUserReference.setValue(true);

                    }
                });

                acceptConfirmation.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alertDialog = acceptConfirmation.create();
                alertDialog.show();

            }
        });

        holder.rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder deleteConfirmation;
                deleteConfirmation = new AlertDialog.Builder(holder.rejectButton.getContext());
                deleteConfirmation.setTitle("Delete Tutor");
                deleteConfirmation.setMessage("Click Delete to reject this tutor");

                deleteConfirmation.setCancelable(false);

                deleteConfirmation.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Posts").child(postId).child("requestNumber");

                        anotherDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Posts").child(postId);

                        otherUserReference = FirebaseDatabase.getInstance().getReference().child("Users").child(model.getUserId()).child("requests").child(postId);
                        otherUserReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                String check = dataSnapshot.getValue().toString();

                                if(!check.equals("true"))
                                {
                                    removeUserReference = FirebaseDatabase.getInstance().getReference().child("Users").child(model.getUserId()).child("requests").child(postId);
                                    removeUserReference.setValue("rejected");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                        final String[] checkRequestNumber = {""};

                        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            boolean processDone = true;
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if (processDone) {

                                    String requestNumber = dataSnapshot.getValue().toString();
                                    Log.d("decrease", "inside requestAdapter");

                                    int requestNumberValue = Integer.valueOf(requestNumber);
                                    int pushNumber = requestNumberValue - 1;
                                    checkRequestNumber[0] = String.valueOf(pushNumber);

                                }
                                processDone = false;
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        Log.d("decrease", "request value outside datasnapshot: " + checkRequestNumber[0]);

                        anotherDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                DatabaseReference requestNumberReference;
                                requestNumberReference = FirebaseDatabase.getInstance().getReference().child("Posts").child(postId).child("requestNumber");
                                requestNumberReference.setValue(checkRequestNumber[0]);
                                Log.d("decrease", "request value in datasnapshot: " + checkRequestNumber[0]);

                                if (checkRequestNumber[0].equals("0")) {
                                    Log.d("decrease", "request value in datasnapshot: " + checkRequestNumber[0]);
                                    DatabaseReference requestStatusReference;
                                    requestStatusReference = FirebaseDatabase.getInstance().getReference().child("Posts").child(postId).child(currentUser.getUid()).child("requestStatus");
                                    requestStatusReference.setValue(false);


                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


//                        final String[] checkRequestNumber = {""};
//
//                        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//
//                        final int[] count = {0};
//
//                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                            boolean processDone = true;
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                if (processDone) {
//
//                                    String requestNumber = dataSnapshot.getValue().toString();
//                                    Log.d("decrease", "inside requestAdapter");
//
//                                    if (requestNumber.equals("1")) {
//                                        Log.d("decrease", "request inside if condition: " + requestNumber);
//                                        DatabaseReference requestStatusReference;
//                                        requestStatusReference = FirebaseDatabase.getInstance().getReference().child("Posts").child(postId).child(currentUser.getUid()).child("requestStatus");
//                                        requestStatusReference.setValue(false);
//                                    }
//
//                                    int requestNumberValue = Integer.valueOf(requestNumber);
//                                    int pushNumber = requestNumberValue - 1;
//
//                                    if (count[0] == 0) {
//                                        checkRequestNumber[0] = String.valueOf(pushNumber);
//                                        DatabaseReference requestNumberReference;
//                                        requestNumberReference = FirebaseDatabase.getInstance().getReference().child("Posts").child(postId).child("temp");
//                                        requestNumberReference.setValue(checkRequestNumber[0]);
//                                    }
//
//                                    Log.d("decrease", "check string" + checkRequestNumber[0]);
//                                    Log.d("decrease", "count value" + count[0]);
//
//                                    count[0]++;
//                                    //stopListening();
//                                }
//                                processDone = false;
//                            }
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
                    }
                });

                deleteConfirmation.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alertDialog = deleteConfirmation.create();
                alertDialog.show();

            }



        });
    }

    @NonNull
    @Override
    public AllRequestPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_request, parent, false);
        return new AllRequestAdapter.AllRequestPostViewHolder(view);
    }

    public class AllRequestPostViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        TextView userInstitution;
        TextView userDepartment;
        TextView userStudyYear;
        TextView userEmail;
        TextView userNumber;
        TextView userStatus;
        Button acceptButton;
        Button rejectButton;


        public AllRequestPostViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.userName);
            userInstitution = itemView.findViewById(R.id.userInstitution);
            userDepartment = itemView.findViewById(R.id.userDepartment);
            userStudyYear = itemView.findViewById(R.id.userStudyYear);
            userEmail = itemView.findViewById(R.id.userEmail);
            userNumber = itemView.findViewById(R.id.userNumber);
            userStatus = itemView.findViewById(R.id.userStatus);

            acceptButton = itemView.findViewById(R.id.acceptButton);
            rejectButton = itemView.findViewById(R.id.rejectButton);

        }
    }
}
