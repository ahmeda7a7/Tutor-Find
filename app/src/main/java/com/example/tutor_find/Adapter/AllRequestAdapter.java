package com.example.tutor_find.Adapter;

import android.content.DialogInterface;
import android.graphics.Color;
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
    //DatabaseReference requestNumberReference;

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

                        final int[] requestNumberValue = new int[1];
                        final String[] requestNumber = new String[1];

                        final String[] counterCheck = {""};

                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Posts").child(postId).child("requestNumber");
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                requestNumber[0] = dataSnapshot.getValue().toString();

                                if(counterCheck[0].equals("")) {
                                    requestNumberValue[0] = Integer.valueOf(requestNumber[0]);
                                    int pushNumber = requestNumberValue[0] - 1;
                                    checkRequestNumber[0] = String.valueOf(pushNumber);

                                    DatabaseReference requestNumberReference;
                                    requestNumberReference = FirebaseDatabase.getInstance().getReference().child("Posts").child(postId);
                                    requestNumberReference.child("requestNumber").setValue(checkRequestNumber[0]);

                                    if (checkRequestNumber[0].equals('0')) {
                                        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                        requestNumberReference.child(currentUser).child("requestStatus").setValue(false);
                                    }
                                    counterCheck[0] = "done";
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

//                        DatabaseReference requestNumberReference;
//                        requestNumberReference = FirebaseDatabase.getInstance().getReference().child("Posts").child(postId);
//                        requestNumberReference.child("requestNumber").setValue(checkRequestNumber[0]);
//
//                        if (checkRequestNumber[0].equals("0"))
//                        {
//                            String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                            requestNumberReference.child(currentUser).child("requestStatus").setValue(false);
//                        }

                        //requestNumberValue[0] = Integer.valueOf(requestNumber[0]);
                        int pushNumber = requestNumberValue[0] - 1;
                        checkRequestNumber[0] = String.valueOf(pushNumber);

                        DatabaseReference requestNumberReference;
                        requestNumberReference = FirebaseDatabase.getInstance().getReference().child("Posts").child(postId);
                        requestNumberReference.child("requestNumber").setValue(checkRequestNumber[0]);

                        if (checkRequestNumber[0].equals("0"))
                        {
                            String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            requestNumberReference.child(currentUser).child("requestStatus").setValue(false);
                        }

//                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Posts");
//                        databaseReference.child(postId).child("requestNumber");
//                        databaseReference.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                DatabaseReference requestNumberReference;
//                                requestNumberReference = FirebaseDatabase.getInstance().getReference().child("Posts").child(postId);
//                                requestNumberReference.child("requestNumber").setValue(checkRequestNumber[0]);
//
//                                if (checkRequestNumber[0].equals("0"))
//                                {
//                                    String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                                    requestNumberReference.child(currentUser).child("requestStatus").setValue(false);
//                                }
//
//                            }
//
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