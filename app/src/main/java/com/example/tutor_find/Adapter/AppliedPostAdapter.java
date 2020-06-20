package com.example.tutor_find.Adapter;

import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutor_find.Model.Post;
import com.example.tutor_find.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AppliedPostAdapter extends FirebaseRecyclerAdapter<Post, AppliedPostAdapter.AppliedPostViewHolder> {

    DatabaseReference userReference;

    public AppliedPostAdapter(@NonNull FirebaseRecyclerOptions<Post> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final AppliedPostViewHolder holder, int position, @NonNull final Post model) {

        holder.postGroup.setText("Group: " + model.getGroup());
        holder.postCurriculum.setText("Curriculum: " + model.getCurriculum());
        holder.postStudyClass.setText("Class: " + model.getStudyClass());
        holder.postSubjectList.setText("Subjects: " + model.getSubjectList());
        holder.postSalary.setText("Salary: " + model.getSalary());
        holder.postDescription.setText("Description: " + model.getDescription());
        holder.postArea.setText("Area: " + model.getArea());
        holder.postAddress.setText("Address: " + model.getAddress());

        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        userReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String decision = dataSnapshot.child("requests").child(model.getPostId()).getValue().toString();

                if(decision.equals("rejected"))
                {
                    holder.postDecision.setText("Rejected");
                    holder.postDecision.setTextColor(Color.parseColor("#F44336"));
                }
                else if (decision.equals("true"))
                {
                    holder.postDecision.setText("Accepted");
                    holder.postDecision.setTextColor(Color.parseColor("#008577"));
                }
                else
                {
                    holder.postDecision.setText("Pending");
                    holder.postDecision.setTextColor(Color.parseColor("#FFEB3B"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @NonNull
    @Override
    public AppliedPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_post_verdict_view, parent, false);

        return new AppliedPostViewHolder(view);
    }

    public class AppliedPostViewHolder extends RecyclerView.ViewHolder {

        TextView postGroup;
        TextView postCurriculum;
        TextView postStudyClass;
        TextView postSubjectList;
        TextView postSalary;
        TextView postDescription;
        TextView postArea;
        TextView postAddress;
        TextView postDecision;

        public AppliedPostViewHolder(@NonNull View itemView) {
            super(itemView);

            postGroup = itemView.findViewById(R.id.postGroup);
            postCurriculum = itemView.findViewById(R.id.postCurriculum);
            postStudyClass = itemView.findViewById(R.id.postStudyClass);
            postSubjectList = itemView.findViewById(R.id.postSubjectList);
            postSalary = itemView.findViewById(R.id.postSalary);
            postDescription = itemView.findViewById(R.id.postDescription);
            postArea = itemView.findViewById(R.id.postArea);
            postAddress = itemView.findViewById(R.id.postAddress);
            postDecision = itemView.findViewById(R.id.postDecision);

        }
    }
}
