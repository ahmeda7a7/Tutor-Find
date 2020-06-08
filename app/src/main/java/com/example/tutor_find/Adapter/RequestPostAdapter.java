package com.example.tutor_find.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutor_find.AllRequestActivity;
import com.example.tutor_find.Model.Post;
import com.example.tutor_find.Notifications.Data;
import com.example.tutor_find.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RequestPostAdapter extends FirebaseRecyclerAdapter<Post, RequestPostAdapter.RequestPostViewHolder> {

    DatabaseReference databaseReference;

    public RequestPostAdapter(@NonNull FirebaseRecyclerOptions<Post> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final RequestPostViewHolder holder, int position, @NonNull final Post model) {

        holder.postGroup.setText("Group: " + model.getGroup());
        holder.postCurriculum.setText("Curriculum: " + model.getCurriculum());
        holder.postStudyClass.setText("Class: " + model.getStudyClass());
        holder.postSubjectList.setText("Subjects: " + model.getSubjectList());

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Posts").child(model.getPostId()).child("decision");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String check = dataSnapshot.getValue().toString();
                if(check.equals("true"))
                {
                    holder.postDecision.setText("Accepted");
                    holder.postDecision.setTextColor(Color.parseColor("#008577"));
                }
                else
                {
                    holder.postDecision.setText("pending");
                    holder.postDecision.setTextColor(Color.parseColor("#D81B60"));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(holder.viewButton.getContext(), AllRequestActivity.class);
                String postId = model.getPostId();

                intent.putExtra("postId", postId);

                holder.viewButton.getContext().startActivity(intent);

            }
        });

    }

    @NonNull
    @Override
    public RequestPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_request, parent, false);
        return new RequestPostAdapter.RequestPostViewHolder(view);
    }

    public class RequestPostViewHolder extends RecyclerView.ViewHolder {

        TextView postGroup;
        TextView postCurriculum;
        TextView postStudyClass;
        TextView postSubjectList;
        TextView postDecision;
        Button viewButton;

        public RequestPostViewHolder(@NonNull View itemView) {
            super(itemView);

            postGroup = itemView.findViewById(R.id.postGroup);
            postCurriculum = itemView.findViewById(R.id.postCurriculum);
            postStudyClass = itemView.findViewById(R.id.postStudyClass);
            postSubjectList = itemView.findViewById(R.id.postSubjectList);
            postDecision = itemView.findViewById(R.id.postDecision);
            viewButton = itemView.findViewById(R.id.viewButton);
        }
    }
}
